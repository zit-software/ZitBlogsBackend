package com.zit.blog.Auth;

import com.zit.blog.AfterAllIfAllTestsSucceededExtension;
import com.zit.blog.Auth.DTO.LoginDTO;
import com.zit.blog.User.User;
import com.zit.blog.User.UserRepository;
import com.zit.blog.config.exception.CustomException;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(AfterAllIfAllTestsSucceededExtension.class)
public class AuthServiceTest {
    @Autowired
    AuthService underTest;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JWTService jwtService;

    User user;
    String password;

    @BeforeEach
    public void insertData() {
        password = "1234567879";
        user = userRepository.save(new User(null, "randomMockedEmail@gmail.com", passwordEncoder.encode(password), "Mocked User", ""));
    }

    @Test
    public void authenticateShouldThrowErrorWhenEmailNotFound() {
        LoginDTO loginDTO = new LoginDTO("notFoundEmail@gmail.com", "");
        assertThatExceptionOfType(CustomException.class).isThrownBy(() -> {
            underTest.authenticate(loginDTO);
        }).satisfies(e -> assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void authenticateShouldThrowErrorWhenWrongPassword() {
        LoginDTO loginDTO = new LoginDTO(user.getEmail(), "12345");
        assertThatExceptionOfType(CustomException.class).isThrownBy(() -> {
            underTest.authenticate(loginDTO);
        }).satisfies(e -> assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void authenticateShouldAddSecurityContext() {
        LoginDTO loginDTO = new LoginDTO(user.getEmail(), password);
        try {
            underTest.authenticate(loginDTO);
            assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        } catch (CustomException ignored) {
        }
    }

    @Test
    public void authenticateShouldReturnAccessToken() {
        LoginDTO loginDTO = new LoginDTO(user.getEmail(), password);
        try {
            Map<String, String> map = underTest.authenticate(loginDTO);
            assertThat(map.get("accessToken")).isInstanceOf(String.class);
        } catch (CustomException ignored) {
        }
    }

    @Test
    public void authenticateShouldReturnTokenWithCorrectPayload() {
        LoginDTO loginDTO = new LoginDTO(user.getEmail(), password);
        try {
            Map<String, String> map = underTest.authenticate(loginDTO);
            String token = map.get("accessToken");
            String email = jwtService.extractClaim(token, Claims::getSubject);
            assertThat(email).isEqualTo(user.getEmail());
        } catch (CustomException ignored) {
        }
    }
}
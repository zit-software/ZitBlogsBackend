package com.zit.blog.Auth;

import com.zit.blog.Auth.DTO.IdentityDTO;
import com.zit.blog.Auth.DTO.LoginDTO;
import com.zit.blog.Auth.DTO.RegisterDTO;
import com.zit.blog.User.User;
import com.zit.blog.User.UserRepository;
import com.zit.blog.config.exception.CustomException;
import com.zit.blog.config.security.UserDetails;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Map<String, String> authenticate(LoginDTO loginDTO) throws CustomException {
        /**
         * TODO: Hướng dẫn
         * 1. Tìm user theo địa chỉ email lưu vào biến user, nếu user không tồn tại thì throw CustomException với status code là `HttpStatus.UNAUTHORIZED`
         * 2. Kiểm tra mật khẩu có đúng không, nếu không đúng thì throw CustomException với status code là `HttpStatus.UNAUTHORIZED`
         * 3. Bỏ comment phần code bên dưới và chạy thử
         * TODO: Gợi ý
         * 1. Sử dụng `userRepository` để lây ra user
         * 2. Sử dụng `passwordEncoder.matches` để kiểm tra mật khẩu có có khớp không
         */

        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new CustomException("Email không tồn tại", HttpStatus.UNAUTHORIZED)
        );

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new CustomException("Mật khẩu không đúng", HttpStatus.UNAUTHORIZED);
        }

        Map<String, String> responseMap = new HashMap<>();

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                loginDTO.getPassword());
        authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtService.signAccessToken(null, user);
        responseMap.put("accessToken", accessToken);

        return responseMap;
    }

    public Map<String, String> register(RegisterDTO registerDTO) throws CustomException {
        Optional<User> user = userRepository.findByEmail(registerDTO.getEmail());

        if (user.isPresent()) {
            throw new CustomException("Email này đã tồn tại", HttpStatus.CONFLICT);
        }

        User newUser = User.builder()
                .email(registerDTO.getEmail())
                .fullName(registerDTO.getFullName())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .avatar("https://www.gravatar.com/avatar/" + encodeMD5(registerDTO.getEmail()))
                .build();

        newUser = userRepository.save(newUser);

        String accessToken = jwtService.signAccessToken(null, new UserDetails(newUser));

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("accessToken", accessToken);

        return responseMap;
    }

    public IdentityDTO getIdentity() {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return new IdentityDTO(user.getEmail(), user.getFullName(), user.getAvatar(), user.getId());
    }

    public String encodeMD5(String raw) {
        String encoded = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(raw.getBytes());
            byte[] digest = md.digest();
            encoded = DatatypeConverter
                    .printHexBinary(digest).toLowerCase();
            System.out.println(encoded);
        } catch (NoSuchAlgorithmException ignored) {
        }
        return encoded;
    }
}

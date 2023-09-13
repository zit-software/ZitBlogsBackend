package com.zit.blog.Auth;

import com.zit.blog.Auth.DTO.IdentityDTO;
import com.zit.blog.Auth.DTO.LoginDTO;
import com.zit.blog.Auth.DTO.RegisterDTO;
import com.zit.blog.config.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginDTO loginDTO) throws CustomException {
        return authService.authenticate(loginDTO);
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody RegisterDTO registerDTO) throws CustomException {
        return authService.register(registerDTO);
    }

    @GetMapping()
    public IdentityDTO getIdentity() {
        return authService.getIdentity();
    }
}

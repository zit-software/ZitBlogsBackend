package com.zit.blog.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDTO {
    private String email;
    private String password;
    private String fullName;
}

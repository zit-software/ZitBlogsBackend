package com.zit.blog.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityDTO {
    String email;
    String fullName;
    String avatar;
    Long id;
}

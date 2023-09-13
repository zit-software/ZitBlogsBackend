package com.zit.blog.Blog.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBlogDTO {
    private String title;
    private String content;
}

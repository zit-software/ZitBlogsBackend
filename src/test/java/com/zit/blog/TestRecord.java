package com.zit.blog;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TestRecord {
    private Long id;
    private String name;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}

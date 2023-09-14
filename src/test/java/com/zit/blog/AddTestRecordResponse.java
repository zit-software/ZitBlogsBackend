package com.zit.blog;

import lombok.Data;

import java.util.Date;

@Data
public class AddTestRecordResponse {
    private Long id;
    private String name;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}

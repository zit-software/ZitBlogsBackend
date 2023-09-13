package com.zit.blog;

import lombok.Data;

import java.util.Date;

@Data
public class AddRecordResponse {
    private Long id;
    private String name;
    private String time;
}

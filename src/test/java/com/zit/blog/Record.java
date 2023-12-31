package com.zit.blog;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Record {
    private Long id;
    private String name;
    private Long testId;
    private Date createdAt;
    private String macAddress;
}

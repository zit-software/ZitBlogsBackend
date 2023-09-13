package com.zit.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestingConfiguration {
    @Value("${app.blog.test.name}")
    private String testTaker;
    @Value("${app.blog.test.judge-server}")
    private String judgeServerURL;
}

package com.zit.blog;


import com.zit.blog.Auth.AuthServiceTest;
import com.zit.blog.Blog.BlogServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;


@Suite
@SelectClasses({AuthServiceTest.class, BlogServiceTest.class})
@SpringBootTest
class ServerApplicationTests {
}

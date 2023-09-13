package com.zit.blog.Blog;

import com.zit.blog.AfterAllIfAllTestsSucceededExtension;
import com.zit.blog.Blog.DTO.CreateBlogDTO;
import com.zit.blog.Blog.DTO.UpdateBlogDTO;
import com.zit.blog.User.User;
import com.zit.blog.User.UserRepository;
import com.zit.blog.config.exception.CustomException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(AfterAllIfAllTestsSucceededExtension.class)
public class BlogServiceTest {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BlogService underTest;
    Blog mockedBlog1;
    Blog mockedBlog2;
    User user;

    @BeforeEach
    public void insertData() {
        // Insert Data
        String password = "1234567879";
        user = userRepository.save(
                new User(null, "randomMockedEmail@gmail.com",
                        passwordEncoder.encode(password), "Mocked User", "")
        );
        User user2 = userRepository.save(
                new User(null, "randomMockedEmail2@gmail.com",
                        passwordEncoder.encode(password), "Mocked User 2", "")
        );
        mockedBlog1 = blogRepository.save(
                new Blog(null, "Mocked Blog 1", "Mocked Blog 1 Content", user,
                        new Date(), new Date())
        );
        mockedBlog2 = blogRepository.save(
                new Blog(null, "Mocked Blog 2", "Mocked Blog 2 Content", user2,
                        new Date(), new Date())
        );

        // Login User 1
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), password);
        authenticationManager.authenticate(authentication);
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user, null,
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Test
    void getAllBlogsShouldReturnMockedBlogs() {
        List<Blog> allBlogs = underTest.getAllBlogs();
        assertThat(allBlogs).contains(mockedBlog1, mockedBlog2);
    }

    @Test
    void createNewBlog() {
        CreateBlogDTO createBlogDTO = new CreateBlogDTO("Mocked Create Blog", "Mocked Create Block Content");
        Blog blog = underTest.createNewBlog(createBlogDTO);

        assertThat(blog.getContent()).isEqualTo(createBlogDTO.getContent());
        assertThat(blog.getTitle()).isEqualTo(createBlogDTO.getTitle());
        assertThat(blog.getUser()).isEqualTo(user);

        List<Blog> allBlogs = underTest.getAllBlogs();
        assertThat(allBlogs).contains(mockedBlog1, mockedBlog2, blog);
    }

    @Test
    void updateBlogShouldThrowExceptionWhenBlogIdNotFound() {
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> {
                    underTest.updateBlog(3L, null);
                })
                .satisfies(e -> assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void updateBlockedWhenDifferentUserTryToUpdate() {
        UpdateBlogDTO updateBlogDTO = new UpdateBlogDTO("Mocked Blog Updated", "Mocked Blog Content Updated");
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> {
                    underTest.updateBlog(mockedBlog2.getId(), updateBlogDTO);
                })
                .satisfies(e -> assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.FORBIDDEN));
    }

    @Test
    void updateBlogShouldUpdateCurrentBlog() {
        UpdateBlogDTO updateBlogDTO = new UpdateBlogDTO("Mocked Blog Updated", "Mocked Blog Content Updated");
        try {
            Blog blog = underTest.updateBlog(mockedBlog1.getId(), updateBlogDTO);
            assertThat(blog.getId()).isEqualTo(mockedBlog1.getId());
            assertThat(mockedBlog1.getTitle()).isEqualTo(updateBlogDTO.getTitle());
            assertThat(mockedBlog1.getContent()).isEqualTo(updateBlogDTO.getContent());
            assertThat(blog.getUser()).isEqualTo(mockedBlog1.getUser());
        } catch (CustomException ignored) {
        }
    }

    @Test
    void deleteBlog() {
        underTest.deleteBlog(mockedBlog1.getId());
        List<Blog> allBlogs = blogRepository.findAll();
        assertThat(allBlogs).doesNotContain(mockedBlog1);
    }
}
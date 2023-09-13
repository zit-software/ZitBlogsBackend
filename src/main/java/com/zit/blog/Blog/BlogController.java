package com.zit.blog.Blog;

import com.zit.blog.Blog.DTO.CreateBlogDTO;
import com.zit.blog.Blog.DTO.UpdateBlogDTO;
import com.zit.blog.config.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping()
    public List<Blog> getAllList() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public Blog getOne(@PathVariable Long id) throws CustomException {
        return blogService.getOneBlog(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public Blog createBlog(@RequestBody CreateBlogDTO createBlogDTO) {
        return blogService.createNewBlog(createBlogDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Blog updateBlog(@RequestBody UpdateBlogDTO updateBlogDTO, @PathVariable Long id) throws CustomException {
        return blogService.updateBlog(id, updateBlogDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }
}

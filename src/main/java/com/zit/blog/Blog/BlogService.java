package com.zit.blog.Blog;

import com.zit.blog.Blog.DTO.CreateBlogDTO;
import com.zit.blog.Blog.DTO.UpdateBlogDTO;
import com.zit.blog.User.User;
import com.zit.blog.config.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public User getCurrentLoginUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public List<Blog> getAllBlogs() {
        /**
         * TODO: Hướng dẫn
         * 1. Trả về danh sách tất cả các blog, thứ tự tùy ý
         * TODO: Gợi ý
         * 1. Sử dụng `blogRepository` để lấy ra danh sách các blog
         */

        return null;
    }

    public Blog getOneBlog(Long id) throws CustomException {
        /**
         * TODO: Hướng dẫn
         * 1. Trả về blog có id là `id`, nếu không tồn tại thì throw CustomException với status code là `HttpStatus.NOT_FOUND`
         * TODO: Gợi ý
         * 1. Sử dụng `blogRepository` để tương tác với database
         */

        return null;
    }

    public Blog createNewBlog(CreateBlogDTO createBlogDTO) {
        /**
         * TODO: Hướng dẫn
         * 1. Tạo mới một blog với dữ liệu từ `createBlogDTO` và lưu vào database
         * 2. Trả về blog vừa tạo
         * TODO: Gợi ý
         * 1. Sử dụng `blogRepository` để tương tác với database
         * 2. Dùng hàm `getCurrentLoginUser` để lấy ra user hiện tại
         * 3. Dùng `Blog.builder` để tạo mới một blog
         * 4. Sử dụng `blogRepository.save` để lưu blog vào database
         */

        return null;
    }

    public Blog updateBlog(Long blogId, UpdateBlogDTO updateBlogDTO) throws CustomException {
        /**
         * TODO: Hướng dẫn
         * 1. Cập nhật blog có id là `blogId` với dữ liệu từ `updateBlogDTO` và lưu vào database
         * 2. Nếu không tìm thấy blog thì throw CustomException với status code là `HttpStatus.NOT_FOUND`
         * 3. Nếu người dùng hiện tại không phải là tác giả của blog thì throw CustomException với status code là `HttpStatus.FORBIDDEN`
         * 4. Trả về blog vừa cập nhật
         * TODO: Gợi ý
         * 1. Sử dụng `blogRepository` để tương tác với database
         * 2. Dùng hàm `getCurrentLoginUser` để lấy ra user hiện tại
         */

        return null;
    }

    public void deleteBlog(Long blogId) {
        blogRepository.deleteById(blogId);
    }

}

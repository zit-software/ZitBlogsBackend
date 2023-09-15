# Hướng dẫn làm bài tập workshop springboot "ZIT Blogs"

## Bối cảnh

Tình hình là gần đây **ZIT Software** đang phát triển cho mình một trang blog để chia sẻ các bài viết về lập trình đến với các bạn.

Đang viết dở thì bị trộm vô nhà xóa hết mấy dòng code bên **backend** : )) nên tụi mình rất cần các bạn viết lại các đoạn code đã bị mất đó để phần mềm có thể hoạt động bình thường.

## Việc cần làm

Hiện tại phần **frontend** của tụi mình đã được deploy lên địa chỉ [https://zitblogs.vercel.app](https://zitblogs.vercel.app).

Chúng mình cần các bạn clone project **backend** của chúng mình từ github về: [https://github.com/zit-software/ZitBlogsBackend](https://github.com/zit-software/ZitBlogsBackend) và viết thêm code vào các hàm bị bỏ trống trong các class `main.java.com.zit.blog.Auth.AuthService` và `main.java.com.zit.blog.Blog.BlogService` (ít thì 5 hàm nhiều thì 10 hàm).

### AuthService

- `authenticate`: Xử lý yêu cầu đăng nhập của người dùng

### BlogService

- `getAllList`: Trả về danh sách bài blog, sắp xếp theo giảm dần theo trường `createdAt`
- `getOneBlog`: Trả về một bài blog theo `id`
- `createBlog`: Tạo một bài blog
- `updateBlog`: Cập nhật bài blog


Sau khi đã hoàn thành các hàm trên, các bạn sẽ chạy file test để chấm điểm (`test.java.com.zit.blog.ServerApplicationTests`). Các bạn pass hết test case sớm nhất sẽ nhận được quà từ **Ban tổ chức**.

![](https://mermaid.ink/img/pako:eNqVkbFuAjEMhl8l8gwvkK0VK12qblmsi--IlEuQ4xSh494dQ0A99RbwEvt3ZH32P0GXPYEF4l3AgXF0yWj8FGJzuWy3eTKfMQ_WVFVcWnSnlt8iJDHB_9VFOKTB9DXGLxxp1aARQ1yp-IuCvJKPWMop82L8DoVMx6SP_5B_ej36ppvWmJ_QtzVegJYgcU3c5SSU5F2GBQJsYCTWxb1e-47hQA6k1wGrqaceaxQHLs36Favk73PqwApX2kCb-LAIbI-xqEo-SOZ9c_Bu5HwF0-aPEw?type=png)

> Lưu ý: Không chỉnh sửa hoặc xóa những đoạn code không nằm trong hướng dẫn!

## Hướng dẫn chi tiết

### Clone repo

Các bạn di chuyển vào thư mục muốn tải project về và sử dụng lệnh

```sh
git clone https://github.com/zit-software/ZitBlogsBackend
```

### Chạy project

Sau khi đã clone project về, các bạn sử dụng một **Intelj** để mở project này lên

Trong thư mục `src/main/resources`:
- Sao chép file `application.properties.example` thành `application.properties`

![](https://i.ibb.co/gVngfN4/Screenshot-from-2023-09-12-19-32-03.png)

Các bạn đợi **Intelj** tự động cài xong các thư viện thì có thể chạy dự án này lên bằng cách nhấn vào nút "play" ở góc trên bên phải IDE.

![](https://i.ibb.co/rpVtx7Y/Screenshot-from-2023-09-12-19-35-36.png)

### Test API

Các bạn có thể test API bằng một trong 2 cách:

- **Frontend** của **ZIT Blogs** đã được deploy tại địa chỉ [https://zitblogs.vercel.app](https://zitblogs.vercel.app): Trang web này được thiết lập để gọi về các api tại địa chỉ [http://localhost:8080](http://localhost:8080) (Gọi về endpoint của server spring hiện tại của các bạn).
- Test bằng **Swagger UI**: tại địa chỉ [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Chấm điểm

Sau khi đã viết xong các hàm được yêu cầu ở trên, các bạn sẽ chạy file test để chấm điểm (`test.java.com.zit.blog.ServerApplicationTests`). Các bạn pass hết test case sớm nhất sẽ nhận được quà từ **Ban tổ chức**.

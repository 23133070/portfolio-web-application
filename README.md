# Website Cá Nhân - Personal Portfolio

Đây là một trang web cá nhân hoàn chỉnh được xây dựng bằng **Java Servlet** cho backend và HTML, CSS, JavaScript cho frontend.

## 📋 Các tính năng

### ✨ Trang chủ (Home)
- Giới thiệu ngắn gọn với hiệu ứng typing animation
- Ảnh đại diện với hiệu ứng gradient border xoay
- Navigation menu responsive
- Call-to-action buttons

### 👨‍💻 Giới thiệu (About)  
- Tiểu sử cá nhân
- Danh sách sở thích với icons
- Kỹ năng với progress bars có animation
- Công cụ sử dụng với grid layout

### 🚀 Dự án (Projects)
- Grid hiển thị 4 dự án mẫu
- Hover effects với overlay
- Links đến GitHub và Live Demo
- Tech stack tags cho mỗi dự án

### 📝 Blog
- 3 bài viết mẫu với thumbnails
- Categories và meta information
- Responsive card layout

### 📚 Bài tập (Assignments)
- 9 bài tập từ tuần 1-9
- Status indicators (Completed, In Progress, Pending)
- Links để thêm đường dẫn nộp bài

### 📧 Liên hệ (Contact)
- Form liên hệ với validation
- Thông tin liên hệ
- Social media links
- Email handling simulation

## 🛠️ Công nghệ sử dụng

### Backend
- **Java 11+**: Core programming language
- **Java Servlet API**: Web request handling
- **Maven**: Build tool và dependency management
- **DAO Pattern**: Data access layer
- **File Storage**: Lưu trữ dữ liệu dạng text file

### Frontend  
- **HTML5**: Cấu trúc semantic
- **CSS3**: 
  - Flexbox & Grid Layout
  - CSS Variables
  - Animations & Transitions
  - Responsive Design
  - Gradient Effects
- **JavaScript**: 
  - DOM Manipulation
  - Event Listeners
  - Intersection Observer API
  - Smooth Scrolling

### Server
- **Apache Tomcat** hoặc **Jetty**: Web server
- **Web.xml**: Deployment descriptor

## 🎨 Thiết kế

- **Color Scheme**: Gradient từ purple đến blue
- **Typography**: Inter font family
- **Layout**: Mobile-first responsive design
- **Icons**: Font Awesome
- **Effects**: 
  - Typing animation
  - Skill progress bars
  - Scroll animations
  - Hover effects
  - Parallax scrolling

## 📱 Responsive Design

Website được thiết kế responsive cho:
- Desktop (1200px+)
- Tablet (768px - 1199px) 
- Mobile (< 768px)

## 🚀 Cách sử dụng

### Yêu cầu hệ thống
- Java JDK 11 hoặc cao hơn
- Apache Maven 3.6+
- Apache Tomcat 9+ hoặc Jetty 9+

### Cài đặt và chạy

1. **Clone project**
```bash
git clone <repository-url>
cd WEB_CACNHAN
```

2. **Compile với Maven**
```bash
mvn clean compile
```

3. **Chạy với Jetty (đề xuất)**
```bash
mvn jetty:run
```

4. **Hoặc chạy với Tomcat**
```bash
mvn tomcat7:run
```

5. **Truy cập ứng dụng**
- Trang chủ: `http://localhost:8080/portfolio`
- Admin panel: `http://localhost:8080/portfolio/admin/dashboard`
- API bài tập: `http://localhost:8080/portfolio/assignment/json`

### Build WAR file
```bash
mvn clean package
```
File WAR sẽ được tạo trong thư mục `target/portfolio-web.war`

### Deploy lên server
1. Copy file WAR vào thư mục webapps của Tomcat
2. Restart Tomcat
3. Truy cập qua `http://server:port/portfolio-web`

## 📝 Tùy chỉnh nội dung

### Thông tin cá nhân
```html
<!-- Trong section home -->
<h1>Xin chào! Tôi là <span class="highlight">Tên của bạn</span></h1>
```

### Kỹ năng
```html
<!-- Thêm/sửa skill items -->
<div class="skill-item">
    <i class="fab fa-html5"></i>
    <span>HTML5</span>
    <div class="skill-bar">
        <div class="skill-progress" data-width="90%"></div>
    </div>
</div>
```

### Dự án
```html
<!-- Cập nhật project cards -->
<div class="project-card">
    <div class="project-image">
        <img src="path-to-your-image" alt="Project Name">
        <div class="project-overlay">
            <div class="project-links">
                <a href="github-link" class="project-link" target="_blank">
                    <i class="fab fa-github"></i>
                </a>
                <a href="demo-link" class="project-link" target="_blank">
                    <i class="fas fa-external-link-alt"></i>
                </a>
            </div>
        </div>
    </div>
    <!-- ... content -->
</div>
```

### Bài tập
```html
<!-- Cập nhật assignment links -->
<div class="assignment-links">
    <a href="your-assignment-link" class="assignment-link" target="_blank">
        <i class="fas fa-link"></i> Link nộp bài
    </a>
</div>
```

### Social Media
```html
<!-- Cập nhật social links -->
<a href="your-linkedin-url" target="_blank" class="social-link linkedin">
    <i class="fab fa-linkedin-in"></i>
</a>
```

## 🎯 CSS Variables

Dễ dàng thay đổi màu sắc thông qua CSS variables:

```css
:root {
    --primary-color: #667eea;
    --secondary-color: #764ba2;
    --accent-color: #f093fb;
    /* ... */
}
```

## ✅ Browser Support

- Chrome (Latest)
- Firefox (Latest)  
- Safari (Latest)
- Edge (Latest)

## 🏗️ Kiến trúc ứng dụng

```
┌─────────────────┐    HTTP    ┌─────────────────┐
│   Frontend      │  Request   │   Servlet       │
│  (HTML/CSS/JS)  │ ────────>  │   Container     │
│                 │ <──────    │  (Tomcat/Jetty) │
└─────────────────┘  Response  └─────────────────┘
                                        │
                                        ▼
                               ┌─────────────────┐
                               │    Servlets     │
                               │ ┌─────────────┐ │
                               │ │ Contact     │ │
                               │ │ Assignment  │ │
                               │ │ Admin       │ │
                               │ └─────────────┘ │
                               └─────────────────┘
                                        │
                                        ▼
                               ┌─────────────────┐
                               │   DAO Layer     │
                               │ ┌─────────────┐ │
                               │ │ ContactDAO  │ │
                               │ │AssignmentDAO│ │
                               │ └─────────────┘ │
                               └─────────────────┘
                                        │
                                        ▼
                               ┌─────────────────┐
                               │ File Storage    │
                               │  contacts.txt   │
                               │ assignments.txt │
                               └─────────────────┘
```

## � API Endpoints

### Contact API
- `POST /contact` - Gửi tin nhắn liên hệ
- `GET /contact?action=success` - Trang thành công
- `GET /contact?action=error` - Trang lỗi

### Assignment API  
- `GET /assignment/list` - Danh sách bài tập
- `GET /assignment/view?week=1` - Chi tiết bài tập
- `GET /assignment/json` - Dữ liệu JSON
- `POST /assignment/submit` - Nộp bài tập

### Admin API
- `GET /admin/dashboard` - Trang tổng quan
- `GET /admin/contacts` - Quản lý tin nhắn
- `GET /admin/assignments` - Quản lý bài tập
- `POST /admin/mark-read` - Đánh dấu đã đọc
- `POST /admin/grade-assignment` - Chấm điểm

## 📁 Cấu trúc dự án

```
WEB_CACNHAN/
├── pom.xml                         # Maven configuration
├── README.md                       # Tài liệu dự án
├── src/
│   └── main/
│       ├── java/
│       │   └── com/portfolio/
│       │       ├── model/          # Data models
│       │       │   ├── Contact.java
│       │       │   └── Assignment.java
│       │       ├── dao/            # Data Access Objects
│       │       │   ├── ContactDAO.java
│       │       │   ├── ContactDAOImpl.java
│       │       │   ├── AssignmentDAO.java
│       │       │   └── AssignmentDAOImpl.java
│       │       ├── servlet/        # Servlet controllers
│       │       │   ├── ContactServlet.java
│       │       │   ├── AssignmentServlet.java
│       │       │   └── AdminServlet.java
│       │       └── filter/         # Filters
│       │           └── EncodingFilter.java
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── web.xml         # Deployment descriptor
│           │   └── data/           # Data storage directory
│           ├── index.html          # Trang chủ
│           ├── style.css           # Styles
│           └── script.js           # Frontend JS
└── target/                         # Build output
    └── portfolio-web.war           # Deployable WAR file
```

## 🤝 Đóng góp

Mọi góp ý và cải thiện đều được chào đón!

## 📞 Liên hệ

- Email: your.email@gmail.com
- LinkedIn: [Your LinkedIn]
- GitHub: [Your GitHub]

---

*Được thiết kế với ❤️ bằng HTML, CSS & JavaScript*
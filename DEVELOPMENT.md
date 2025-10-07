# Hướng dẫn Development - Portfolio Web App

## 🔧 Setup môi trường phát triển

### 1. Cài đặt công cụ cần thiết
- **Java JDK 11+**: [Download Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- **Apache Maven 3.6+**: [Download Maven](https://maven.apache.org/download.cgi)
- **IDE**: IntelliJ IDEA, Eclipse, hoặc VS Code với Java Extension Pack
- **Git**: [Download Git](https://git-scm.com/downloads)

### 2. Kiểm tra cài đặt
```bash
java -version      # Java 11 hoặc cao hơn
mvn -version       # Maven 3.6+
git --version      # Git version
```

## 🚀 Chạy ứng dụng

### Phương pháp 1: Maven Jetty Plugin (Đề xuất)
```bash
# Clone project
git clone <repository-url>
cd WEB_CACNHAN

# Compile code
mvn clean compile

# Chạy với Jetty
mvn jetty:run

# Truy cập: http://localhost:8080/portfolio
```

### Phương pháp 2: Maven Tomcat Plugin
```bash
# Chạy với embedded Tomcat
mvn tomcat7:run

# Truy cập: http://localhost:8080/portfolio
```

### Phương pháp 3: Deploy lên Tomcat Server
```bash
# Build WAR file
mvn clean package

# Copy WAR file đến Tomcat
cp target/portfolio-web.war $TOMCAT_HOME/webapps/

# Start Tomcat
$TOMCAT_HOME/bin/startup.sh   # Linux/Mac
# hoặc
$TOMCAT_HOME/bin/startup.bat  # Windows

# Truy cập: http://localhost:8080/portfolio-web
```

## 🛠️ Development Workflow

### 1. Cấu trúc Source Code
```
src/main/java/com/portfolio/
├── model/          # Data models (POJO classes)
├── dao/           # Data Access Objects 
├── servlet/       # HTTP request handlers
└── filter/        # Request/Response filters
```

### 2. Hot Reload Development
Khi sử dụng `mvn jetty:run`, Jetty sẽ tự động reload khi:
- File Java được compile lại
- File trong webapp được thay đổi

Để compile lại Java code:
```bash
# Terminal khác (giữ jetty:run chạy)
mvn compile
```

### 3. Debugging
```bash
# Chạy với debug mode
mvn jetty:run -Djetty.jvmArgs="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# Attach debugger từ IDE tới port 5005
```

## 📝 Thêm chức năng mới

### 1. Tạo Model mới
```java
// src/main/java/com/portfolio/model/NewModel.java
public class NewModel {
    private int id;
    private String name;
    
    // constructors, getters, setters
}
```

### 2. Tạo DAO Interface và Implementation
```java
// Interface
public interface NewModelDAO {
    NewModel save(NewModel model);
    NewModel findById(int id);
    List<NewModel> findAll();
}

// Implementation với file storage
public class NewModelDAOImpl implements NewModelDAO {
    // Implementation logic
}
```

### 3. Tạo Servlet
```java
@WebServlet("/newmodel/*")
public class NewModelServlet extends HttpServlet {
    private NewModelDAO dao;
    
    @Override
    public void init() throws ServletException {
        this.dao = new NewModelDAOImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle GET requests
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle POST requests
    }
}
```

### 4. Cập nhật web.xml (nếu cần)
```xml
<servlet>
    <servlet-name>NewModelServlet</servlet-name>
    <servlet-class>com.portfolio.servlet.NewModelServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>NewModelServlet</servlet-name>
    <url-pattern>/newmodel/*</url-pattern>
</servlet-mapping>
```

## 🧪 Testing

### 1. Unit Testing với JUnit
```java
// src/test/java/com/portfolio/dao/ContactDAOImplTest.java
@Test
public void testSaveContact() {
    ContactDAO dao = new ContactDAOImpl();
    Contact contact = new Contact("John", "john@test.com", "Test", "Message");
    
    Contact saved = dao.save(contact);
    
    assertNotNull(saved);
    assertTrue(saved.getId() > 0);
}
```

### 2. Chạy tests
```bash
mvn test
```

### 3. Integration Testing
```bash
# Chạy server trong background
mvn jetty:run &

# Test với curl
curl -X POST http://localhost:8080/portfolio/contact \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=Test&email=test@example.com&subject=Test&message=Testing"

# Stop background server
pkill -f jetty
```

## 📊 Monitoring & Logging

### 1. Xem logs
```bash
# Jetty logs
tail -f target/jetty-console.log

# Application logs (System.out.println)
# Hiển thị trực tiếp trong console khi chạy mvn jetty:run
```

### 2. Performance Monitoring
```java
// Thêm vào servlet để đo thời gian xử lý
long startTime = System.currentTimeMillis();
// ... xử lý request
long duration = System.currentTimeMillis() - startTime;
System.out.println("Request processed in: " + duration + "ms");
```

## 🔒 Security Best Practices

### 1. Input Validation
```java
// Luôn validate input từ user
private String validateInput(String input) {
    if (input == null || input.trim().isEmpty()) {
        throw new IllegalArgumentException("Input cannot be empty");
    }
    return input.trim();
}
```

### 2. XSS Protection
```java
// Escape HTML trong output
private String escapeHtml(String input) {
    if (input == null) return "";
    return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
}
```

### 3. SQL Injection Prevention
- Dự án này sử dụng file storage, không có SQL
- Nếu chuyển sang database, sử dụng PreparedStatement

## 🚢 Deployment

### 1. Build cho Production
```bash
# Build với production profile
mvn clean package -Pprod

# WAR file sẽ có minified CSS/JS
```

### 2. Deploy lên Server
```bash
# Copy WAR đến server
scp target/portfolio-web.war user@server:/opt/tomcat/webapps/

# SSH vào server và restart Tomcat
ssh user@server
sudo systemctl restart tomcat
```

### 3. Environment Configuration
```bash
# Set Java system properties
export JAVA_OPTS="-Ddata.dir=/opt/portfolio/data -Xmx512m"
```

## 🐛 Troubleshooting

### 1. Port đã được sử dụng
```bash
# Tìm process sử dụng port 8080
lsof -i :8080

# Kill process
kill -9 <PID>

# Hoặc chạy trên port khác
mvn jetty:run -Djetty.port=8081
```

### 2. Permission Denied (File I/O)
```bash
# Tạo thư mục data với quyền phù hợp
mkdir -p src/main/webapp/WEB-INF/data
chmod 755 src/main/webapp/WEB-INF/data
```

### 3. Encoding Issues
- Đảm bảo file được save với UTF-8 encoding
- Kiểm tra EncodingFilter trong web.xml
- Set IDE encoding thành UTF-8

## 📚 Resources

- [Java Servlet Tutorial](https://docs.oracle.com/javaee/7/tutorial/servlets.htm)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Jetty Documentation](https://www.eclipse.org/jetty/documentation/)
- [Tomcat Documentation](https://tomcat.apache.org/tomcat-9.0-doc/)
package com.portfolio.servlet;

import com.portfolio.dao.ContactDAO;
import com.portfolio.dao.ContactDAOImpl;
import com.portfolio.model.Contact;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet to handle contact form submissions
 */
public class ContactServlet extends HttpServlet {
    
    private ContactDAO contactDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.contactDAO = new ContactDAOImpl();
    }

    /**
     * Handle GET requests - show contact form or success page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("success".equals(action)) {
            // Show success page
            showSuccessPage(response);
        } else if ("error".equals(action)) {
            // Show error page
            showErrorPage(response, request.getParameter("message"));
        } else {
            // Redirect to main page
            response.sendRedirect(request.getContextPath() + "/index.html#contact");
        }
    }

    /**
     * Handle POST requests - process contact form submission
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set encoding for Vietnamese characters
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Get form parameters
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String subject = request.getParameter("subject");
            String message = request.getParameter("message");
            
            // Validate input
            String validationError = validateInput(name, email, subject, message);
            if (validationError != null) {
                response.sendRedirect(request.getContextPath() + 
                    "/contact?action=error&message=" + validationError);
                return;
            }
            
            // Create contact object
            Contact contact = new Contact(name.trim(), email.trim(), subject.trim(), message.trim());
            contact.setIpAddress(getClientIP(request));
            
            // Save contact
            Contact savedContact = contactDAO.save(contact);
            
            if (savedContact != null) {
                // Send notification email (simulate)
                sendNotificationEmail(savedContact);
                
                // Redirect to success page
                response.sendRedirect(request.getContextPath() + "/contact?action=success");
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/contact?action=error&message=Có lỗi xảy ra khi gửi tin nhắn");
            }
            
        } catch (Exception e) {
            System.err.println("Error processing contact form: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + 
                "/contact?action=error&message=Lỗi hệ thống, vui lòng thử lại");
        }
    }

    /**
     * Validate form input
     */
    private String validateInput(String name, String email, String subject, String message) {
        if (name == null || name.trim().isEmpty()) {
            return "Vui lòng nhập họ và tên";
        }
        
        if (email == null || email.trim().isEmpty()) {
            return "Vui lòng nhập email";
        }
        
        if (!isValidEmail(email)) {
            return "Email không hợp lệ";
        }
        
        if (subject == null || subject.trim().isEmpty()) {
            return "Vui lòng nhập chủ đề";
        }
        
        if (message == null || message.trim().isEmpty()) {
            return "Vui lòng nhập tin nhắn";
        }
        
        // Check length limits
        if (name.length() > 100) {
            return "Họ tên không được quá 100 ký tự";
        }
        
        if (email.length() > 150) {
            return "Email không được quá 150 ký tự";
        }
        
        if (subject.length() > 200) {
            return "Chủ đề không được quá 200 ký tự";
        }
        
        if (message.length() > 2000) {
            return "Tin nhắn không được quá 2000 ký tự";
        }
        
        return null; // No validation errors
    }

    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /**
     * Get client IP address
     */
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    /**
     * Send notification email (simulation)
     * In production, this would use JavaMail API to send actual emails
     */
    private void sendNotificationEmail(Contact contact) {
        // Email notification would be implemented here with JavaMail API
        // For now, the contact is just stored in the data file
    }

    /**
     * Show success page
     */
    private void showSuccessPage(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Gửi thành công - Portfolio</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("<style>");
        out.println(".success-container { display: flex; justify-content: center; align-items: center; min-height: 100vh; padding: 2rem; }");
        out.println(".success-card { background: white; padding: 3rem; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.15); text-align: center; max-width: 500px; }");
        out.println(".success-icon { font-size: 4rem; color: #28a745; margin-bottom: 1rem; }");
        out.println(".success-title { color: #28a745; margin-bottom: 1rem; }");
        out.println(".success-message { color: #666; margin-bottom: 2rem; line-height: 1.6; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='success-container'>");
        out.println("<div class='success-card'>");
        out.println("<div class='success-icon'>✓</div>");
        out.println("<h2 class='success-title'>Gửi tin nhắn thành công!</h2>");
        out.println("<p class='success-message'>Cảm ơn bạn đã liên hệ. Tôi sẽ phản hồi lại trong thời gian sớm nhất.</p>");
        out.println("<a href='index.html' class='btn btn-primary'>Về trang chủ</a>");
        out.println("</div>");
        out.println("</div>");
        
        // Auto redirect after 5 seconds
        out.println("<script>");
        out.println("setTimeout(function() { window.location.href = 'index.html'; }, 5000);");
        out.println("</script>");
        
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Show error page
     */
    private void showErrorPage(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        if (errorMessage == null) {
            errorMessage = "Đã xảy ra lỗi không xác định";
        }
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Lỗi - Portfolio</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("<style>");
        out.println(".error-container { display: flex; justify-content: center; align-items: center; min-height: 100vh; padding: 2rem; }");
        out.println(".error-card { background: white; padding: 3rem; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.15); text-align: center; max-width: 500px; }");
        out.println(".error-icon { font-size: 4rem; color: #dc3545; margin-bottom: 1rem; }");
        out.println(".error-title { color: #dc3545; margin-bottom: 1rem; }");
        out.println(".error-message { color: #666; margin-bottom: 2rem; line-height: 1.6; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='error-container'>");
        out.println("<div class='error-card'>");
        out.println("<div class='error-icon'>⚠</div>");
        out.println("<h2 class='error-title'>Có lỗi xảy ra</h2>");
        out.println("<p class='error-message'>" + errorMessage + "</p>");
        out.println("<a href='index.html#contact' class='btn btn-primary'>Thử lại</a>");
        out.println("</div>");
        out.println("</div>");
        
        // Auto redirect after 5 seconds
        out.println("<script>");
        out.println("setTimeout(function() { window.location.href = 'index.html#contact'; }, 5000);");
        out.println("</script>");
        
        out.println("</body>");
        out.println("</html>");
    }
}
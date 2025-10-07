package com.portfolio.servlet;

import com.portfolio.dao.ContactDAO;
import com.portfolio.dao.ContactDAOImpl;
import com.portfolio.dao.AssignmentDAO;
import com.portfolio.dao.AssignmentDAOImpl;
import com.portfolio.model.Contact;
import com.portfolio.model.Assignment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Admin panel servlet for managing contacts and assignments
 */
public class AdminServlet extends HttpServlet {
    
    private ContactDAO contactDAO;
    private AssignmentDAO assignmentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.contactDAO = new ContactDAOImpl();
        this.assignmentDAO = new AssignmentDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String action = pathInfo != null ? pathInfo.substring(1) : "dashboard";
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        switch (action) {
            case "dashboard":
            case "":
                showDashboard(request, response);
                break;
            case "contacts":
                showContacts(request, response);
                break;
            case "assignments":
                showAssignments(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String action = pathInfo != null ? pathInfo.substring(1) : "";
        
        request.setCharacterEncoding("UTF-8");

        switch (action) {
            case "mark-read":
                markContactAsRead(request, response);
                break;
            case "grade-assignment":
                gradeAssignment(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        PrintWriter out = response.getWriter();
        
        // Get statistics
        int totalContacts = contactDAO.count();
        int unreadContacts = contactDAO.countUnread();
        double completionPercentage = assignmentDAO.getCompletionPercentage();
        int completedAssignments = assignmentDAO.countByStatus(Assignment.AssignmentStatus.COMPLETED);
        
        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Admin Dashboard - Portfolio</title>");
        out.println("<link rel='stylesheet' href='../style.css'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css' rel='stylesheet'>");
        out.println("<style>");
        out.println(".dashboard { padding-top: 100px; }");
        out.println(".stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 2rem; margin-bottom: 3rem; }");
        out.println(".stat-card { background: white; padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); text-align: center; }");
        out.println(".stat-icon { font-size: 3rem; margin-bottom: 1rem; }");
        out.println(".stat-number { font-size: 2.5rem; font-weight: bold; margin-bottom: 0.5rem; }");
        out.println(".quick-actions { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 2rem; }");
        out.println(".action-card { background: white; padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        // Navigation
        out.println("<nav class='navbar'>");
        out.println("<div class='nav-container'>");
        out.println("<div class='nav-logo'><a href='../index.html'>Portfolio Admin</a></div>");
        out.println("<div class='nav-menu'>");
        out.println("<a href='dashboard' class='nav-link'>Dashboard</a>");
        out.println("<a href='contacts' class='nav-link'>Liên hệ</a>");
        out.println("<a href='assignments' class='nav-link'>Bài tập</a>");
        out.println("<a href='../index.html' class='nav-link'>Về trang chủ</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");
        
        // Dashboard content
        out.println("<main class='dashboard'>");
        out.println("<div class='container'>");
        out.println("<h1 class='section-title'>Admin Dashboard</h1>");
        
        // Statistics
        out.println("<div class='stats-grid'>");
        
        out.println("<div class='stat-card'>");
        out.println("<div class='stat-icon' style='color: #667eea;'><i class='fas fa-envelope'></i></div>");
        out.println("<div class='stat-number' style='color: #667eea;'>" + totalContacts + "</div>");
        out.println("<div class='stat-label'>Tổng tin nhắn</div>");
        if (unreadContacts > 0) {
            out.println("<div style='color: #e53e3e; margin-top: 0.5rem;'>" + unreadContacts + " tin nhắn mới</div>");
        }
        out.println("</div>");
        
        out.println("<div class='stat-card'>");
        out.println("<div class='stat-icon' style='color: #38a169;'><i class='fas fa-tasks'></i></div>");
        out.println("<div class='stat-number' style='color: #38a169;'>" + completedAssignments + "/9</div>");
        out.println("<div class='stat-label'>Bài tập hoàn thành</div>");
        out.println("<div style='margin-top: 0.5rem;'>" + String.format("%.1f", completionPercentage) + "% tiến độ</div>");
        out.println("</div>");
        
        out.println("<div class='stat-card'>");
        out.println("<div class='stat-icon' style='color: #f093fb;'><i class='fas fa-chart-line'></i></div>");
        out.println("<div class='stat-number' style='color: #f093fb;'>" + String.format("%.0f", completionPercentage) + "%</div>");
        out.println("<div class='stat-label'>Tiến độ học tập</div>");
        out.println("</div>");
        
        out.println("</div>");
        
        // Quick actions
        out.println("<h2>Thao tác nhanh</h2>");
        out.println("<div class='quick-actions'>");
        
        out.println("<div class='action-card'>");
        out.println("<h3><i class='fas fa-envelope'></i> Quản lý tin nhắn</h3>");
        out.println("<p>Xem và trả lời tin nhắn từ khách hàng</p>");
        out.println("<a href='contacts' class='btn btn-primary'>Xem tin nhắn (" + unreadContacts + " mới)</a>");
        out.println("</div>");
        
        out.println("<div class='action-card'>");
        out.println("<h3><i class='fas fa-tasks'></i> Quản lý bài tập</h3>");
        out.println("<p>Chấm điểm và feedback cho bài tập</p>");
        out.println("<a href='assignments' class='btn btn-primary'>Quản lý bài tập</a>");
        out.println("</div>");
        
        out.println("</div>");
        out.println("</div>");
        out.println("</main>");
        
        out.println("</body>");
        out.println("</html>");
    }

    private void showContacts(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        List<Contact> contacts = contactDAO.findAll();
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Quản lý tin nhắn - Admin</title>");
        out.println("<link rel='stylesheet' href='../style.css'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css' rel='stylesheet'>");
        out.println("<style>");
        out.println(".contact-item { background: white; margin-bottom: 1rem; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }");
        out.println(".contact-header { padding: 1rem; background: #f8f9fa; border-bottom: 1px solid #dee2e6; display: flex; justify-content: space-between; align-items: center; }");
        out.println(".contact-body { padding: 1rem; }");
        out.println(".unread { border-left: 4px solid #e53e3e; }");
        out.println(".read { border-left: 4px solid #38a169; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body style='padding-top: 100px;'>");
        
        // Navigation
        out.println("<nav class='navbar'>");
        out.println("<div class='nav-container'>");
        out.println("<div class='nav-logo'><a href='dashboard'>Admin</a></div>");
        out.println("<div class='nav-menu'>");
        out.println("<a href='dashboard' class='nav-link'>Dashboard</a>");
        out.println("<a href='contacts' class='nav-link'>Liên hệ</a>");
        out.println("<a href='assignments' class='nav-link'>Bài tập</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");
        
        out.println("<div class='container'>");
        out.println("<h1>Quản lý tin nhắn (" + contacts.size() + ")</h1>");
        
        if (contacts.isEmpty()) {
            out.println("<div class='text-center' style='padding: 3rem;'>");
            out.println("<i class='fas fa-inbox' style='font-size: 4rem; color: #ccc;'></i>");
            out.println("<p style='color: #666; margin-top: 1rem;'>Chưa có tin nhắn nào</p>");
            out.println("</div>");
        } else {
            for (Contact contact : contacts) {
                String statusClass = contact.isRead() ? "read" : "unread";
                
                out.println("<div class='contact-item " + statusClass + "'>");
                out.println("<div class='contact-header'>");
                out.println("<div>");
                out.println("<strong>" + contact.getName() + "</strong> &lt;" + contact.getEmail() + "&gt;");
                out.println("<div style='font-size: 0.9rem; color: #666;'>" + contact.getFormattedDate() + "</div>");
                out.println("</div>");
                out.println("<div>");
                if (!contact.isRead()) {
                    out.println("<form method='post' action='mark-read' style='display: inline;'>");
                    out.println("<input type='hidden' name='contactId' value='" + contact.getId() + "'>");
                    out.println("<button type='submit' class='btn btn-secondary' style='padding: 0.25rem 0.5rem; font-size: 0.8rem;'>Đánh dấu đã đọc</button>");
                    out.println("</form>");
                } else {
                    out.println("<span style='color: #38a169;'><i class='fas fa-check'></i> Đã đọc</span>");
                }
                out.println("</div>");
                out.println("</div>");
                
                out.println("<div class='contact-body'>");
                out.println("<h4>" + contact.getSubject() + "</h4>");
                out.println("<p>" + contact.getMessage().replace("\n", "<br>") + "</p>");
                if (contact.getIpAddress() != null) {
                    out.println("<small style='color: #666;'>IP: " + contact.getIpAddress() + "</small>");
                }
                out.println("</div>");
                out.println("</div>");
            }
        }
        
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    private void showAssignments(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        List<Assignment> assignments = assignmentDAO.findAll();
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Quản lý bài tập - Admin</title>");
        out.println("<link rel='stylesheet' href='../style.css'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body style='padding-top: 100px;'>");
        
        // Navigation
        out.println("<nav class='navbar'>");
        out.println("<div class='nav-container'>");
        out.println("<div class='nav-logo'><a href='dashboard'>Admin</a></div>");
        out.println("<div class='nav-menu'>");
        out.println("<a href='dashboard' class='nav-link'>Dashboard</a>");
        out.println("<a href='contacts' class='nav-link'>Liên hệ</a>");
        out.println("<a href='assignments' class='nav-link'>Bài tập</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");
        
        out.println("<div class='container'>");
        out.println("<h1>Quản lý bài tập</h1>");
        
        out.println("<div class='assignments-grid'>");
        for (Assignment assignment : assignments) {
            out.println("<div class='assignment-card'>");
            out.println("<div class='assignment-header'>");
            out.println("<h3>" + assignment.getWeekDisplay() + "</h3>");
            out.println("<span class='assignment-status " + assignment.getStatus().getCssClass() + "'>");
            out.println(assignment.getStatus().getDisplayName());
            out.println("</span>");
            out.println("</div>");
            
            out.println("<div class='assignment-content'>");
            out.println("<h4>" + assignment.getTitle() + "</h4>");
            out.println("<p>" + assignment.getDescription() + "</p>");
            
            if (assignment.hasSubmission()) {
                out.println("<p><strong>Bài nộp:</strong></p>");
                out.println("<a href='" + assignment.getSubmissionLink() + "' target='_blank' class='assignment-link'>");
                out.println("<i class='fas fa-external-link-alt'></i> " + assignment.getSubmissionLink());
                out.println("</a>");
                
                // Grading form
                out.println("<form method='post' action='grade-assignment' style='margin-top: 1rem; padding: 1rem; background: #f8f9fa; border-radius: 6px;'>");
                out.println("<input type='hidden' name='assignmentId' value='" + assignment.getId() + "'>");
                out.println("<div style='margin-bottom: 1rem;'>");
                out.println("<label>Điểm số (0-100):</label>");
                out.println("<input type='number' name='score' min='0' max='100' value='" + (assignment.getScore() >= 0 ? assignment.getScore() : "") + "' style='width: 80px; margin-left: 0.5rem;'>");
                out.println("</div>");
                out.println("<div style='margin-bottom: 1rem;'>");
                out.println("<label>Feedback:</label>");
                out.println("<textarea name='feedback' rows='2' style='width: 100%; margin-top: 0.5rem;'>" + (assignment.getFeedback() != null ? assignment.getFeedback() : "") + "</textarea>");
                out.println("</div>");
                out.println("<button type='submit' class='btn btn-primary'>Chấm điểm</button>");
                out.println("</form>");
            }
            
            if (assignment.isGraded()) {
                out.println("<div style='margin-top: 1rem; padding: 1rem; background: #d4edda; border-radius: 6px;'>");
                out.println("<strong>Điểm:</strong> " + assignment.getScoreDisplay());
                if (assignment.getFeedback() != null && !assignment.getFeedback().isEmpty()) {
                    out.println("<br><strong>Feedback:</strong> " + assignment.getFeedback());
                }
                out.println("</div>");
            }
            
            out.println("</div>");
            out.println("</div>");
        }
        out.println("</div>");
        
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    private void markContactAsRead(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int contactId = Integer.parseInt(request.getParameter("contactId"));
            contactDAO.markAsRead(contactId);
            response.sendRedirect("contacts?success=marked-read");
        } catch (Exception e) {
            response.sendRedirect("contacts?error=mark-read-failed");
        }
    }

    private void gradeAssignment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
            int score = Integer.parseInt(request.getParameter("score"));
            String feedback = request.getParameter("feedback");
            
            assignmentDAO.updateGrade(assignmentId, score, feedback);
            response.sendRedirect("assignments?success=graded");
        } catch (Exception e) {
            response.sendRedirect("assignments?error=grade-failed");
        }
    }
}
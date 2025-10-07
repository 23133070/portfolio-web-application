package com.portfolio.servlet;

import com.portfolio.dao.AssignmentDAO;
import com.portfolio.dao.AssignmentDAOImpl;
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
 * Servlet to handle assignment operations
 * Manages assignments for 9 weeks (Week 1-9)
 */
public class AssignmentServlet extends HttpServlet {
    
    private AssignmentDAO assignmentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.assignmentDAO = new AssignmentDAOImpl();
    }

    /**
     * Handle GET requests - list assignments, show assignment details
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String action = pathInfo != null ? pathInfo.substring(1) : "list";
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        switch (action) {
            case "list":
                listAssignments(request, response);
                break;
            case "view":
                viewAssignment(request, response);
                break;
            case "json":
                getAssignmentsAsJSON(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handle POST requests - update assignment submission
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String action = pathInfo != null ? pathInfo.substring(1) : "";
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        switch (action) {
            case "submit":
                submitAssignment(request, response);
                break;
            case "update-status":
                updateStatus(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * List all assignments
     */
    private void listAssignments(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        List<Assignment> assignments = assignmentDAO.findAll();
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Danh sách Bài tập - Portfolio</title>");
        out.println("<link rel='stylesheet' href='../style.css'>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body>");
        
        // Navigation
        out.println("<nav class='navbar'>");
        out.println("<div class='nav-container'>");
        out.println("<div class='nav-logo'><a href='../index.html'>Portfolio</a></div>");
        out.println("<div class='nav-menu'>");
        out.println("<a href='../index.html' class='nav-link'>Trang chủ</a>");
        out.println("<a href='../admin/' class='nav-link'>Admin</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");
        
        // Main content
        out.println("<main style='padding-top: 100px;'>");
        out.println("<div class='container'>");
        out.println("<h1 class='section-title'>Danh sách Bài tập</h1>");
        
        // Statistics
        double completionPercentage = assignmentDAO.getCompletionPercentage();
        int completedCount = assignmentDAO.countByStatus(Assignment.AssignmentStatus.COMPLETED);
        int totalCount = assignments.size();
        
        out.println("<div class='stats-card' style='background: white; padding: 2rem; border-radius: 12px; margin-bottom: 2rem; box-shadow: 0 4px 6px rgba(0,0,0,0.1);'>");
        out.println("<h3>Tiến độ học tập</h3>");
        out.println("<div style='display: flex; gap: 2rem; margin-top: 1rem;'>");
        out.println("<div><strong>Hoàn thành:</strong> " + completedCount + "/" + totalCount + " bài</div>");
        out.println("<div><strong>Tiến độ:</strong> " + String.format("%.1f", completionPercentage) + "%</div>");
        out.println("</div>");
        out.println("<div style='background: #f0f0f0; height: 10px; border-radius: 5px; margin-top: 1rem; overflow: hidden;'>");
        out.println("<div style='background: linear-gradient(90deg, #667eea, #764ba2); height: 100%; width: " + completionPercentage + "%; transition: width 0.3s ease;'></div>");
        out.println("</div>");
        out.println("</div>");
        
        // Assignments grid
        out.println("<div class='assignments-grid'>");
        
        for (Assignment assignment : assignments) {
            String statusClass = assignment.getStatus().getCssClass();
            String statusDisplay = assignment.getStatus().getDisplayName();
            
            out.println("<div class='assignment-card " + statusClass + "'>");
            out.println("<div class='assignment-header'>");
            out.println("<h3>" + assignment.getWeekDisplay() + "</h3>");
            out.println("<span class='assignment-status " + statusClass + "'>" + statusDisplay + "</span>");
            out.println("</div>");
            
            out.println("<div class='assignment-content'>");
            out.println("<h4>" + assignment.getTitle() + "</h4>");
            out.println("<p>" + assignment.getShortDescription() + "</p>");
            
            if (assignment.hasSubmission()) {
                out.println("<div class='assignment-links'>");
                out.println("<a href='" + assignment.getSubmissionLink() + "' target='_blank' class='assignment-link'>");
                out.println("<i class='fas fa-external-link-alt'></i> Xem bài nộp");
                out.println("</a>");
                out.println("</div>");
            }
            
            // Submission form
            out.println("<form method='post' action='assignment/submit' style='margin-top: 1rem;'>");
            out.println("<input type='hidden' name='assignmentId' value='" + assignment.getId() + "'>");
            out.println("<div style='display: flex; gap: 0.5rem;'>");
            out.println("<input type='url' name='submissionLink' placeholder='Link bài làm...' ");
            out.println("value='" + (assignment.getSubmissionLink() != null ? assignment.getSubmissionLink() : "") + "' ");
            out.println("style='flex: 1; padding: 0.5rem; border: 1px solid #ddd; border-radius: 6px;'>");
            out.println("<button type='submit' class='btn btn-primary' style='padding: 0.5rem 1rem;'>Nộp bài</button>");
            out.println("</div>");
            out.println("</form>");
            
            if (assignment.isGraded()) {
                out.println("<div style='margin-top: 1rem; padding: 1rem; background: #f8f9fa; border-radius: 6px;'>");
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
        out.println("</main>");
        
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * View specific assignment details
     */
    private void viewAssignment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String weekParam = request.getParameter("week");
        if (weekParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing week parameter");
            return;
        }
        
        try {
            int week = Integer.parseInt(weekParam);
            Assignment assignment = assignmentDAO.findByWeek(week);
            
            if (assignment == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Assignment not found");
                return;
            }
            
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang='vi'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>" + assignment.getTitle() + " - Portfolio</title>");
            out.println("<link rel='stylesheet' href='../style.css'>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<div class='container' style='padding-top: 2rem;'>");
            out.println("<h1>" + assignment.getWeekDisplay() + ": " + assignment.getTitle() + "</h1>");
            out.println("<div class='assignment-details'>");
            out.println("<p><strong>Mô tả:</strong> " + assignment.getDescription() + "</p>");
            out.println("<p><strong>Trạng thái:</strong> " + assignment.getStatus().getDisplayName() + "</p>");
            
            if (assignment.hasSubmission()) {
                out.println("<p><strong>Bài nộp:</strong> <a href='" + assignment.getSubmissionLink() + "' target='_blank'>" + assignment.getSubmissionLink() + "</a></p>");
            }
            
            if (assignment.isGraded()) {
                out.println("<p><strong>Điểm:</strong> " + assignment.getScoreDisplay() + "</p>");
                if (assignment.getFeedback() != null) {
                    out.println("<p><strong>Feedback:</strong> " + assignment.getFeedback() + "</p>");
                }
            }
            
            out.println("</div>");
            out.println("<a href='list' class='btn btn-secondary'>Quay lại danh sách</a>");
            out.println("</div>");
            
            out.println("</body>");
            out.println("</html>");
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid week number");
        }
    }

    /**
     * Submit assignment link
     */
    private void submitAssignment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        try {
            int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
            String submissionLink = request.getParameter("submissionLink");
            
            if (submissionLink == null || submissionLink.trim().isEmpty()) {
                response.sendRedirect("assignment/list?error=empty-link");
                return;
            }
            
            Assignment assignment = assignmentDAO.findById(assignmentId);
            if (assignment == null) {
                response.sendRedirect("assignment/list?error=not-found");
                return;
            }
            
            // Validate URL
            if (!assignment.isValidSubmissionLink(submissionLink)) {
                response.sendRedirect("assignment/list?error=invalid-url");
                return;
            }
            
            // Update assignment
            boolean success = assignmentDAO.updateSubmissionLink(assignmentId, submissionLink.trim());
            
            if (success) {
                response.sendRedirect("assignment/list?success=submitted");
            } else {
                response.sendRedirect("assignment/list?error=save-failed");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("assignment/list?error=invalid-id");
        } catch (Exception e) {
            System.err.println("Error submitting assignment: " + e.getMessage());
            response.sendRedirect("assignment/list?error=system-error");
        }
    }

    /**
     * Update assignment status (for admin)
     */
    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        try {
            int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
            String statusStr = request.getParameter("status");
            
            Assignment.AssignmentStatus status = Assignment.AssignmentStatus.valueOf(statusStr);
            boolean success = assignmentDAO.updateStatus(assignmentId, status);
            
            if (success) {
                response.sendRedirect("assignment/list?success=status-updated");
            } else {
                response.sendRedirect("assignment/list?error=update-failed");
            }
            
        } catch (Exception e) {
            System.err.println("Error updating assignment status: " + e.getMessage());
            response.sendRedirect("assignment/list?error=system-error");
        }
    }

    /**
     * Get assignments as JSON for AJAX requests
     */
    private void getAssignmentsAsJSON(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        List<Assignment> assignments = assignmentDAO.findAll();
        
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"assignments\": [");
        
        for (int i = 0; i < assignments.size(); i++) {
            Assignment a = assignments.get(i);
            if (i > 0) out.println(",");
            
            out.println("{");
            out.println("\"id\": " + a.getId() + ",");
            out.println("\"week\": " + a.getWeek() + ",");
            out.println("\"title\": \"" + escapeJSON(a.getTitle()) + "\",");
            out.println("\"description\": \"" + escapeJSON(a.getDescription()) + "\",");
            out.println("\"status\": \"" + a.getStatus().name() + "\",");
            out.println("\"statusDisplay\": \"" + a.getStatus().getDisplayName() + "\",");
            out.println("\"hasSubmission\": " + a.hasSubmission() + ",");
            out.println("\"submissionLink\": \"" + (a.getSubmissionLink() != null ? escapeJSON(a.getSubmissionLink()) : "") + "\",");
            out.println("\"isGraded\": " + a.isGraded() + ",");
            out.println("\"score\": " + a.getScore());
            out.println("}");
        }
        
        out.println("],");
        out.println("\"statistics\": {");
        out.println("\"total\": " + assignments.size() + ",");
        out.println("\"completed\": " + assignmentDAO.countByStatus(Assignment.AssignmentStatus.COMPLETED) + ",");
        out.println("\"completionPercentage\": " + assignmentDAO.getCompletionPercentage());
        out.println("}");
        out.println("}");
    }

    /**
     * Escape string for JSON output
     */
    private String escapeJSON(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
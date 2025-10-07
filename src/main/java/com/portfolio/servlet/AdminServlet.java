package com.portfolio.servlet;

import com.portfolio.dao.ContactDAO;
import com.portfolio.dao.ContactDAOImpl;
import com.portfolio.model.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Admin servlet for managing contacts
 */
public class AdminServlet extends HttpServlet {
    
    private ContactDAO contactDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.contactDAO = new ContactDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            showDashboard(request, response);
        } else if (pathInfo.startsWith("/contacts")) {
            showContacts(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.startsWith("/contacts/delete/")) {
            String contactId = pathInfo.substring("/contacts/delete/".length());
            deleteContact(request, response, contactId);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int totalContacts = contactDAO.count();
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Admin Dashboard - Portfolio</title>");
        out.println("    <style>");
        out.println("        * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; }");
        out.println("        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }");
        out.println("        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 2rem; border-radius: 10px; margin-bottom: 2rem; }");
        out.println("        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 1.5rem; margin-bottom: 2rem; }");
        out.println("        .stat-card { background: white; padding: 1.5rem; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("        .stat-number { font-size: 2rem; font-weight: bold; color: #667eea; }");
        out.println("        .stat-label { color: #666; margin-top: 0.5rem; }");
        out.println("        .nav-links { display: flex; gap: 1rem; }");
        out.println("        .nav-link { background: white; color: #667eea; padding: 0.75rem 1.5rem; border-radius: 5px; text-decoration: none; font-weight: 500; }");
        out.println("        .nav-link:hover { background: #667eea; color: white; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='container'>");
        out.println("        <div class='header'>");
        out.println("            <h1>📊 Admin Dashboard</h1>");
        out.println("            <p>Quản lý trang web cá nhân</p>");
        out.println("        </div>");
        out.println("        <div class='stats-grid'>");
        out.println("            <div class='stat-card'>");
        out.println("                <div class='stat-number'>" + totalContacts + "</div>");
        out.println("                <div class='stat-label'>Tổng số liên hệ</div>");
        out.println("            </div>");
        out.println("        </div>");
        out.println("        <div class='nav-links'>");
        out.println("            <a href='/portfolio/admin/contacts' class='nav-link'>📧 Quản lý liên hệ</a>");
        out.println("            <a href='/portfolio/' class='nav-link'>🏠 Về trang chủ</a>");
        out.println("        </div>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    private void showContacts(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        List<Contact> contacts = contactDAO.findAll();
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='vi'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Quản lý Liên hệ - Admin</title>");
        out.println("    <style>");
        out.println("        * { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; }");
        out.println("        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }");
        out.println("        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 2rem; border-radius: 10px; margin-bottom: 2rem; }");
        out.println("        .contact-grid { display: grid; gap: 1rem; }");
        out.println("        .contact-card { background: white; padding: 1.5rem; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println("        .contact-header { display: flex; justify-content: between; align-items: center; margin-bottom: 1rem; }");
        out.println("        .contact-name { font-size: 1.2rem; font-weight: bold; color: #333; }");
        out.println("        .contact-email { color: #667eea; }");
        out.println("        .contact-message { margin-top: 1rem; padding: 1rem; background: #f8f9fa; border-radius: 5px; }");
        out.println("        .delete-btn { background: #dc3545; color: white; border: none; padding: 0.5rem 1rem; border-radius: 5px; cursor: pointer; }");
        out.println("        .back-btn { background: #667eea; color: white; padding: 0.75rem 1.5rem; border-radius: 5px; text-decoration: none; display: inline-block; margin-bottom: 1rem; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='container'>");
        out.println("        <a href='/portfolio/admin/' class='back-btn'>← Về Dashboard</a>");
        out.println("        <div class='header'>");
        out.println("            <h1>📧 Quản lý Liên hệ</h1>");
        out.println("            <p>Tổng có " + contacts.size() + " liên hệ</p>");
        out.println("        </div>");
        out.println("        <div class='contact-grid'>");
        
        for (Contact contact : contacts) {
            out.println("            <div class='contact-card'>");
            out.println("                <div class='contact-header'>");
            out.println("                    <div>");
            out.println("                        <div class='contact-name'>" + escapeHtml(contact.getName()) + "</div>");
            out.println("                        <div class='contact-email'>" + escapeHtml(contact.getEmail()) + "</div>");
            out.println("                    </div>");
            out.println("                    <form method='POST' action='/portfolio/admin/contacts/delete/" + contact.getId() + "' style='margin: 0;'>");
            out.println("                        <button type='submit' class='delete-btn' onclick='return confirm(\"Xác nhận xóa liên hệ này?\")'>Xóa</button>");
            out.println("                    </form>");
            out.println("                </div>");
            out.println("                <div class='contact-message'>");
            out.println("                    <strong>Tin nhắn:</strong><br>");
            out.println("                    " + escapeHtml(contact.getMessage()));
            out.println("                </div>");
            out.println("                <small style='color: #666; margin-top: 1rem; display: block;'>Gửi lúc: " + contact.getSubmittedAt() + "</small>");
            out.println("            </div>");
        }
        
        if (contacts.isEmpty()) {
            out.println("            <div class='contact-card'>");
            out.println("                <p style='text-align: center; color: #666;'>Chưa có liên hệ nào.</p>");
            out.println("            </div>");
        }
        
        out.println("        </div>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    private void deleteContact(HttpServletRequest request, HttpServletResponse response, String contactId)
            throws IOException {
        try {
            int id = Integer.parseInt(contactId);
            boolean deleted = contactDAO.delete(id);
            
            if (deleted) {
                response.sendRedirect("/portfolio/admin/contacts?deleted=success");
            } else {
                response.sendRedirect("/portfolio/admin/contacts?error=notfound");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/portfolio/admin/contacts?error=invalid");
        }
    }
    
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#x27;");
    }
}
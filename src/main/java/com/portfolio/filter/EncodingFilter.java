package com.portfolio.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

/**
 * Filter to set UTF-8 encoding for all requests and responses
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null) {
            encoding = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Set request encoding
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
        }
        
        // Set response encoding
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=" + encoding);
        
        // Continue with the chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}
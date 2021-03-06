package ecare.config;

import javax.servlet.*;
import java.io.IOException;

/**
 * This is character servlet filter to convert UTF-8 characters in all requests\responses
 */

public class CharacterFilter implements Filter {

    //init method of charFilter
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    //destroy method of charFilter
    @Override
    public void destroy() {
        //do nothing
    }

    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }

}
package filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/secure/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        String loginURI = httpRequest.getContextPath() + "/login.jsp";
        String requestURI = httpRequest.getRequestURI();

        // Allow access to login page and authentication servlet
        boolean loginRequest = requestURI.endsWith("login.jsp") || requestURI.endsWith("LoginServlet");

        if (loggedIn || loginRequest) {
            chain.doFilter(request, response); // Allow request to proceed
        } else {
            httpResponse.sendRedirect(loginURI); // Redirect to login page
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}


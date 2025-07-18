package src.danik.userservice.config.user;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class UserHeaderFilter implements Filter {
    private final UserContext userContext;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String userId = httpServletRequest.getHeader("social-user-id");
        if (userId != null && !userId.isEmpty()) {
            userContext.setUserIdVault(Long.parseLong(userId));
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            userContext.clearThreadLocal();
        }
    }
}
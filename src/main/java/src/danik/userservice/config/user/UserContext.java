package src.danik.userservice.config.user;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private final ThreadLocal<Long> userIdVault = new ThreadLocal<>();

    public void setUserIdVault(long userId) {
        userIdVault.set(userId);
    }
    public Long getUserIdVault() {
        return userIdVault.get();
    }
    public void clearThreadLocal() {
        userIdVault.remove();
    }
}

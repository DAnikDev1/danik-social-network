package src.danik.userservice.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import src.danik.userservice.config.user.UserContext;

@RequiredArgsConstructor
public class FeignUserInterceptor implements RequestInterceptor {
    private final UserContext userContext;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = userContext.getUserIdVault();
        if(userId != null) {
            requestTemplate.header("social-user-id", userId.toString());
        }
    }
}

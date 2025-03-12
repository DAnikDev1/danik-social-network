package src.danik.userservice.config.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import src.danik.userservice.config.user.UserContext;

@Configuration
public class FeignConfig {
    @Bean
    public FeignUserInterceptor feignUserInterceptor(UserContext userContext) {
        return new FeignUserInterceptor(userContext);
    }
}

package src.danik.userservice.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Set;

@FeignClient(name = "post-microservice", url = "${post.microservice.url}", configuration = FeignConfig.class)
public interface FeignPostServiceConnect {
    @GetMapping("/internal/popular_posts")
    Set<Long> getUserIdsFromPopularPosts(@RequestHeader("system-key") String systemKey);
}

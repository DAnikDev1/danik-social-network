package src.danik.userservice.scheduler;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import src.danik.userservice.config.feign.FeignPostServiceConnect;
import src.danik.userservice.service.cache.CacheService;
import src.danik.userservice.service.user.UserService;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheScheduler {
    private final CacheService cacheService;
    private final FeignPostServiceConnect postServiceConnect;
    private final UserService userService;

    @Value("${system.key}")
    private String systemKey;

    @Scheduled(initialDelay = 5000, fixedDelay = 600000)
    public void generatePopularUsersCache() {
        log.info("Generating hot cache for popular users");
        try {
            cacheService.clearCache("popularUsers");

            try {
                Set<Long> ids = postServiceConnect.getUserIdsFromPopularPosts(systemKey);
                ids.forEach(id -> {
                    cacheService.putInCache("popularUsers", id, userService.getUserById(id));
                });
                
                log.info("{} users was cached", ids.size());
            } catch (FeignException e) {
                log.warn("Failed to fetch popular users from PostService: {}", e.getMessage());
            }
        } catch (Exception e) {
            log.error("Error to generate hot cache", e);
        }
    }
}

package src.danik.userservice.service.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {
    private final CacheManager cacheManager;

    public <T> T getFromCache(String cacheName, Object key, Class<T> type) {
        return Optional.ofNullable(cacheManager.getCache(cacheName))
                .map(cache -> cache.get(key, type))
                .orElse(null);
    }

    public void putInCache(String cacheName, Object key, Object value) {
        log.info("Putting {} in cache {} with key = {}", value, cacheName, key);
        Optional.ofNullable(cacheManager.getCache(cacheName))
                .ifPresent(cache -> cache.put(key, value));
    }

    public void clearCache(String cacheName) {
        Optional.ofNullable(cacheManager.getCache(cacheName))
                .ifPresent(Cache::clear);
    }
}

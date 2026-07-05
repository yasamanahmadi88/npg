package ix.portal.npg.config;

import ix.portal.npg.repository.UserRepository;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final GitProperties gitProperties;
    private final BuildProperties buildProperties;

    public CacheConfiguration(
        @Autowired(required = false) GitProperties gitProperties,
        @Autowired(required = false) BuildProperties buildProperties
    ) {
        this.gitProperties = gitProperties;
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }

    @Bean
    public InitializingBean userRepositoryCacheInitializer(CacheManager cacheManager) {
        return () -> {
            if (cacheManager instanceof SpringEmbeddedCacheManager springEmbeddedCacheManager) {
                EmbeddedCacheManager nativeCacheManager = springEmbeddedCacheManager.getNativeCacheManager();

                defineCacheIfMissing(nativeCacheManager, UserRepository.USERS_BY_LOGIN_CACHE);
                defineCacheIfMissing(nativeCacheManager, UserRepository.USERS_BY_EMAIL_CACHE);
            }
        };
    }

    private void defineCacheIfMissing(EmbeddedCacheManager cacheManager, String cacheName) {
        if (cacheManager.getCacheConfiguration(cacheName) == null) {
            cacheManager.defineConfiguration(cacheName, new ConfigurationBuilder().build());
        }
    }
}
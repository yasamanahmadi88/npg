package ix.portal.npg.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import ix.portal.npg.security.jwt.SessionInfo;
import ix.portal.npg.service.SettingQueryService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityCache {

    @Value("${bucket4j.get.bucket-size}")
    long bucketSizeForGet;

    @Value("${bucket4j.get.token-per-minute}")
    long tokenPerMinuteForGet;

    @Value("${bucket4j.post.bucket-size}")
    long bucketSizeForPost;

    @Value("${bucket4j.post.token-per-minute}")
    long tokenPerMinuteForPost;

    private final SettingQueryService settingQueryService;

    private Map<String, SessionInfo> sessionInfos = new ConcurrentHashMap<>();

    public SecurityCache(SettingQueryService settingQueryService) {
        this.settingQueryService = settingQueryService;
    }

    public void storeSession(
        Object principal,
        String sessionId,
        String ip,
        String username,
        String token,
        String userAgent,
        LocalDateTime login,
        LocalDateTime logout,
        Boolean validToken
    ) {
        sessionInfos.put(
            token,
            new SessionInfo(
                principal,
                sessionId,
                ip,
                username,
                token,
                userAgent,
                login,
                logout,
                validToken,
                login,
                createNewBucket("get"),
                createNewBucket("post")
            )
        );
    }

    public void removeSession(String jwtToken) {
        if (sessionInfos.get(jwtToken) != null) {
            sessionInfos.remove(jwtToken);
        }
    }

    public List<SessionInfo> getAllSessionInfo() {
        try {
            sessionInfos.forEach(
                (s, sessionInfo) -> {
                    if (isTokenExpired(sessionInfo)) removeSession(s);
                }
            );
            return new ArrayList<>(sessionInfos.values());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public SessionInfo getSessionInfoByToken(String jwtToken) {
        try {
            if (jwtToken == null || sessionInfos.size() == 0) return null;

            if (sessionInfos.get(jwtToken) != null) {
                SessionInfo sessionInfo = sessionInfos.get(jwtToken);
                if (isTokenExpired(sessionInfo)) removeSession(jwtToken); else return sessionInfo.setLastActionDate(LocalDateTime.now());
            }

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Boolean hasConcurrentSession(String username) {
        try {
            if (username == null) return false;
            Map<String, SessionInfo> result = sessionInfos
                .entrySet()
                .stream()
                .filter(map -> username.equalsIgnoreCase(map.getValue().getUsername()))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

            return !result.isEmpty();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public SessionInfo fetchSessionInfo(String username) {
        try {
            if (username == null) return null;
            Map<String, SessionInfo> result = sessionInfos
                .entrySet()
                .stream()
                .filter(map -> username.equalsIgnoreCase(map.getValue().getUsername()))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
            return result.values().stream().findFirst().get();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean isTokenExpired(SessionInfo sessionInfo) {
        return sessionInfo.getLastActionDate().isBefore(LocalDateTime.now().minusMinutes(30)); //token inactivity expiration time, current = 30 minutes
    }

    private Bucket createNewBucket(String reqType) {
        Refill refill;
        Bandwidth limit;

        settingQueryService
            .findByKey("bucket4j.get.token-per-minute")
            .ifPresent(settingDTO -> tokenPerMinuteForGet = Long.parseLong(settingDTO.getValue()));

        settingQueryService
            .findByKey("bucket4j.get.bucket-size")
            .ifPresent(settingDTO -> bucketSizeForGet = Long.parseLong(settingDTO.getValue()));

        settingQueryService
            .findByKey("bucket4j.post.token-per-minute")
            .ifPresent(settingDTO -> tokenPerMinuteForPost = Long.parseLong(settingDTO.getValue()));

        settingQueryService
            .findByKey("bucket4j.post.bucket-size")
            .ifPresent(settingDTO -> bucketSizeForPost = Long.parseLong(settingDTO.getValue()));

        if (reqType.equals("get")) {
            refill = Refill.greedy(tokenPerMinuteForGet, Duration.ofMinutes(1));
            limit = Bandwidth.classic(bucketSizeForGet, refill);
        } else {
            refill = Refill.greedy(tokenPerMinuteForPost, Duration.ofMinutes(1));
            limit = Bandwidth.classic(bucketSizeForPost, refill);
        }

        return Bucket.builder().addLimit(limit).build();
    }
}



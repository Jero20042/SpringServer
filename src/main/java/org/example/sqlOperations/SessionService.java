package org.example.sqlOperations;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private final Map<String, Session> sessions =
            new ConcurrentHashMap<>();

    public String createSession(String login) {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        Instant expiresAt = Instant.now()
                .plus(Duration.ofHours(1));

        sessions.put(token, new Session(login, expiresAt));

        return token;
    }

    public record Session(String login, Instant expiresAt) {
    }
    public Optional<String> getLoginByToken(String token) {
        Session session = sessions.get(token);

        if (session == null || session.expiresAt().isBefore(Instant.now())) {
            sessions.remove(token);
            return Optional.empty();
        }

        return Optional.of(session.login());
    }
}
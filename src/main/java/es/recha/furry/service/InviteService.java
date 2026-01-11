package es.recha.furry.service;

import es.recha.furry.model.Invite;
import es.recha.furry.model.User;
import es.recha.furry.repositories.InviteRepository;
import es.recha.furry.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class InviteService {

    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;

    public InviteService(InviteRepository inviteRepository, UserRepository userRepository) {
        this.inviteRepository = inviteRepository;
        this.userRepository = userRepository;
    }

    public record CreatedInvite(String token, Instant expiresAt) {}

    @Transactional
    public CreatedInvite createInvite(String email, Long createdByUserId, int daysValid) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email es obligatorio");
        }
        if (createdByUserId == null) {
            throw new IllegalArgumentException("createdByUserId es obligatorio");
        }

        User creator = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new IllegalArgumentException("creator no existe"));

        String token = randomToken(32);
        String tokenHash = sha256Hex(token);

        Invite invite = new Invite();
        invite.setEmail(email);
        invite.setTokenHash(tokenHash);
        invite.setCreatedBy(creator);
        invite.setExpiresAt(Instant.now().plus(Math.max(daysValid, 1), ChronoUnit.DAYS));

        inviteRepository.save(invite);
        return new CreatedInvite(token, invite.getExpiresAt());
    }

    private String randomToken(int bytes) {
        byte[] b = new byte[bytes];
        new SecureRandom().nextBytes(b);
        return toHex(b);
    }

    private String sha256Hex(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(s.getBytes(StandardCharsets.UTF_8));
            return toHex(hash);
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte x : bytes) sb.append(String.format("%02x", x));
        return sb.toString();
    }
}

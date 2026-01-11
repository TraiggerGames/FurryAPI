package es.recha.furry.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="invite",
        uniqueConstraints = @UniqueConstraint(name="ux_invite_token_hash", columnNames="token_hash"))
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=180)
    private String email;

    @Column(name="token_hash", nullable=false, length=64)
    private String tokenHash;

    @Column(name="expires_at", nullable=false)
    private Instant expiresAt;

    @Column(name="used_at")
    private Instant usedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="created_by", nullable=false)
    private User createdBy;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    @PrePersist void prePersist() { this.createdAt = Instant.now(); }

    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTokenHash() { return tokenHash; }
    public void setTokenHash(String tokenHash) { this.tokenHash = tokenHash; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public Instant getUsedAt() { return usedAt; }
    public void setUsedAt(Instant usedAt) { this.usedAt = usedAt; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public Instant getCreatedAt() { return createdAt; }
}

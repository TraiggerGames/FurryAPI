package es.recha.furry.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


}

package es.recha.furry.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="universo_member",
        uniqueConstraints = @UniqueConstraint(name="ux_universo_member", columnNames={"universo_id","user_id"}))
public class UniversoMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="universo_id", nullable=false)
    private Universo universo;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable=false, length=20)
    private String permission = "VIEW"; // VIEW / EDIT / MANAGE

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    @PrePersist void prePersist() { this.createdAt = Instant.now(); }

    public Long getId() { return id; }

    public Universo getUniverso() { return universo; }
    public void setUniverso(Universo universo) { this.universo = universo; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }

    public Instant getCreatedAt() { return createdAt; }
}

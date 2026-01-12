package es.recha.furry.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


}

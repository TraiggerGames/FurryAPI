package es.recha.furry.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="universo",
        uniqueConstraints = {
                @UniqueConstraint(name="ux_universo_slug", columnNames="slug"),
                @UniqueConstraint(name="ux_universo_nombre", columnNames="nombre")
        })
public class Universo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=120)
    private String nombre;

    @Column(nullable=false, length=160)
    private String slug;

    @Column(columnDefinition="TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="visibility_code", nullable=false)
    private Visibility visibility;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="status_code", nullable=false)
    private ContentStatus status;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    @Column(name="updated_at")
    private Instant updatedAt;

    @PrePersist void prePersist() { this.createdAt = Instant.now(); }
    @PreUpdate void preUpdate() { this.updatedAt = Instant.now(); }

}

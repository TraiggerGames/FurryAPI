package es.recha.furry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="furry",
        uniqueConstraints = @UniqueConstraint(name="ux_furry_slug", columnNames="slug"))
public class Furry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=120)
    private String nombre;

    @Column(nullable=false, length=180)
    private String slug;

    @Column(length=120)
    private String alias;

    @Column(columnDefinition="TEXT")
    private String descripcion;

    @Column(name="imagen_url", length=512)
    private String imagenUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="tipo_id", nullable=false)
    private Tipo tipo;

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

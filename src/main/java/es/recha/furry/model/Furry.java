package es.recha.furry.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
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

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }

    public ContentStatus getStatus() { return status; }
    public void setStatus(ContentStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}

package es.recha.furry.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="furry_universo",
        uniqueConstraints = @UniqueConstraint(name="ux_fu_furry_universo", columnNames={"furry_id","universo_id"}))
public class FurryUniverso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="furry_id", nullable=false)
    private Furry furry;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="universo_id", nullable=false)
    private Universo universo;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="origen_code", nullable=false)
    private LinkOrigen origen;

    @Column(length=500)
    private String nota;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    @PrePersist void prePersist() { this.createdAt = Instant.now(); }

    public Long getId() { return id; }

    public Furry getFurry() { return furry; }
    public void setFurry(Furry furry) { this.furry = furry; }

    public Universo getUniverso() { return universo; }
    public void setUniverso(Universo universo) { this.universo = universo; }

    public LinkOrigen getOrigen() { return origen; }
    public void setOrigen(LinkOrigen origen) { this.origen = origen; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public Instant getCreatedAt() { return createdAt; }
}

package es.recha.furry.repositories;

import es.recha.furry.model.Universo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversoRepository extends JpaRepository<Universo, Long> {
    Optional<Universo> findBySlug(String slug);
    boolean existsBySlug(String slug);
}

package es.recha.furry.repositories;

import es.recha.furry.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
    Optional<Tipo> findBySlug(String slug);
    boolean existsBySlug(String slug);
}

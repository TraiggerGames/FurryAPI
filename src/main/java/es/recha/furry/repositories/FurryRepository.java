package es.recha.furry.repositories;

import es.recha.furry.model.Furry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FurryRepository extends JpaRepository<Furry, Long> {
    Optional<Furry> findBySlug(String slug);
    boolean existsBySlug(String slug);
}

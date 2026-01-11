package es.recha.furry.repositories;

import es.recha.furry.model.FurryUniverso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FurryUniversoRepository extends JpaRepository<FurryUniverso, Long> {
    Optional<FurryUniverso> findByFurryIdAndUniversoId(Long furryId, Long universoId);
}

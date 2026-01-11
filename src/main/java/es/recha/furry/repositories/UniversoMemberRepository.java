package es.recha.furry.repositories;

import es.recha.furry.model.UniversoMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversoMemberRepository extends JpaRepository<UniversoMember, Long> {
    Optional<UniversoMember> findByUniversoIdAndUserId(Long universoId, Long userId);
}

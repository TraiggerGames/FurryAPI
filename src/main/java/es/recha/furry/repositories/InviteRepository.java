package es.recha.furry.repositories;

import es.recha.furry.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Optional<Invite> findByTokenHash(String tokenHash);
}

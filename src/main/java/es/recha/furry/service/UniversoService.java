package es.recha.furry.service;

import es.recha.furry.model.ContentStatus;
import es.recha.furry.model.Universo;
import es.recha.furry.model.User;
import es.recha.furry.model.Visibility;
import es.recha.furry.repositories.ContentStatusRepository;
import es.recha.furry.repositories.UniversoRepository;
import es.recha.furry.repositories.UserRepository;
import es.recha.furry.repositories.VisibilityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversoService {

    private final UniversoRepository universoRepository;
    private final UserRepository userRepository;
    private final VisibilityRepository visibilityRepository;
    private final ContentStatusRepository contentStatusRepository;
    private final SlugService slugService;

    public UniversoService(
            UniversoRepository universoRepository,
            UserRepository userRepository,
            VisibilityRepository visibilityRepository,
            ContentStatusRepository contentStatusRepository,
            SlugService slugService
    ) {
        this.universoRepository = universoRepository;
        this.userRepository = userRepository;
        this.visibilityRepository = visibilityRepository;
        this.contentStatusRepository = contentStatusRepository;
        this.slugService = slugService;
    }

    public List<Universo> list() {
        return universoRepository.findAll();
    }

    public Universo getBySlug(String slug) {
        return universoRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Universo no encontrado: " + slug));
    }

    @Transactional
    public Universo create(String nombre, String descripcion, Long ownerId, String visibilityCode) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("nombre es obligatorio");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("ownerId es obligatorio");
        }

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner no existe"));

        String visCode = (visibilityCode == null || visibilityCode.isBlank()) ? "PUBLIC" : visibilityCode;
        Visibility visibility = visibilityRepository.findById(visCode)
                .orElseThrow(() -> new IllegalArgumentException("Visibility inválida: " + visCode));

        ContentStatus status = contentStatusRepository.findById("ACTIVE")
                .orElseThrow(() -> new IllegalArgumentException("Falta content_status ACTIVE en BD"));

        Universo u = new Universo();
        u.setNombre(nombre);
        u.setDescripcion(descripcion);
        u.setOwner(owner);
        u.setVisibility(visibility);
        u.setStatus(status);

        String slug = slugService.uniqueSlug(nombre, universoRepository::existsBySlug);
        u.setSlug(slug);

        return universoRepository.save(u);
    }
}

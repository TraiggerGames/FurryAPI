package es.recha.furry.service;

import es.recha.furry.model.Tipo;
import es.recha.furry.repositories.TipoRepository;
import es.recha.furry.service.exceptions.BadRequestException;
import es.recha.furry.service.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoService {
    private final TipoRepository tipoRepository;
    private final SlugService slugService;

    public TipoService(TipoRepository tipoRepository, SlugService slugService) {
        this.tipoRepository = tipoRepository;
        this.slugService = slugService;
    }

    public List<Tipo> list() {
        return tipoRepository.findAll();
    }

    public Tipo getBySlug(String slug) {
        return tipoRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Tipo no encontrado: " + slug));
    }

    @Transactional
    public Tipo create(String nombre, String descripcion, String parentSlug) {
        if (nombre == null || nombre.isBlank()) {
            throw new BadRequestException("nombre es obligatorio");
        }

        Tipo parent = null;
        if (parentSlug != null && !parentSlug.isBlank()) {
            parent = getBySlug(parentSlug);
        }

        Tipo t = new Tipo();
        t.setNombre(nombre);
        t.setDescripcion(descripcion);
        t.setParent(parent);

        String slug = slugService.uniqueSlug(nombre, tipoRepository::existsBySlug);
        t.setSlug(slug);

        return tipoRepository.save(t);
    }
}

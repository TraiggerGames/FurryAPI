package es.recha.furry.service;

import es.recha.furry.model.*;
import es.recha.furry.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FurryService {

    private final FurryRepository furryRepository;
    private final TipoRepository tipoRepository;
    private final UserRepository userRepository;
    private final VisibilityRepository visibilityRepository;
    private final ContentStatusRepository contentStatusRepository;
    private final UniversoRepository universoRepository;
    private final LinkOrigenRepository linkOrigenRepository;
    private final FurryUniversoRepository furryUniversoRepository;
    private final SlugService slugService;

    public FurryService(
            FurryRepository furryRepository,
            TipoRepository tipoRepository,
            UserRepository userRepository,
            VisibilityRepository visibilityRepository,
            ContentStatusRepository contentStatusRepository,
            UniversoRepository universoRepository,
            LinkOrigenRepository linkOrigenRepository,
            FurryUniversoRepository furryUniversoRepository,
            SlugService slugService
    ) {
        this.furryRepository = furryRepository;
        this.tipoRepository = tipoRepository;
        this.userRepository = userRepository;
        this.visibilityRepository = visibilityRepository;
        this.contentStatusRepository = contentStatusRepository;
        this.universoRepository = universoRepository;
        this.linkOrigenRepository = linkOrigenRepository;
        this.furryUniversoRepository = furryUniversoRepository;
        this.slugService = slugService;
    }

    public List<Furry> list() {
        return furryRepository.findAll();
    }

    public Furry getBySlug(String slug) {
        return furryRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Furry no encontrado: " + slug));
    }

    @Transactional
    public Furry create(String nombre, String alias, String descripcion, String imagenUrl,
                        String tipoSlug, Long ownerId, String visibilityCode) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("nombre es obligatorio");
        }
        if (tipoSlug == null || tipoSlug.isBlank()) {
            throw new IllegalArgumentException("tipoSlug es obligatorio");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("ownerId es obligatorio");
        }

        Tipo tipo = tipoRepository.findBySlug(tipoSlug)
                .orElseThrow(() -> new IllegalArgumentException("Tipo no existe: " + tipoSlug));

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner no existe"));

        String visCode = (visibilityCode == null || visibilityCode.isBlank()) ? "PUBLIC" : visibilityCode;
        Visibility visibility = visibilityRepository.findById(visCode)
                .orElseThrow(() -> new IllegalArgumentException("Visibility inválida: " + visCode));

        ContentStatus status = contentStatusRepository.findById("ACTIVE")
                .orElseThrow(() -> new IllegalArgumentException("Falta content_status ACTIVE en BD"));

        Furry f = new Furry();
        f.setNombre(nombre);
        f.setAlias(alias);
        f.setDescripcion(descripcion);
        f.setImagenUrl(imagenUrl);
        f.setTipo(tipo);
        f.setOwner(owner);
        f.setVisibility(visibility);
        f.setStatus(status);

        String slug = slugService.uniqueSlug(nombre, furryRepository::existsBySlug);
        f.setSlug(slug);

        return furryRepository.save(f);
    }

    @Transactional
    public FurryUniverso linkToUniverso(String furrySlug, String universoSlug, String origenCode, String nota) {
        if (furrySlug == null || furrySlug.isBlank()) {
            throw new IllegalArgumentException("furrySlug es obligatorio");
        }
        if (universoSlug == null || universoSlug.isBlank()) {
            throw new IllegalArgumentException("universoSlug es obligatorio");
        }

        Furry furry = getBySlug(furrySlug);
        Universo universo = universoRepository.findBySlug(universoSlug)
                .orElseThrow(() -> new IllegalArgumentException("Universo no encontrado: " + universoSlug));

        if (furryUniversoRepository.findByFurryIdAndUniversoId(furry.getId(), universo.getId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe el vínculo furry-universo");
        }

        String orgCode = (origenCode == null || origenCode.isBlank()) ? "OTRO" : origenCode;
        LinkOrigen origen = linkOrigenRepository.findById(orgCode)
                .orElseThrow(() -> new IllegalArgumentException("Origen inválido: " + orgCode));

        FurryUniverso fu = new FurryUniverso();
        fu.setFurry(furry);
        fu.setUniverso(universo);
        fu.setOrigen(origen);
        fu.setNota(nota);

        return furryUniversoRepository.save(fu);
    }
}

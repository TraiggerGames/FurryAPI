package es.recha.furry.service;

import es.recha.furry.model.*;
import es.recha.furry.repositories.*;
import es.recha.furry.service.exceptions.BadRequestException;
import es.recha.furry.service.exceptions.ConflictException;
import es.recha.furry.service.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FurryService {

    private final FurryRepository furryRepository;
    private final FurryUniversoRepository furryUniversoRepository;
    private final LinkOrigenRepository linkOrigenRepository;

    private final TipoService tipoService;
    private final UniversoService universoService;
    private final UserService userService;

    private final VisibilityRepository visibilityRepository;
    private final ContentStatusRepository contentStatusRepository;
    private final SlugService slugService;

    public FurryService(
            FurryRepository furryRepository,
            FurryUniversoRepository furryUniversoRepository,
            LinkOrigenRepository linkOrigenRepository,
            TipoService tipoService,
            UniversoService universoService,
            UserService userService,
            VisibilityRepository visibilityRepository,
            ContentStatusRepository contentStatusRepository,
            SlugService slugService
    ) {
        this.furryRepository = furryRepository;
        this.furryUniversoRepository = furryUniversoRepository;
        this.linkOrigenRepository = linkOrigenRepository;
        this.tipoService = tipoService;
        this.universoService = universoService;
        this.userService = userService;
        this.visibilityRepository = visibilityRepository;
        this.contentStatusRepository = contentStatusRepository;
        this.slugService = slugService;
    }

    public List<Furry> list() {
        return furryRepository.findAll();
    }

    public Furry getBySlug(String slug) {
        return furryRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Furry no encontrado: " + slug));
    }

    @Transactional
    public Furry create(String nombre, String alias, String descripcion, String imagenUrl,
                        String tipoSlug, Long ownerId, String visibilityCode) {

        if (nombre == null || nombre.isBlank()) throw new BadRequestException("nombre es obligatorio");
        if (tipoSlug == null || tipoSlug.isBlank()) throw new BadRequestException("tipoSlug es obligatorio");
        if (ownerId == null) throw new BadRequestException("ownerId es obligatorio");

        Tipo tipo = tipoService.getBySlug(tipoSlug);
        User owner = userService.getById(ownerId);

        String visCode = (visibilityCode == null || visibilityCode.isBlank()) ? "PUBLIC" : visibilityCode;
        Visibility visibility = visibilityRepository.findById(visCode)
                .orElseThrow(() -> new NotFoundException("Visibility inválida: " + visCode));

        ContentStatus status = contentStatusRepository.findById("ACTIVE")
                .orElseThrow(() -> new NotFoundException("Falta content_status ACTIVE en BD"));

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
        if (furrySlug == null || furrySlug.isBlank()) throw new BadRequestException("furrySlug es obligatorio");
        if (universoSlug == null || universoSlug.isBlank()) throw new BadRequestException("universoSlug es obligatorio");

        Furry furry = getBySlug(furrySlug);
        Universo universo = universoService.getBySlug(universoSlug);

        if (furryUniversoRepository.findByFurryIdAndUniversoId(furry.getId(), universo.getId()).isPresent()) {
            throw new ConflictException("Ya existe el vínculo furry-universo");
        }

        String orgCode = (origenCode == null || origenCode.isBlank()) ? "OTRO" : origenCode;
        LinkOrigen origen = linkOrigenRepository.findById(orgCode)
                .orElseThrow(() -> new NotFoundException("Origen inválido: " + orgCode));

        FurryUniverso fu = new FurryUniverso();
        fu.setFurry(furry);
        fu.setUniverso(universo);
        fu.setOrigen(origen);
        fu.setNota(nota);

        return furryUniversoRepository.save(fu);
    }
}

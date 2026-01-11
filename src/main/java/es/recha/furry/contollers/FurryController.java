package es.recha.furry.contollers;

import es.recha.furry.model.Furry;
import es.recha.furry.model.FurryUniverso;
import es.recha.furry.service.FurryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/furries")
public class FurryController {

    private final FurryService furryService;

    public FurryController(FurryService furryService) {
        this.furryService = furryService;
    }

    public record FurryCreateRequest(
            String nombre,
            String alias,
            String descripcion,
            String imagenUrl,
            String tipoSlug,
            Long ownerId,
            String visibilityCode
    ) {}

    public record LinkUniversoRequest(
            String universoSlug,
            String origenCode,
            String nota
    ) {}

    @GetMapping
    public List<Furry> list() {
        return furryService.list();
    }

    @GetMapping("/{slug}")
    public Furry get(@PathVariable String slug) {
        return furryService.getBySlug(slug);
    }

    @PostMapping
    public Furry create(@RequestBody FurryCreateRequest req) {
        return furryService.create(
                req.nombre(), req.alias(), req.descripcion(), req.imagenUrl(),
                req.tipoSlug(), req.ownerId(), req.visibilityCode()
        );
    }

    @PostMapping("/{furrySlug}/universos")
    public FurryUniverso linkUniverso(@PathVariable String furrySlug, @RequestBody LinkUniversoRequest req) {
        return furryService.linkToUniverso(furrySlug, req.universoSlug(), req.origenCode(), req.nota());
    }
}

package es.recha.furry.contollers;

import es.recha.furry.model.Universo;
import es.recha.furry.service.UniversoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universos")
public class UniversoController {

    private final UniversoService universoService;

    public UniversoController(UniversoService universoService) {
        this.universoService = universoService;
    }

    public record UniversoCreateRequest(
            String nombre,
            String descripcion,
            Long ownerId,
            String visibilityCode
    ) {}

    @GetMapping
    public List<Universo> list() {
        return universoService.list();
    }

    @GetMapping("/{slug}")
    public Universo get(@PathVariable String slug) {
        return universoService.getBySlug(slug);
    }

    @PostMapping
    public Universo create(@RequestBody UniversoCreateRequest req) {
        return universoService.create(req.nombre(), req.descripcion(), req.ownerId(), req.visibilityCode());
    }
}

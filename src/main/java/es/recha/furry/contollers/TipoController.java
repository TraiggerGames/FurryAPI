package es.recha.furry.contollers;

import es.recha.furry.model.Tipo;
import es.recha.furry.service.TipoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
public class TipoController {

    private final TipoService tipoService;

    public TipoController(TipoService tipoService) {
        this.tipoService = tipoService;
    }

    public record TipoCreateRequest(String nombre, String descripcion, String parentSlug) {}

    @GetMapping
    public List<Tipo> list() {
        return tipoService.list();
    }

    @GetMapping("/{slug}")
    public Tipo get(@PathVariable String slug) {
        return tipoService.getBySlug(slug);
    }

    @PostMapping
    public Tipo create(@RequestBody TipoCreateRequest req) {
        return tipoService.create(req.nombre(), req.descripcion(), req.parentSlug());
    }
}

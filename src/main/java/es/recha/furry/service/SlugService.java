package es.recha.furry.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.function.Function;

@Service
public class SlugService {

    public String slugify(String input) {
        if (input == null) return "item";
        String s = input.trim().toLowerCase(Locale.ROOT);
        s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        s = s.replaceAll("[^a-z0-9]+", "-");
        s = s.replaceAll("(^-|-$)", "");
        if (s.isBlank()) return "item";
        return s;
    }

    /**
     * Genera un slug único usando un checker (existsBySlug).
     * - base, base-2, base-3...
     */
    public String uniqueSlug(String baseText, Function<String, Boolean> existsBySlug) {
        String base = slugify(baseText);
        String candidate = base;
        int i = 2;
        while (existsBySlug.apply(candidate)) {
            candidate = base + "-" + i;
            i++;
            if (i > 10_000) throw new IllegalStateException("No se pudo generar slug único");
        }
        return candidate;
    }
}

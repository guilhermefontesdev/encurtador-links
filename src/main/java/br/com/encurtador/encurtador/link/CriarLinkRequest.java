package br.com.encurtador.encurtador.link;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CriarLinkRequest(
        @NotBlank(message = "A URL é obrigatória")
        @URL(regexp = "^https://.*", message = "Informe uma URL válida começando com http:// ou https://")
        String url
) {}

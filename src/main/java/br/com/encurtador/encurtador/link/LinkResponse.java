package br.com.encurtador.encurtador.link;

import java.time.Instant;

public record LinkResponse(
        String code,
        String urlOriginal,
        String urlCurta,
        Instant criadoEm
) {}

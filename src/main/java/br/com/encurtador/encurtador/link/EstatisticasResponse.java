package br.com.encurtador.encurtador.link;

import java.util.List;

public record EstatisticasResponse(String code, long totalCliques, List<CliquesPorDia> porDia) {
}

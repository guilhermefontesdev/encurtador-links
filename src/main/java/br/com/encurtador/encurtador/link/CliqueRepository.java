package br.com.encurtador.encurtador.link;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;



public interface CliqueRepository extends JpaRepository<Clique, Long> {

    @Query("""
            select new br.com.encurtador.encurtador.link.CliquesPorDia(
                cast(c.clicadoEm as LocalDate ), count(c))
             from Clique c
             where c.link = :link
             group by  cast(c.clicadoEm as Localdate)
             order by cast(c.clicadoEm as LocalDate)
             """)
    List<CliquesPorDia> contarPorDia(@Param("link") Link link);

    long countByLink(Link link);
}

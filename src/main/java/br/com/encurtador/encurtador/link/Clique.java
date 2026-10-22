package br.com.encurtador.encurtador.link;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "cliques", indexes = @Index(name = "idx_cliques_link_id", columnList = "link_id"))
public class Clique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "link_id", nullable = false)
    private Link link;

    @Column(name = "clicado_em", nullable = false)
    private Instant clicadoEm;

    protected Clique(){

    }

    public Clique(Link link){
        this.link = link;
        this.clicadoEm = Instant.now();
    }

    public Long getId(){
        return id;
    }

    public Link getLink(){
        return link;
    }

    public Instant getCliadoEm(){
        return clicadoEm;
    }



}

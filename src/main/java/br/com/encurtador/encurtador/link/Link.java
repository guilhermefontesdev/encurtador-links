package br.com.encurtador.encurtador.link;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "links")

public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @Column(name = "urlOriginal", nullable = false, length = 2048 )
    private String urlOriginal;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected Link(){

    }

    public Link(String code, String urlOriginal){
        this.code = code;
        this.urlOriginal = urlOriginal;
        this.criadoEm= Instant.now();
    }

    public Long getId(){ return id; }
    public String getCode(){ return code; }
    public String  getUrlOriginal(){ return urlOriginal; }
    public Instant getCriadoEm(){ return criadoEm; }



}

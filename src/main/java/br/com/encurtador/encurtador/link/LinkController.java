package br.com.encurtador.encurtador.link;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.List;

import java.net.URI;


@RestController
@RequestMapping("/links")
public class LinkController {

    private final LinkService service;

    public LinkController(LinkService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LinkResponse> criar(@Valid @RequestBody CriarLinkRequest request){
        LinkResponse resposta = service.encurtar(request);
        return ResponseEntity
                .created(URI.create(resposta.urlCurta()))
                        .body(resposta);

    }

    @GetMapping
    public List<LinkResponse> listar(){
        return service.listar();
    }

    @GetMapping("/{code}/estatisticas")
    public EstatisticasResponse estatisticas(@PathVariable String code){
        return service.estatisticas(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link nao encontrado"));
    }

}

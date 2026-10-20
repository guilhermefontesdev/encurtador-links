package br.com.encurtador.encurtador.link;


import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

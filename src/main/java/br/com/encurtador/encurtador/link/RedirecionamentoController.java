package br.com.encurtador.encurtador.link;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;

@RestController
public class RedirecionamentoController {

    private final LinkService service;

    public RedirecionamentoController(LinkService service){
        this.service = service;
    }

    @GetMapping("/{code:[a-zA-Z0-9]+}")
    public ResponseEntity<Void> redirecionar(@PathVariable String code){
        String urlOriginal = service.registrarCliques(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Link nao encontrado"));

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(urlOriginal))
                .build();
    }

}

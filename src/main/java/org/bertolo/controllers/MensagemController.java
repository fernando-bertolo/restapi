package org.bertolo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bertolo.exceptions.MensagemNotFoundException;
import org.bertolo.model.Mensagem;
import org.bertolo.services.MensagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Mensagem> registrarMensagem(
            @Valid @RequestBody Mensagem mensagem
    ) {
        var mensagemRegistrada = this.mensagemService.registrarMensagem(mensagem);
        return new ResponseEntity<>(mensagemRegistrada, HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> buscarMensagem(@PathVariable String id){
        try {

            var uuid = UUID.fromString(id);
            var mensagemEncontrada = this.mensagemService.obterMensagem(uuid);

            return new ResponseEntity<>(mensagemEncontrada, HttpStatus.OK);

        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID invalido");
        } catch(MensagemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

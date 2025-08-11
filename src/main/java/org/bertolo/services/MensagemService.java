package org.bertolo.services;

import org.bertolo.model.Mensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MensagemService {
    Mensagem registrarMensagem(Mensagem mensagem);
    Mensagem obterMensagem(UUID id);
    Mensagem atualizarMensagem(UUID id, Mensagem mensagemAtualizada);
    boolean removerMensagem(UUID id);
    Page<Mensagem> obterMensagens(Pageable pageable);
}

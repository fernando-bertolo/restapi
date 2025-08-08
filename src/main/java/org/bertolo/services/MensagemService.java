package org.bertolo.services;

import org.bertolo.model.Mensagem;

import java.util.UUID;

public interface MensagemService {
    Mensagem registrarMensagem(Mensagem mensagem);
    Mensagem obterMensagem(UUID id);
}

package org.bertolo.helper;

import org.bertolo.model.Mensagem;

public class MensagemHelper {
    public static Mensagem gerarMensagem() {
        return Mensagem.builder()
                .usuario("João")
                .conteudo("conteudo")
                .build();

    }
}

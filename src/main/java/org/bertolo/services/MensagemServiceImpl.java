package org.bertolo.services;

import lombok.RequiredArgsConstructor;
import org.bertolo.exceptions.MensagemNotFoundException;
import org.bertolo.model.Mensagem;
import org.bertolo.repository.MensagemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MensagemServiceImpl implements MensagemService {

    private final MensagemRepository mensagemRepository;

    @Override
    public Mensagem registrarMensagem(Mensagem mensagem) {
        mensagem.setId(UUID.randomUUID());
        return this.mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem obterMensagem(UUID id) {
        return this.mensagemRepository.findById(id)
                .orElseThrow(() -> new MensagemNotFoundException("Mensagem não encontrada!"));
    }

    @Override
    public Mensagem atualizarMensagem(UUID id, Mensagem mensagemAtualizada) {
        var mensagem = this.obterMensagem(id);
        if(!mensagem.getId().equals(mensagemAtualizada.getId())) {
            throw new MensagemNotFoundException("Mensagem não apresenta o ID correto");
        }

        mensagem.setDataAlteracao(LocalDateTime.now());
        mensagem.setConteudo(mensagemAtualizada.getConteudo());
        return this.mensagemRepository.save(mensagem);
    }

    @Override
    public boolean removerMensagem(UUID id) {
        var mensagem = this.obterMensagem(id);
        this.mensagemRepository.delete(mensagem);
        return true;
    }

    @Override
    public Page<Mensagem> obterMensagens(Pageable pageable) {
        return this.mensagemRepository.obterMensagem(pageable);
    }
}

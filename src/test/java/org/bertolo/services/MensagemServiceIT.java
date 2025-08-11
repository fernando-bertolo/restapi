package org.bertolo.services;

import jakarta.transaction.Transactional;
import org.bertolo.helper.MensagemHelper;
import org.bertolo.model.Mensagem;
import org.bertolo.repository.MensagemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MensagemServiceIT {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private MensagemService mensagemService;


    @Test
    void devePermitirRegistrarMensagem() {
        // Arrange
        var mensagem = MensagemHelper.gerarMensagem();

        // Act
        var mensagemRegistrada = this.mensagemService.registrarMensagem(mensagem);

        //Assert
        assertThat(mensagemRegistrada)
                .isNotNull()
                .isInstanceOf(Mensagem.class);

        assertThat(mensagemRegistrada.getId())
                .isNotNull();

        assertThat(mensagemRegistrada.getUsuario())
                .isEqualTo(mensagem.getUsuario());

        assertThat(mensagemRegistrada.getConteudo())
                .isEqualTo(mensagem.getConteudo());
    }

    @Test
    void devePermitirObterMensagem() {
        // Arrange
        var id = UUID.fromString("be91afb9-e0fb-4cce-96d7-181d0c8b9153");

        // Act
        var mensagemObtida = this.mensagemService.obterMensagem(id);

        // Assert
        assertThat(mensagemObtida)
                .isInstanceOf(Mensagem.class)
                .isNotNull();
        assertThat(mensagemObtida.getUsuario())
                .isNotNull();
        assertThat(mensagemObtida.getId())
                .isNotNull();
        assertThat(mensagemObtida.getConteudo())
                .isNotNull();
    }

    @Test
    void devePermitirRemoverMensagem() {
        // Arrange
        var id = UUID.fromString("be91afb9-e0fb-4cce-96d7-181d0c8b9153");

        // Act
        var mensagemRemovida = this.mensagemService.removerMensagem(id);

        // Assert
        assertThat(mensagemRemovida).isTrue();
    }
}

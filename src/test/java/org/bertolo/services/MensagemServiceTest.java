package org.bertolo.services;

import org.bertolo.helper.MensagemHelper;
import org.bertolo.model.Mensagem;
import org.bertolo.repository.MensagemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static java.beans.Beans.isInstanceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MensagemServiceTest {

    @Mock
    private MensagemRepository mensagemRepository;
    private MensagemService mensagemService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        this.mock = MockitoAnnotations.openMocks(this);
        this.mensagemService = new MensagemServiceImpl(this.mensagemRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.mock.close();
    }

    @Test
    void devePermitirRegistrarMensagem() {
        // Arrange
        var mensagem = MensagemHelper.gerarMensagem();
        when(this.mensagemRepository.save(any(Mensagem.class))).thenAnswer(i -> i.getArgument(0));

        //Act
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

        verify(this.mensagemRepository, times(1)).save(mensagem);

    }

    @Test
    void devePermitirObterMensagemPorId() {
        // Arrange
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);

        when(this.mensagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(mensagem));

        // Act
        var mensagemObtida = this.mensagemService.obterMensagem(id);

        // Assert

        verify(this.mensagemRepository, times(1)).findById(id);
        assertThat(mensagemObtida).isEqualTo(mensagem);
    }

    @Test
    void devePermitirObterMensagens() {
        fail("metodo nao implementado");
    }


    @Test
    void devePermitirModificarMensagem() {
        fail("metodo nao implementado");
    }

    @Test
    void devePermitirRemoverMensagem() {
        fail("metodo nao implementado");
    }
}

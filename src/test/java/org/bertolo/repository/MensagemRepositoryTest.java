package org.bertolo.repository;

import org.bertolo.model.Mensagem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MensagemRepositoryTest {

    @Mock
    private MensagemRepository mensagemRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        this.mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.mock.close();
    }

    @Test
    void devePermitirRegistrarMensagem() {
        // Arrange - Preparar
        var mensagem = this.gerarMensagem();
        when(this.mensagemRepository.save(any(Mensagem.class))).thenReturn(mensagem);

        // Act - Atuar
        var mensagemArmazena = this.mensagemRepository.save(mensagem);

        // Assert - verificar
        verify(this.mensagemRepository, times(1)).save(mensagem);
        System.out.println(mensagemArmazena);
    }

    @Test
    void devePermitirConsultarMensagem() {

        //Arrange
        var id = UUID.randomUUID();
        var mensagem = this.gerarMensagem();
        mensagem.setId(id);

        // Act

        when(this.mensagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(mensagem));

        var mensagemEncontrada = this.mensagemRepository.findById(id);


        // Assert
        assertThat(mensagemEncontrada)
                .isNotNull()
                .containsSame(mensagem);

    }

    @Test
    void devePermitirRemoverMensagem() {
        //Arrange
        var id = UUID.randomUUID();
        var mensagem = this.gerarMensagem();
        mensagem.setId(id);

        // Act

        doNothing().when(this.mensagemRepository).deleteById(any(UUID.class));

        this.mensagemRepository.deleteById(id);


        // Assert
        verify(this.mensagemRepository, times(1)).deleteById(id);
    }

    private Mensagem gerarMensagem() {
        return Mensagem.builder()
                .usuario("João")
                .conteudo("conteudo")
                .build();

    }


}

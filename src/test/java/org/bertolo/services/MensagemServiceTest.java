package org.bertolo.services;

import org.bertolo.helper.MensagemHelper;
import org.bertolo.repository.MensagemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

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
        var mensagem = MensagemHelper.gerarMensagem();

        when(this.mensagemRepository.save(any()))
    }

    @Test
    void devePermitirObterMensagemPorId() {
        fail("metodo nao implementado");
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

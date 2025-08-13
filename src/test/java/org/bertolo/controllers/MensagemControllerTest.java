package org.bertolo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bertolo.exceptions.MensagemNotFoundException;
import org.bertolo.handlers.GlobalExceptionHandler;
import org.bertolo.helper.MensagemHelper;
import org.bertolo.model.Mensagem;
import org.bertolo.repository.MensagemRepository;
import org.bertolo.services.MensagemService;
import org.bertolo.services.MensagemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.string;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MensagemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MensagemService mensagemService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        MensagemController mensagemController = new MensagemController(this.mensagemService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mensagemController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.mock.close();
    }

    @Test
    void devePermitirRegistrarMensagem() throws Exception {

        // Arrange
        var mensagemRequest = MensagemHelper.gerarMensagem();
        when(this.mensagemService.registrarMensagem(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act + Assert
        this.mockMvc.perform(post("/mensagens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.asJsonString(mensagemRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(this.mensagemService, times(1)).registrarMensagem(any(Mensagem.class));
    }

    @Test
    void devePermitirObterMensagem() throws Exception {
        // Arrange
        var id = UUID.randomUUID();
        var mensagemResponse = MensagemHelper.gerarMensagem();
        mensagemResponse.setId(id);
        mensagemResponse.setDataCriacao(LocalDateTime.now());
        mensagemResponse.setDataAlteracao(LocalDateTime.now());

        when(this.mensagemService.obterMensagem(any(UUID.class)))
            .thenReturn(mensagemResponse);

        // Act + Assert
        this.mockMvc.perform(get("/mensagens/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.mensagemService, times(1)).obterMensagem(any(UUID.class));

    }

    @Test
    void deveGerarExcecaoAoObterMensagemComIdNaoExistente() throws Exception {
        // Arrange
        var id = UUID.randomUUID();

        when(this.mensagemService.obterMensagem(any(UUID.class)))
            .thenThrow(new MensagemNotFoundException("mensagem nao encontrada"));

        // Act + Assert
        this.mockMvc.perform(get("/mensagens/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(this.mensagemService, times(1)).obterMensagem(any(UUID.class));
    }

    @Test
    void deveGerarExcecaoAoObterMensagemComIdInvalido() throws Exception {
        // Arrange
        var id = "123";

        // Assert
        this.mockMvc.perform(get("/mensagens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(this.mensagemService, never()).obterMensagem(any(UUID.class));
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

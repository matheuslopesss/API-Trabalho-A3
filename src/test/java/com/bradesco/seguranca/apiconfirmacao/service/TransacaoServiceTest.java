package com.bradesco.seguranca.apiconfirmacao.service;

import com.bradesco.seguranca.apiconfirmacao.model.StatusTransacao;
import com.bradesco.seguranca.apiconfirmacao.model.TransacaoPendente;
import com.bradesco.seguranca.apiconfirmacao.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    // 1. Mock: Simula o banco de dados (Repository)
    @Mock
    private TransacaoRepository transacaoRepository;

    // 2. Injecção: Cria a instância do Service, injetando o Mock do Repository
    @InjectMocks
    private ConfirmacaoService confirmacaoService;

    @Test
    void deveConfirmarTransacaoComSucesso() {
        Long transacaoId = 1L;

        // --- Dado (Given) ---
        // Cria uma transação com o status inicial correto: PENDENTE_CONFIRMACAO
        TransacaoPendente transacaoPendente = new TransacaoPendente();
        transacaoPendente.setId(transacaoId);
        // CORREÇÃO: Usando setStatus (pois o campo na entidade chama 'status')
        transacaoPendente.setStatus(StatusTransacao.PENDENTE_CONFIRMACAO);

        // Define o comportamento do Mock: Quando buscar pelo ID, retorne a transação PENDENTE
        Mockito.when(transacaoRepository.findById(transacaoId))
                .thenReturn(Optional.of(transacaoPendente));

        // --- Ação (When) ---
        // CORREÇÃO: Chamando o método confirmarTransacao que criamos no Service
        confirmacaoService.confirmarTransacao(transacaoId);

        // --- Verificação (Then) ---
        // 1. Verifica se o status foi alterado para CONFIRMADA_CLIENTE
        // CORREÇÃO: Sintaxe do assertEquals corrigida
        assertEquals(StatusTransacao.CONFIRMADA_CLIENTE, transacaoPendente.getStatus(),
                "O status deve ser CONFIRMADA_CLIENTE após a execução.");

        // 2. Verifica se o método 'save' foi chamado no Mock
        Mockito.verify(transacaoRepository, Mockito.times(1)).save(transacaoPendente);
    }
}
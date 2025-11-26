package com.bradesco.seguranca.apiconfirmacao.service;

import com.bradesco.seguranca.apiconfirmacao.dto.TransacaoRequestDTO; // Para o método de criação
import com.bradesco.seguranca.apiconfirmacao.model.StatusTransacao;
import com.bradesco.seguranca.apiconfirmacao.model.TransacaoPendente;
import com.bradesco.seguranca.apiconfirmacao.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    // O método de confirmação (confirmar) iria aqui se estivesse na camada Service
    // public void confirmar(Long id) { ... }

    // 1. MÉTODO DE CRIAÇÃO (Persistência)
    public TransacaoPendente criarTransacao(TransacaoRequestDTO request) {
        TransacaoPendente novaTransacao = new TransacaoPendente();

        // Mapeamento dos dados do DTO
        novaTransacao.setEstabelecimento(request.getEstabelecimento());
        novaTransacao.setIdCliente(request.getIdCliente());
        novaTransacao.setValor(request.getValor());

        // Configurações padrão: PENDENTE_CONFIRMACAO e data/hora atual
        novaTransacao.setStatus(StatusTransacao.PENDENTE_CONFIRMACAO);
        novaTransacao.setDataHora(LocalDateTime.now());

        // Salva no banco de dados
        return transacaoRepository.save(novaTransacao);
    }

    // 2. MÉTODO DE NEGAÇÃO (Atualização)
    public void negar(Long id) {
        // Busca a transação, ou lança erro se não for encontrada
        TransacaoPendente transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação de ID " + id + " não encontrada."));

        // Altera o status para NEGADA_CLIENTE
        transacao.setStatus(StatusTransacao.NEGADA_CLIENTE);

        // Salva a alteração no banco de dados
        transacaoRepository.save(transacao);
    }
}
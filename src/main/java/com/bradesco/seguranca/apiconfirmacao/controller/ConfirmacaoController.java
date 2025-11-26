package com.bradesco.seguranca.apiconfirmacao.controller;

import com.bradesco.seguranca.apiconfirmacao.dto.TransacaoRequestDTO;
import com.bradesco.seguranca.apiconfirmacao.model.StatusTransacao;
import com.bradesco.seguranca.apiconfirmacao.model.TransacaoPendente;
import com.bradesco.seguranca.apiconfirmacao.repository.TransacaoRepository;
import com.bradesco.seguranca.apiconfirmacao.service.ConfirmacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/seguranca") // Caminho Base
public class ConfirmacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ConfirmacaoService confirmacaoService;

    // 1. ENDPOINT DE CRIAÇÃO (POST)
    // URL: http://localhost:8081/api/v1/seguranca/transacoes
    @PostMapping("/transacoes")
    public ResponseEntity<TransacaoPendente> criarTransacao(@RequestBody TransacaoRequestDTO request) {
        TransacaoPendente novaTransacao = confirmacaoService.criarTransacao(request);
        return ResponseEntity.status(201).body(novaTransacao);
    }

    // 2. ENDPOINT DE CONFIRMAÇÃO (POST)
    // URL: http://localhost:8081/api/v1/seguranca/transacoes/{id}/confirmar
    @PostMapping("/transacoes/{id}/confirmar")
    public ResponseEntity<String> confirmarTransacao(@PathVariable Long id) {
        return processarDecisao(id, StatusTransacao.CONFIRMADA_CLIENTE, "Transação confirmada com sucesso.");
    }

    // 3. ENDPOINT DE NEGAÇÃO (POST)
    // URL: http://localhost:8081/api/v1/seguranca/transacoes/{id}/negar
    @PostMapping("/transacoes/{id}/negar")
    public ResponseEntity<String> negarTransacao(@PathVariable Long id) {
        confirmacaoService.negar(id);
        return ResponseEntity.ok("Transação negada com sucesso.");
    }

    // Método auxiliar privado (Lógica interna)
    private ResponseEntity<String> processarDecisao(Long id, StatusTransacao novoStatus, String mensagem) {
        Optional<TransacaoPendente> transacaoOpt = transacaoRepository.findById(id);
        if (transacaoOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Transação não encontrada.");
        }
        TransacaoPendente transacao = transacaoOpt.get();
        transacao.setStatus(novoStatus);
        transacaoRepository.save(transacao);
        return ResponseEntity.ok(mensagem);
    }
}
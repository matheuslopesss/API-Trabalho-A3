package com.bradesco.seguranca.apiconfirmacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transacoes_pendentes")
public class TransacaoPendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID real do cliente que veio do Core Bancário
    private String idCliente;

    private String estabelecimento;
    private BigDecimal valor;
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    public TransacaoPendente() {}
}
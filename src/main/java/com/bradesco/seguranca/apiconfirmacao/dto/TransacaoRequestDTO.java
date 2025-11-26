package com.bradesco.seguranca.apiconfirmacao.dto;

import java.math.BigDecimal;

public class TransacaoRequestDTO {

    private String estabelecimento;
    private String idCliente;
    private BigDecimal valor;

    // Getters e Setters

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
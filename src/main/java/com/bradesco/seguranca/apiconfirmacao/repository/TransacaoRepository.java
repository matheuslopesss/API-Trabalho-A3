package com.bradesco.seguranca.apiconfirmacao.repository;


import com.bradesco.seguranca.apiconfirmacao.model.StatusTransacao;
import com.bradesco.seguranca.apiconfirmacao.model.TransacaoPendente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TransacaoRepository extends JpaRepository<TransacaoPendente, Long> {

    // 3. O método customizado que o Controller irá usar
    List<TransacaoPendente> findByIdClienteAndStatus(String idCliente, StatusTransacao status);

}
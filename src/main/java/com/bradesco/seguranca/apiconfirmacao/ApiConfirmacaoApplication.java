package com.bradesco.seguranca.apiconfirmacao; // Pacote raiz (correto)

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // O Maven procura por essa anotação!
public class ApiConfirmacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiConfirmacaoApplication.class, args);
    }
}
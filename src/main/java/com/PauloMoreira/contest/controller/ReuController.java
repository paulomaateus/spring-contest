package com.PauloMoreira.contest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PauloMoreira.contest.dto.ReuDTO;
import com.PauloMoreira.contest.service.ReuService;

/**
 * Controlador para gerenciar operações relacionadas aos réus.
 *
 * Esta classe é responsável por expor um endpoint REST para criar novos réus.
 * Utiliza o serviço {@link ReuService} para realizar a operação de criação.
 *
 * Endpoints disponíveis:
 *
 * - POST /reu: Cria um novo réu com base nas informações fornecidas.
 */
@RestController
@RequestMapping("/reu")
public class ReuController {

    @Autowired
    private ReuService reuService;

    /**
     * Cria um novo réu.
     *
     * @param reu O objeto ReuDTO contendo as informações do réu a ser criado.
     * @return ResponseEntity com o réu criado em formato DTO e o status HTTP
     * 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ReuDTO> createReu(@RequestBody ReuDTO reu) {
        ReuDTO saved = reuService.createReu(reu);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}

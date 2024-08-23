package com.PauloMoreira.contest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PauloMoreira.contest.dto.ProcessoJudicialDTO;
import com.PauloMoreira.contest.model.ProcessoJudicial;
import com.PauloMoreira.contest.service.ProcessoJudicialService;

/**
 * Controlador para gerenciar operações relacionadas aos processos judiciais.
 *
 * Esta classe é responsável por expor endpoints REST para criar, listar e
 * deletar processos judiciais. Utiliza o serviço
 * {@link ProcessoJudicialService} para realizar as operações necessárias.
 *
 * Endpoints disponíveis:
 *
 * - POST /processos: Cria um novo processo judicial. - DELETE
 * /processos/{numero}: Deleta um processo judicial existente com base no número
 * do processo. - GET /processos: Lista todos os processos judiciais, com
 * suporte à paginação.
 */
@RestController
@RequestMapping("/processos")
public class ProcessoJudicialController {

    @Autowired
    private ProcessoJudicialService processoJudicialService;

    /**
     * Cria um novo processo judicial.
     *
     * @param processoJudicial O objeto ProcessoJudicial a ser criado.
     * @return ResponseEntity com o processo judicial criado e o status HTTP 201
     * (Created).
     */
    @PostMapping
    public ResponseEntity<ProcessoJudicial> createProcessoJudicial(@RequestBody ProcessoJudicial processoJudicial) {
        ProcessoJudicial saved = processoJudicialService.createProcessoJudicial(processoJudicial);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * Deleta um processo judicial existente.
     *
     * @param numero O número do processo judicial a ser deletado.
     * @return ResponseEntity com o status HTTP 204 (No Content) se o processo
     * for deletado com sucesso.
     */
    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable String numero) {
        this.processoJudicialService.deleteProcessoJudicial(numero);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Lista todos os processos judiciais com suporte à paginação.
     *
     * @param page O número da página (padrão: 0).
     * @param size O tamanho da página (padrão: 10).
     * @return Page contendo uma lista de processos judiciais em formato DTO.
     */
    @GetMapping
    public Page<ProcessoJudicialDTO> getProcessos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return processoJudicialService.getAllProcessosJudiciais(page, size);
    }
}

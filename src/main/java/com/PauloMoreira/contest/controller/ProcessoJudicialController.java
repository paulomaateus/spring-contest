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

@RestController
@RequestMapping("/processos")
public class ProcessoJudicialController {

    @Autowired
    private ProcessoJudicialService processoJudicialService;

    @PostMapping
    public ResponseEntity<ProcessoJudicial> createProcessoJudicial(@RequestBody ProcessoJudicial processoJudicial) {
        ProcessoJudicial saved = processoJudicialService.createProcessoJudicial(processoJudicial);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable String numero) {
        this.processoJudicialService.deleteProcessoJudicial(numero);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public Page<ProcessoJudicialDTO> getProcessos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return processoJudicialService.getAllProcessosJudiciais(page, size);
    }
}

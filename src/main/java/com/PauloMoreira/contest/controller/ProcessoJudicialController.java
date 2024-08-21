package com.PauloMoreira.contest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PauloMoreira.contest.model.ProcessoJudicial;
import com.PauloMoreira.contest.service.ProcessoJudicialService;

@RestController
@RequestMapping("/processos")
public class ProcessoJudicialController {

    @Autowired
    private ProcessoJudicialService processoJudicialService;

    @PostMapping
    public ResponseEntity<ProcessoJudicial> createProcesso(@RequestBody ProcessoJudicial processoJudicial) {
        ProcessoJudicial saved = processoJudicialService.insertProcessoJudicial(processoJudicial);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable String numero) {
        this.processoJudicialService.deleteProcesso(numero);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

package com.PauloMoreira.contest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PauloMoreira.contest.dto.ReuDTO;
import com.PauloMoreira.contest.exception.ProcessoNotFoundException;
import com.PauloMoreira.contest.model.ProcessoJudicial;
import com.PauloMoreira.contest.model.Reu;
import com.PauloMoreira.contest.repository.ProcessoJudicialRepository;
import com.PauloMoreira.contest.repository.ReuRepository;

@Service
public class ReuService {

    @Autowired
    private ReuRepository reuRepository;
    @Autowired
    private ProcessoJudicialRepository processoJudicialRepository;

    public Reu insertReu(ReuDTO reu) {
        Optional<ProcessoJudicial> result = processoJudicialRepository.findById(reu.getNumeroProcesso());
        if (result.isEmpty()) {
            throw new ProcessoNotFoundException("Processo inexistente. O processo do réu não está cadastrado no sistema.");
        }
        return reuRepository.save(new Reu(reu, result.get()));
    }

}

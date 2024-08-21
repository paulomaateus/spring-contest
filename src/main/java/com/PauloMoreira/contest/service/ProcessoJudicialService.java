package com.PauloMoreira.contest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PauloMoreira.contest.exception.DuplicateNumeroProcessoException;
import com.PauloMoreira.contest.exception.ProcessoNotFoundException;
import com.PauloMoreira.contest.model.ProcessoJudicial;
import com.PauloMoreira.contest.repository.ProcessoJudicialRepository;

@Service
public class ProcessoJudicialService {
    
    @Autowired
    private ProcessoJudicialRepository processoJudicialRepository;

    public ProcessoJudicial insertProcessoJudicial(ProcessoJudicial processoJudicial){
        if (processoJudicialRepository.existsById(processoJudicial.getNumero())){
            throw new DuplicateNumeroProcessoException("Duplicidade de processos. Já existe um processo salvo com esse número");

        }
        return this.processoJudicialRepository.save(processoJudicial);
    }

    public List<ProcessoJudicial> getAll(){
        return this.processoJudicialRepository.findAll();
    }

    public void deleteProcesso(String numero){
        if (!processoJudicialRepository.existsById(numero)){
            throw new ProcessoNotFoundException("Processo inexistente. O número do processo ao qual se quer deletar não existe no sistema.");
        }else{
            this.processoJudicialRepository.deleteById(numero);
        }
        
    }
}

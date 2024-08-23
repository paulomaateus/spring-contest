package com.PauloMoreira.contest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.PauloMoreira.contest.dto.ProcessoJudicialDTO;
import com.PauloMoreira.contest.exception.DuplicateNumeroProcessoException;
import com.PauloMoreira.contest.exception.InvalidInputException;
import com.PauloMoreira.contest.exception.ProcessoNotFoundException;
import com.PauloMoreira.contest.model.ProcessoJudicial;
import com.PauloMoreira.contest.repository.ProcessoJudicialRepository;
import com.PauloMoreira.contest.util.ValidationUtils;

@Service
public class ProcessoJudicialService {

    @Autowired
    private ProcessoJudicialRepository processoJudicialRepository;

    public ProcessoJudicial createProcessoJudicial(ProcessoJudicial processoJudicial) {
        if (!ValidationUtils.isValidNumeroProcesso(processoJudicial.getNumero())) {
            throw new InvalidInputException("O numerro do processo não pode ser nulo nem vazio.");
        }
        if (processoJudicialRepository.existsById(processoJudicial.getNumero())) {
            throw new DuplicateNumeroProcessoException("Duplicidade de processos. Já existe um processo salvo com esse número");

        }
        return this.processoJudicialRepository.save(processoJudicial);
    }

    public Page<ProcessoJudicialDTO> getAllProcessosJudiciais(int page, int size) {
        Pageable pageagle = PageRequest.of(page, size);
        Page<ProcessoJudicial> processos = this.processoJudicialRepository.findAll(pageagle);
        Page<ProcessoJudicialDTO> processosDTO = processos.map(processo -> new ProcessoJudicialDTO(processo));
        return processosDTO;
    }

    public void deleteProcessoJudicial(String numero) {
        if (!ValidationUtils.isValidNumeroProcesso(numero)) {
            throw new InvalidInputException("O numerro do processo não pode ser nulo nem vazio.");
        }
        if (!processoJudicialRepository.existsById(numero)) {
            throw new ProcessoNotFoundException("Processo inexistente. O número do processo ao qual se quer deletar não existe no sistema.");
        } else {
            this.processoJudicialRepository.deleteById(numero);
        }
    }
}

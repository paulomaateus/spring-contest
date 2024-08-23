package com.PauloMoreira.contest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PauloMoreira.contest.dto.ReuDTO;
import com.PauloMoreira.contest.exception.InvalidInputException;
import com.PauloMoreira.contest.exception.ProcessoNotFoundException;
import com.PauloMoreira.contest.model.ProcessoJudicial;
import com.PauloMoreira.contest.model.Reu;
import com.PauloMoreira.contest.repository.ProcessoJudicialRepository;
import com.PauloMoreira.contest.repository.ReuRepository;
import com.PauloMoreira.contest.util.ValidationUtils;

/**
 * Serviço para gerenciar réus.
 *
 * Esta classe fornece métodos para criar e associar réus a processos judiciais.
 */
@Service
public class ReuService {

    @Autowired
    private ReuRepository reuRepository;

    @Autowired
    private ProcessoJudicialRepository processoJudicialRepository;

    /**
     * Cria um novo réu e o associa a um processo judicial existente.
     *
     * Verifica se o CPF é válido e se o processo judicial ao qual o réu está
     * sendo associado existe.
     *
     * @param reuDTO O objeto ReuDTO com informações do réu.
     * @return O objeto ReuDTO salvo.
     * @throws InvalidInputException Se o CPF do réu for inválido.
     * @throws ProcessoNotFoundException Se o processo judicial associado ao réu
     * não existir.
     */
    public ReuDTO createReu(ReuDTO reu) {
        if (!ValidationUtils.isValidCpf(reu.getCpf())) {
            throw new InvalidInputException("Número de CPF inválido.");
        }

        Optional<ProcessoJudicial> result = processoJudicialRepository.findById(reu.getNumeroProcesso());
        if (result.isEmpty()) {
            throw new ProcessoNotFoundException("Processo inexistente. O processo do réu não está cadastrado no sistema.");
        }

        ProcessoJudicial processo = result.get();
        Reu reuSave = new Reu(reu, processo);

        // Associa o réu ao processo judicial
        processo.setReu(reuSave);

        // Salva o réu e o processo judicial atualizado
        reuRepository.save(reuSave);
        processoJudicialRepository.save(processo);

        return reu;
    }
}

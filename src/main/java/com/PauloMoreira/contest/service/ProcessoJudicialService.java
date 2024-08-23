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

/**
 * Serviço para gerenciar processos judiciais.
 *
 * Esta classe fornece métodos para criar, obter e deletar processos judiciais.
 */
@Service
public class ProcessoJudicialService {

    @Autowired
    private ProcessoJudicialRepository processoJudicialRepository;

    /**
     * Cria um novo processo judicial.
     *
     * Verifica se o número do processo é válido e se já não existe um processo
     * com o mesmo número.
     *
     * @param processoJudicial O objeto ProcessoJudicial a ser criado.
     * @return O processo judicial salvo.
     * @throws InvalidInputException Se o número do processo for inválido.
     * @throws DuplicateNumeroProcessoException Se já existir um processo com o
     * mesmo número.
     */
    public ProcessoJudicial createProcessoJudicial(ProcessoJudicial processoJudicial) {
        if (!ValidationUtils.isValidNumeroProcesso(processoJudicial.getNumero())) {
            throw new InvalidInputException("O número do processo não pode ser nulo nem vazio.");
        }
        if (processoJudicialRepository.existsById(processoJudicial.getNumero())) {
            throw new DuplicateNumeroProcessoException("Duplicidade de processos. Já existe um processo salvo com esse número.");
        }
        return this.processoJudicialRepository.save(processoJudicial);
    }

    /**
     * Obtém todos os processos judiciais com paginação.
     *
     * @param page Número da página a ser retornada.
     * @param size Tamanho da página.
     * @return Uma página de objetos ProcessoJudicialDTO.
     */
    public Page<ProcessoJudicialDTO> getAllProcessosJudiciais(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProcessoJudicial> processos = this.processoJudicialRepository.findAll(pageable);
        Page<ProcessoJudicialDTO> processosDTO = processos.map(processo -> new ProcessoJudicialDTO(processo));
        return processosDTO;
    }

    /**
     * Deleta um processo judicial pelo número.
     *
     * Verifica se o número do processo é válido e se o processo existe antes de
     * deletá-lo.
     *
     * @param numero O número do processo a ser deletado.
     * @throws InvalidInputException Se o número do processo for inválido.
     * @throws ProcessoNotFoundException Se o processo com o número fornecido
     * não existir.
     */
    public void deleteProcessoJudicial(String numero) {
        if (!ValidationUtils.isValidNumeroProcesso(numero)) {
            throw new InvalidInputException("O número do processo não pode ser nulo nem vazio.");
        }
        if (!processoJudicialRepository.existsById(numero)) {
            throw new ProcessoNotFoundException("Processo inexistente. O número do processo ao qual se quer deletar não existe no sistema.");
        } else {
            this.processoJudicialRepository.deleteById(numero);
        }
    }
}

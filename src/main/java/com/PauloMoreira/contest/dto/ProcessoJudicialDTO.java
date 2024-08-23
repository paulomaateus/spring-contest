package com.PauloMoreira.contest.dto;

import com.PauloMoreira.contest.model.ProcessoJudicial;

/**
 * Data Transfer Object (DTO) para a entidade {@link ProcessoJudicial}.
 *
 * Esta classe é usada para transferir dados do processo judicial entre as
 * camadas da aplicação, principalmente para exposições via APIs. Contém
 * informações sobre o processo judicial e, opcionalmente, o CPF do réu
 * associado.
 */
public class ProcessoJudicialDTO {

    /**
     * Construtor que inicializa o DTO com base em uma instância de
     * {@link ProcessoJudicial}.
     *
     * @param processoJudicial A instância de ProcessoJudicial a partir da qual
     * os dados serão extraídos.
     */
    public ProcessoJudicialDTO(ProcessoJudicial processoJudicial) {
        this.numero = processoJudicial.getNumero();
        this.status = processoJudicial.getStatus();
        this.descricao = processoJudicial.getDescricao();
        if (processoJudicial.getReu() != null) {
            this.cpfReu = processoJudicial.getReu().getCpf();
        }
    }

    /**
     * Número do processo judicial.
     */
    private String numero;

    /**
     * Status atual do processo judicial.
     */
    private String status;

    /**
     * Descrição do processo judicial.
     */
    private String descricao;

    /**
     * CPF do réu associado ao processo judicial. Pode ser nulo se não houver
     * réu associado.
     */
    private String cpfReu;

    /**
     * Obtém o número do processo judicial.
     *
     * @return O número do processo judicial.
     */
    public String getNumero() {
        return this.numero;
    }

    /**
     * Define o número do processo judicial.
     *
     * @param numero O número do processo judicial.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtém o status atual do processo judicial.
     *
     * @return O status atual do processo judicial.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Define o status atual do processo judicial.
     *
     * @param status O status atual do processo judicial.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtém a descrição do processo judicial.
     *
     * @return A descrição do processo judicial.
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Define a descrição do processo judicial.
     *
     * @param descricao A descrição do processo judicial.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém o CPF do réu associado ao processo judicial.
     *
     * @return O CPF do réu, ou null se não houver réu associado.
     */
    public String getCpfReu() {
        return this.cpfReu;
    }

    /**
     * Define o CPF do réu associado ao processo judicial.
     *
     * @param cpfReu O CPF do réu, ou null se não houver réu associado.
     */
    public void setCpfReu(String cpfReu) {
        this.cpfReu = cpfReu;
    }
}

package com.PauloMoreira.contest.model;

import com.PauloMoreira.contest.dto.ReuDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * Classe que representa um réu em um processo judicial.
 *
 * Esta entidade é mapeada para a tabela de réus no banco de dados. Cada réu é
 * identificado por um CPF único e está associado a um processo judicial.
 */
@Entity
public class Reu {

    /**
     * Construtor que inicializa um réu com base em um DTO e um processo
     * judicial.
     *
     * @param reuDTO Objeto de transferência de dados que contém informações do
     * réu.
     * @param processoJudicial Processo judicial ao qual o réu está associado.
     */
    public Reu(ReuDTO reuDTO, ProcessoJudicial processoJudicial) {
        this.cpf = reuDTO.getCpf();
        this.nome = reuDTO.getNome();
        this.processo = processoJudicial;
    }

    /**
     * Construtor padrão.
     */
    public Reu() {
    }

    /**
     * CPF do réu.
     *
     * Este campo é a chave primária e deve ser único e não nulo.
     */
    @Id
    @Column(nullable = false, unique = true)
    private String cpf;

    /**
     * Nome do réu.
     *
     * Este campo é obrigatório e não pode ser nulo.
     */
    @Column(nullable = false)
    private String nome;

    /**
     * Relação com a entidade ProcessoJudicial.
     *
     * Um réu está associado a um processo judicial específico. A coluna
     * 'processo_numero' na tabela de réus faz referência ao campo 'numero' na
     * tabela de processos judiciais.
     */
    @OneToOne
    @JoinColumn(name = "processo_numero", referencedColumnName = "numero", nullable = false)
    private ProcessoJudicial processo;

    /**
     * Obtém o CPF do réu.
     *
     * @return O CPF do réu.
     */
    public String getCpf() {
        return this.cpf;
    }

    /**
     * Define o CPF do réu.
     *
     * @param cpf O CPF a ser definido para o réu.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Obtém o nome do réu.
     *
     * @return O nome do réu.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Define o nome do réu.
     *
     * @param nome O nome a ser definido para o réu.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o processo judicial associado ao réu.
     *
     * @return O processo judicial associado ao réu.
     */
    public ProcessoJudicial getProcesso() {
        return this.processo;
    }

    /**
     * Define o processo judicial associado ao réu.
     *
     * @param processo O processo judicial a ser associado ao réu.
     */
    public void setProcesso(ProcessoJudicial processo) {
        this.processo = processo;
    }
}

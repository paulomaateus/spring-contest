package com.PauloMoreira.contest.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * Classe que representa um processo judicial.
 *
 * Esta entidade é mapeada para a tabela de processos judiciais no banco de
 * dados. Cada processo é identificado por um número único.
 */
@Entity
public class ProcessoJudicial {

    /**
     * Número do processo judicial.
     *
     * Este campo é a chave primária e deve ser único e não nulo.
     */
    @Id
    @Column(nullable = false, unique = true)
    private String numero;

    /**
     * Descrição do processo judicial.
     *
     * Este campo pode conter informações adicionais sobre o processo.
     */
    private String descricao;

    /**
     * Status atual do processo judicial.
     *
     * Este campo indica o estado do processo, como 'Ativo', 'Arquivado', etc.
     */
    private String status;

    /**
     * Relação com a entidade Reu.
     *
     * Um processo pode ter um réu associado. O relacionamento é mapeado pela
     * entidade `Reu` e pode ser removido automaticamente se o processo for
     * removido.
     */
    @OneToOne(mappedBy = "processo", cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
    private Reu reu;

    /**
     * Obtém o réu associado ao processo.
     *
     * @return O réu associado ao processo.
     */
    public Reu getReu() {
        return this.reu;
    }

    /**
     * Define o réu associado ao processo.
     *
     * @param reu O réu a ser associado ao processo.
     */
    public void setReu(Reu reu) {
        this.reu = reu;
    }

    /**
     * Obtém o número do processo.
     *
     * @return O número do processo.
     */
    public String getNumero() {
        return this.numero;
    }

    /**
     * Define o número do processo.
     *
     * @param numero O número do processo a ser definido.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtém a descrição do processo.
     *
     * @return A descrição do processo.
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Define a descrição do processo.
     *
     * @param descricao A descrição a ser definida para o processo.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém o status do processo.
     *
     * @return O status do processo.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Define o status do processo.
     *
     * @param status O status a ser definido para o processo.
     */
    public void setStatus(String status) {
        this.status = status;
    }

}

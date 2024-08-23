package com.PauloMoreira.contest.dto;

/**
 * Data Transfer Object (DTO) para a entidade Réu.
 *
 * Esta classe é utilizada para transferir dados do réu entre as camadas da
 * aplicação, principalmente para exposições via APIs. Contém informações sobre
 * o réu, incluindo o CPF, o nome, e o número do processo judicial associado.
 */
public class ReuDTO {

    /**
     * CPF do réu.
     */
    private String cpf;

    /**
     * Nome do réu.
     */
    private String nome;

    /**
     * Número do processo judicial associado ao réu.
     */
    private String numeroProcesso;

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
     * @param cpf O CPF do réu.
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
     * @param nome O nome do réu.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o número do processo judicial associado ao réu.
     *
     * @return O número do processo judicial associado.
     */
    public String getNumeroProcesso() {
        return this.numeroProcesso;
    }

    /**
     * Define o número do processo judicial associado ao réu.
     *
     * @param numeroProcesso O número do processo judicial associado.
     */
    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }
}

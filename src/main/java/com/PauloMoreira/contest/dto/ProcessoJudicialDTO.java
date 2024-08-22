package com.PauloMoreira.contest.dto;

import com.PauloMoreira.contest.model.ProcessoJudicial;

public class ProcessoJudicialDTO {

    public ProcessoJudicialDTO(ProcessoJudicial processoJudicial) {
        this.numero = processoJudicial.getNumero();
        this.status = processoJudicial.getStatus();
        this.descricao = processoJudicial.getDescricao();
        if (processoJudicial.getReu() != null) {
            this.cpfReu = processoJudicial.getReu().getCpf();
        }
    }

    private String numero;

    private String status;

    private String descricao;

    private String cpfReu;

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCpfReu() {
        return this.cpfReu;
    }

    public void setCpfReu(String cpfReu) {
        this.cpfReu = cpfReu;
    }
}

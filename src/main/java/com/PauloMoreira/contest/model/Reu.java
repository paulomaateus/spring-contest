package com.PauloMoreira.contest.model;

import com.PauloMoreira.contest.dto.ReuDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Reu {

    public Reu(ReuDTO reuDTO, ProcessoJudicial processoJudicial) {
        this.cpf = reuDTO.getCpf();
        this.nome = reuDTO.getNome();
        this.processo = processoJudicial;
    }

    public Reu() {

    }

    @Id
    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @OneToOne
    @JoinColumn(name = "processo_numero", referencedColumnName = "numero", nullable = false)
    private ProcessoJudicial processo;

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ProcessoJudicial getProcesso() {
        return this.processo;
    }

    public void setProcesso(ProcessoJudicial processo) {
        this.processo = processo;
    }
}

package com.PauloMoreira.contest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ProcessoJudicial {

    @Id
    @Column(nullable = false, unique = true)
    private String numero;

    private String descricao;

    private String status;

    @OneToOne
    @JoinColumn(name = "cpf_reu", referencedColumnName = "cpf", insertable = false, updatable = true)
    private Reu reu;

    public Reu getReu() {
        return this.reu;
    }

    public void setReu(Reu reu) {
        this.reu = reu;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

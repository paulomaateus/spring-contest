package com.PauloMoreira.contest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PauloMoreira.contest.model.ProcessoJudicial;

/**
 * Repositório para a entidade ProcessoJudicial.
 *
 * Esta interface estende JpaRepository e fornece métodos para realizar
 * operações de CRUD (Criar, Ler, Atualizar e Deletar) na entidade
 * ProcessoJudicial.
 *
 * O JpaRepository é uma interface do Spring Data JPA que fornece uma
 * implementação padrão para operações de persistência em um banco de dados
 * relacional.
 *
 * @see ProcessoJudicial
 */
public interface ProcessoJudicialRepository extends JpaRepository<ProcessoJudicial, String> {
}

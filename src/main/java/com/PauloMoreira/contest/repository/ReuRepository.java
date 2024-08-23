package com.PauloMoreira.contest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PauloMoreira.contest.model.Reu;

/**
 * Repositório para a entidade Reu.
 *
 * Esta interface estende JpaRepository e fornece métodos para realizar
 * operações de CRUD (Criar, Ler, Atualizar e Deletar) na entidade Reu.
 *
 * O JpaRepository é uma interface do Spring Data JPA que fornece uma
 * implementação padrão para operações de persistência em um banco de dados
 * relacional.
 *
 * @see Reu
 */
public interface ReuRepository extends JpaRepository<Reu, String> {
}

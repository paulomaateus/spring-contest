package com.PauloMoreira.contest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PauloMoreira.contest.model.ProcessoJudicial;

public interface ProcessoJudicialRepository extends JpaRepository<ProcessoJudicial, String> {
}

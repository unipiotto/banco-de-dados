package com.bancoDeDados.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class MatriculaRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BigDecimal getNotaFinalPorIdDisciplina(Long idDisciplina) {
        String sql = "SELECT nota_final FROM matricula WHERE disciplina_ID = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, idDisciplina);
    }

    public Long getMatriculaIdPorIdDisciplinaEIdDiscente(Long idDisciplina, Long idDiscente) {
        String sql = "SELECT ID_matricula FROM matricula WHERE disciplina_ID = ? AND discente_ID = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, idDisciplina, idDiscente);
    }
}

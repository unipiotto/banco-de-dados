package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.CursoDisciplina;
import com.bancoDeDados.model.mapper.DepartamentoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CursoDisciplinaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void salvarRelacao(Long idCurso, Long idDisciplina) {
        String sql = "INSERT INTO curso_disciplina (curso_id, disciplina_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, idCurso, idDisciplina);
    }
}


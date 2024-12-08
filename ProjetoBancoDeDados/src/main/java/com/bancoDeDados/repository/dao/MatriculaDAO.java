package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Matricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MatriculaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void adicionarMatricula(Matricula matricula) {
        String sql = """
            INSERT INTO matricula (discente_id, disciplina_id, ano_matricula, semestre)
            VALUES (?, ?, ?, ?);
        """;
        jdbcTemplate.update(sql, matricula.getIdDiscente(), matricula.getIdDisciplina(),
                matricula.getAnoMatricula(), matricula.getSemestre());
    }
}

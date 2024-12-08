package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Disciplina;
import com.bancoDeDados.model.mapper.DisciplinaRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DisciplinaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Disciplina> buscarDisciplinasDeDiscente(Long discenteId) {
        String sql = """
            SELECT d.* FROM disciplinas d
            JOIN matricula m ON d.ID_disciplina = m.disciplina_ID
            WHERE m.discente_ID = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{discenteId}, new DisciplinaRowMapper());
    }

    public List<Disciplina> buscarDisciplinasPorCurso(Long idCurso) {
        String sql = """
            SELECT d.* FROM disciplinas d
            JOIN curso_disciplina cd ON d.ID_disciplina = cd.disciplina_ID
            WHERE cd.curso_ID = ?
            ORDER BY d.nome_disciplina
        """;
        return jdbcTemplate.query(sql, new Object[]{idCurso}, new DisciplinaRowMapper());
    }

    public Disciplina buscarDisciplinaPorId(Long id) {
        String sql = "SELECT * FROM disciplinas WHERE id_disciplina = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new DisciplinaRowMapper());
    }
}

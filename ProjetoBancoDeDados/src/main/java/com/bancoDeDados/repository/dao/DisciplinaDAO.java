package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Disciplina;
import com.bancoDeDados.model.Professor;
import com.bancoDeDados.model.mapper.DisciplinaRowMapper;
import com.bancoDeDados.model.mapper.ProfessorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DisciplinaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Disciplina> buscarDisciplinasDeDiscente(Long discenteId) {
        String sql = """
            SELECT d.* FROM disciplina d
            JOIN matricula m ON d.ID_disciplina = m.disciplina_ID
            WHERE m.discente_ID = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{discenteId}, new DisciplinaRowMapper());
    }

    public List<Disciplina> buscarDisciplinasPorCurso(Long idCurso) {
        String sql = """
            SELECT d.* FROM disciplina d
            JOIN curso_disciplina cd ON d.ID_disciplina = cd.disciplina_ID
            WHERE cd.curso_ID = ?
            ORDER BY d.nome_disciplina
        """;
        return jdbcTemplate.query(sql, new Object[]{idCurso}, new DisciplinaRowMapper());
    }

    public Disciplina buscarDisciplinaPorId(Long id) {
        String sql = "SELECT * FROM disciplina WHERE id_disciplina = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new DisciplinaRowMapper());
    }

    public Disciplina buscarDisciplinaId(Long idDisciplina) {
        String sql = "SELECT * FROM disciplinas WHERE id_disciplina = ?";
        List<Disciplina> disciplinas = jdbcTemplate.query(sql, new Object[]{idDisciplina}, new DisciplinaRowMapper());
        if (disciplinas.isEmpty()) {
            return null;
        }
        return disciplinas.get(0);
    }

    public Disciplina atualizarDisciplina(Disciplina disciplina) {
        String sql = "UPDATE disciplinas SET nome_disciplina = ?, carga_horaria = ?, valor_mensal = ?, professor_ID = ? WHERE ID_disciplina = ?";
        jdbcTemplate.update(sql, disciplina.getNomeDisciplina(), disciplina.getCargaHoraria(), disciplina.getValorMensal(), disciplina.getIdProfessor(), disciplina.getIdDisciplina());
        return buscarDisciplinaId(disciplina.getIdDisciplina());
    }

    public void deletarDisciplina(Long id) {
        String sql = "DELETE FROM disciplinas WHERE ID_disciplina = ?";
        jdbcTemplate.update(sql, id);
    }

    public Disciplina criarDisciplina(Disciplina disciplina) {
        String sql = "INSERT INTO disciplinas (nome_disciplina, carga_horaria, valor_mensal, professor_ID) " +
                "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id_disciplina"});
            ps.setString(1, disciplina.getNomeDisciplina());
            ps.setObject(2, disciplina.getCargaHoraria());
            ps.setObject(3, disciplina.getValorMensal());
            ps.setObject(4, disciplina.getProfessor().getIdProfessor());
            return ps;
        }, keyHolder);

        Long idGerado = keyHolder.getKey().longValue();
        disciplina.setIdDisciplina(idGerado);
        return disciplina;
    }
}

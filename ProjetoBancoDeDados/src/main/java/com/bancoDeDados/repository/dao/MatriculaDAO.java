package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Matricula;
import com.bancoDeDados.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Matricula buscarMatricula(Long id) {
        String sql = "SELECT * FROM matricula WHERE ID_matricula = ?";
        RowMapper<Matricula> rowMapper = new RowMapper<Matricula>() {
            public Matricula mapRow(ResultSet rs, int rowNum) throws SQLException {
                Matricula matricula = new Matricula();
                matricula.setIdMatricula(rs.getLong("ID_matricula"));
                matricula.setIdDisciplina(rs.getLong("disciplina_ID"));
                matricula.setIdDiscente(rs.getLong("discente_ID"));
                matricula.setAnoMatricula(rs.getInt("ano_matricula"));
                matricula.setSemestre(rs.getInt("semestre"));

                return matricula;
            }
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, id);

    }
}

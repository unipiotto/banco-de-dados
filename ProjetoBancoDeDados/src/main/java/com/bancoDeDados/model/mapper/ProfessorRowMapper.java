package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Professor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProfessorRowMapper implements RowMapper<Professor> {
    @Override
    public Professor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Professor professor = new Professor();
        professor.setIdProfessor(rs.getLong("ID_professor"));
        professor.setIdDepartamento(rs.getLong("departamento_id"));
        professor.setIdPessoa(rs.getLong("id_pessoa"));
        professor.setDataContratacao(LocalDate.parse(rs.getString("data_contratacao")));


        return professor;
    }
}

package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Curso;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CursoRowMapper implements RowMapper<Curso>{

    @Override
    public Curso mapRow(ResultSet rs, int rowNum) throws SQLException {
        Curso curso = new Curso();
        curso.setIdCurso(rs.getLong("ID_curso"));
        curso.setNomeCurso(rs.getString("nome_curso"));
        curso.setIdProfessorCordernador(rs.getLong("professor_coordenador_ID"));
        curso.setDepartamentoId(rs.getLong("departamento_ID"));
        return curso;
    }
}

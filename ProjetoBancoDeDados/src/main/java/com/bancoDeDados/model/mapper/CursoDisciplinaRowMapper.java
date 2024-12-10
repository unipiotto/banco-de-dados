package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Curso;
import com.bancoDeDados.model.CursoDisciplina;
import com.bancoDeDados.model.Disciplina;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CursoDisciplinaRowMapper {
    public static CursoDisciplina mapRow(ResultSet rs, int rowNum) throws SQLException {
        CursoDisciplina cursoDisciplina = new CursoDisciplina();
        cursoDisciplina.setIdCurso(rs.getLong("curso_ID"));
        cursoDisciplina.setIdDisciplina(rs.getLong("disciplina_ID"));
        return cursoDisciplina;
    }
}

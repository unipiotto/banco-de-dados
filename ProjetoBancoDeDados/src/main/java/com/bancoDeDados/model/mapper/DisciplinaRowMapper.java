package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Disciplina;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisciplinaRowMapper implements RowMapper<Disciplina> {

    @Override
    public Disciplina mapRow(ResultSet rs, int rowNum) throws SQLException {
        Disciplina disciplina = new Disciplina();
        disciplina.setIdDisciplina(rs.getLong("id_disciplina"));
        disciplina.setNomeDisciplina(rs.getString("nome_disciplina"));
        disciplina.setCargaHoraria(rs.getInt("carga_horaria"));
        disciplina.setValorMensal(rs.getBigDecimal("valor_mensal"));
        disciplina.setIdProfessor(rs.getLong("professor_id"));

        return disciplina;
    }

}

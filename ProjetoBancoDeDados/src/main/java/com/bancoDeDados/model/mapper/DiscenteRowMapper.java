package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Discente.StatusDiscente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DiscenteRowMapper implements RowMapper<Discente> {
    @Override
    public Discente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Discente discente = new Discente();
        discente.setIdDiscente(rs.getLong("id_discente"));
        discente.setRegistroAcademico(rs.getString("registro_academico"));
        discente.setDataIngresso(LocalDate.parse(rs.getString("data_ingresso")));
        discente.setPessoaId(rs.getLong("pessoa_id"));

        String status = rs.getString("status");
        discente.setStatus(StatusDiscente.fromString(status));

        return discente;
    }
}

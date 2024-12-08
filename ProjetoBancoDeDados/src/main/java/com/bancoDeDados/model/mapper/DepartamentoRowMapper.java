package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Departamento;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartamentoRowMapper implements RowMapper<Departamento> {

    @Override
    public Departamento mapRow(ResultSet rs, int rowNum) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(rs.getLong("ID_departamento"));
        departamento.setNomeDepartamento(rs.getString("nome_departamento"));
        return departamento;
    }
}
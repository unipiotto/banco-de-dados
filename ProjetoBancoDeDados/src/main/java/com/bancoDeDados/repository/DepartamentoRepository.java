package com.bancoDeDados.repository;

import com.bancoDeDados.model.Departamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartamentoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Departamento> listarTodos() {
        try {
            String sql = "SELECT d.ID_departamento, d.nome_departamento FROM departamentos d ORDER BY d.ID_departamento ASC";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Departamento departamento = new Departamento();
                departamento.setIdDepartamento(rs.getLong("ID_departamento"));
                departamento.setNomeDepartamento(rs.getString("nome_departamento"));
                return departamento;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

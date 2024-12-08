package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Departamento;
import com.bancoDeDados.model.mapper.DepartamentoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DepartamentoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Departamento buscarDepartamentoPorId(Long id) {
        String sql = "SELECT * FROM departamentos WHERE ID_departamento = ?";
        return jdbcTemplate.queryForObject(sql, new DepartamentoRowMapper(), id);
    }
}
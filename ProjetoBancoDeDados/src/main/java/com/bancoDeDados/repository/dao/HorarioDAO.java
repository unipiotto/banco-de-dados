package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Horario;
import com.bancoDeDados.model.mapper.HorarioRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HorarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Horario> buscarHorariosDeDisciplina(Long disciplinaId) {
        String sql = "SELECT * FROM horario WHERE disciplina_id = ?";
        return jdbcTemplate.query(sql, new Object[]{disciplinaId}, new HorarioRowMapper());
    }
}

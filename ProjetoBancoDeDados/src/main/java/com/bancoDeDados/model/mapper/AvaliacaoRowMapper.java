package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Avaliacao;
import com.bancoDeDados.model.AvaliacaoProfessor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AvaliacaoRowMapper implements RowMapper<Avaliacao> {

    @Override
    public Avaliacao mapRow(ResultSet rs, int rowNum) throws SQLException {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdAvaliacao(rs.getLong("ID_avaliacao"));
        avaliacao.setNota(rs.getBigDecimal("nota"));
        avaliacao.setPeso(rs.getBigDecimal("peso"));
        avaliacao.setIdMatricula(rs.getLong("matricula_ID"));

        return avaliacao;
    }
}

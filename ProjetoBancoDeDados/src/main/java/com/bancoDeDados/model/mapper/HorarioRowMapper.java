package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Endereco;
import com.bancoDeDados.model.Horario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class HorarioRowMapper implements RowMapper<Horario> {
    @Override
    public Horario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Horario horario = new Horario();
        horario.setIdDisciplina(rs.getLong("disciplina_id"));
        String diaSemana = rs.getString("dia_semana");
        if (diaSemana != null) {
            horario.setDiaSemana(Horario.DiaSemana.fromString(diaSemana));
        }
        horario.setHoraInicio(rs.getTime("horario_inicio").toLocalTime());
        horario.setDuracao(rs.getInt("duracao"));
        horario.setNumeroSala(rs.getString("numero_sala"));
        return horario;
    }
}

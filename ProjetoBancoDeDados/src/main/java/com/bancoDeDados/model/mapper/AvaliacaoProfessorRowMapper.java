package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.AvaliacaoProfessor;
import com.bancoDeDados.model.Professor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AvaliacaoProfessorRowMapper implements RowMapper<AvaliacaoProfessor> {
    @Override
    public AvaliacaoProfessor mapRow(ResultSet rs, int rowNum) throws SQLException {
        AvaliacaoProfessor avaliacaoProfessor = new AvaliacaoProfessor();
        avaliacaoProfessor.setIdAvaliacao(rs.getLong("ID_avaliacao"));
        avaliacaoProfessor.setIdDiscente(rs.getLong("discente_ID"));
        avaliacaoProfessor.setIdProfessor(rs.getLong("professor_ID"));
        avaliacaoProfessor.setDataAvaliacao(LocalDate.parse(rs.getString("data_avaliacao")));
        avaliacaoProfessor.setNotaAvaliacao(rs.getInt("nota_ensino"));
        avaliacaoProfessor.setComentario(rs.getString("comentario"));

        return avaliacaoProfessor;
    }
}

package com.bancoDeDados.repository;

import com.bancoDeDados.model.AvaliacaoProfessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AvaliacaoProfessorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AvaliacaoProfessor> listar() {
        String sql = "SELECT * FROM avaliacao_professores";
        return jdbcTemplate.query(sql, (rs, _) -> {
            AvaliacaoProfessor a = new AvaliacaoProfessor();
            a.setIdAvaliacao(rs.getLong("ID_avaliacao"));
            a.setIdProfessor(rs.getLong("professor_ID"));
            a.setIdDiscente(rs.getLong("discente_ID"));
            a.setNotaAvaliacao(rs.getInt("nota_ensino"));
            a.setComentario(rs.getString("comentario"));
            a.setDataAvaliacao(LocalDate.parse(rs.getString("data_avaliacao")));
            return a;
        });
    }

    public List<AvaliacaoProfessor> listarPorIdProfessor(long professorID) {
        String sql = "SELECT * FROM avaliacao_professores WHERE professor_ID = ?";

        return jdbcTemplate.query(sql, (rs, _) -> {
            AvaliacaoProfessor a = new AvaliacaoProfessor();
            a.setIdAvaliacao(rs.getLong("ID_avaliacao"));
            a.setIdProfessor(rs.getLong("professor_ID"));
            a.setIdDiscente(rs.getLong("discente_ID"));
            a.setNotaAvaliacao(rs.getInt("nota_ensino"));
            a.setComentario(rs.getString("comentario"));
            a.setDataAvaliacao(LocalDate.parse(rs.getString("data_avaliacao")));
            return a;
        }, professorID);
    }
}

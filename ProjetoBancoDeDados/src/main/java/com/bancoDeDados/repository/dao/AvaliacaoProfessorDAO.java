package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.AvaliacaoProfessor;
import com.bancoDeDados.model.mapper.AvaliacaoProfessorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AvaliacaoProfessorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AvaliacaoProfessor buscarPorId(Long id){
        String sql = "SELECT * FROM avaliacao_professores WHERE id = ?";
        AvaliacaoProfessor avaliacaoProfessor = jdbcTemplate.queryForObject(sql, new AvaliacaoProfessorRowMapper(), id);

        return avaliacaoProfessor;
    }

    public void inserir(AvaliacaoProfessor avaliacaoProfessor){
        String sql = "INSERT INTO avaliacao_professores (discente_ID, professor_ID, data_avaliacao, nota_ensino, comentario) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, avaliacaoProfessor.getIdDiscente(), avaliacaoProfessor.getIdProfessor(),
                avaliacaoProfessor.getDataAvaliacao(), avaliacaoProfessor.getNotaAvaliacao(), avaliacaoProfessor.getComentario());
    }

}

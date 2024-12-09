package com.bancoDeDados.repository;

import com.bancoDeDados.model.Avaliacao;
import com.bancoDeDados.model.AvaliacaoProfessor;
import com.bancoDeDados.repository.dao.AvaliacaoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AvaliacaoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AvaliacaoDAO avaliacaoDAO;

    public List<Avaliacao> listarNotasDiscente(Long id) {
        String sql = """
                SELECT * FROM avaliacoes 
                WHERE matricula_ID = (SELECT ID_matricula 
                FROM matricula WHERE discente_ID = ?)
                """;
        return jdbcTemplate.query(sql, (rs, _) -> {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setIdAvaliacao(rs.getLong("ID_avaliacao"));
            avaliacao.setNota(rs.getBigDecimal("nota"));
            avaliacao.setPeso(rs.getBigDecimal("peso"));
            avaliacao.setIdMatricula(rs.getLong("matricula_ID"));

            return avaliacao;
        }, id);
    }

    public List<Avaliacao> listarNotasDiscentePorDisciplina(Long idDiscente, Long idDisciplina) {
        String sql = """
                SELECT * FROM avaliacoes 
                WHERE matricula_ID = (SELECT ID_matricula 
                FROM matricula WHERE discente_ID = ? AND disciplina_ID = ?)
                """;
        return jdbcTemplate.query(sql, (rs, _) -> {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setIdAvaliacao(rs.getLong("ID_avaliacao"));
            avaliacao.setNota(rs.getBigDecimal("nota"));
            avaliacao.setPeso(rs.getBigDecimal("peso"));
            avaliacao.setIdMatricula(rs.getLong("matricula_ID"));

            return avaliacao;
        }, idDiscente, idDisciplina);
    }

    public void inserir(Avaliacao avaliacao) {
        avaliacaoDAO.inserir(avaliacao);
    }

    public void atualizar(Avaliacao avaliacao) {
        avaliacaoDAO.atualizar(avaliacao);
    }

    public void buscarPorId(Long id) {
        avaliacaoDAO.buscarPorId(id);
    }

    public Long buscarIdDiscentePorIdAvaliacao(Long idAvaliacao) {
        String sql = """
                SELECT discente_ID
                FROM matricula
                WHERE ID_matricula = (SELECT matricula_ID
                FROM avaliacoes
                WHERE ID_avaliacao = ?)
                """;
        return jdbcTemplate.queryForObject(sql, Long.class, idAvaliacao);
    }

}

package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.mapper.DiscenteRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiscenteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PessoaDAO pessoaDAO;

    public Discente buscarDiscenteCompletoPorId(Long discenteId) {
        String sql = "SELECT * FROM discente WHERE id_discente = ?";
        Discente discente = jdbcTemplate.queryForObject(sql, new DiscenteRowMapper(), discenteId);
        if (discente != null) {
            Pessoa pessoa = pessoaDAO.buscarPessoaComEnderecos(discente.getPessoaId());
            discente.setPessoa(pessoa);
        }
        return discente;
    }

    public List<Discente> listarTodos() {
        String sql = """
        SELECT d.ID_discente, d.registro_academico, d.data_ingresso, d.status,
               p.ID_pessoa, p.nome, p.email, p.telefone, p.cpf, p.data_nascimento
        FROM discente d
        JOIN pessoa p ON d.pessoa_ID = p.ID_pessoa
    """;
        return jdbcTemplate.query(sql, (rs, _) -> {
            Discente discente = new Discente();
            discente.setIdDiscente(rs.getLong("ID_discente"));
            discente.setRegistroAcademico(rs.getString("registro_academico"));
            discente.setDataIngresso(rs.getDate("data_ingresso").toLocalDate());
            discente.setStatus(Discente.StatusDiscente.valueOf(rs.getString("status").toUpperCase()));

            Pessoa pessoa = new Pessoa();
            pessoa.setIdPessoa(rs.getLong("ID_pessoa"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setTelefone(rs.getString("telefone"));
            pessoa.setCpf(rs.getString("cpf"));
            pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
            discente.setPessoa(pessoa);

            return discente;
        });
    }

    public void inserirDiscente(Discente discente) {
        String sql = "INSERT INTO discente (pessoa_ID, registro_academico, data_ingresso, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, discente.getPessoaId(), discente.getRegistroAcademico(),
                discente.getDataIngresso(), discente.getStatusFormatado());
    }

    public int contarDiscentesCadastrados() {
        String sql = "SELECT COUNT(*) FROM discente";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}

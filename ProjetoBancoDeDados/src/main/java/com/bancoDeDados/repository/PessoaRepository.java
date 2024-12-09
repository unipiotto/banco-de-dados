package com.bancoDeDados.repository;

import com.bancoDeDados.model.Departamento;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.Professor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PessoaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PessoaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Pessoa pessoa) {
        if (pessoa.getIdPessoa() == 0) {
            String sql = "INSERT INTO pessoa (nome, email, telefone, cpf, data_nascimento) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(), pessoa.getCpf(), pessoa.getDataNascimento());
        } else {
            String sql = "UPDATE pessoa SET nome = ?, email = ?, telefone = ?, cpf = ?, data_nascimento = ? WHERE id_pessoa = ?";
            jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(), pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getIdPessoa());
        }
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        String sql = "SELECT * FROM pessoa WHERE id_pessoa = ?";
        try {
            Pessoa pessoa = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Pessoa.class));
            return Optional.of(pessoa);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Pessoa> listarProfessoresEDiscentes() {
        String sql = """
                (SELECT DISTINCT P.nome, P.ID_pessoa, P.email, P.telefone, P.cpf, P.data_nascimento
                FROM professor Pr
                JOIN pessoa P ON Pr.pessoa_ID = P.ID_pessoa
                INTERSECT
                SELECT DISTINCT P.nome, P.ID_pessoa, P.email, P.telefone, P.cpf, P.data_nascimento
                FROM discente D
                JOIN pessoa P ON D.pessoa_ID = P.ID_pessoa)
                UNION
                (SELECT DISTINCT P.nome, P.ID_pessoa, P.email, P.telefone, P.cpf, P.data_nascimento
                FROM professor Pr
                JOIN pessoa P ON Pr.pessoa_ID = P.ID_pessoa
                EXCEPT
                SELECT DISTINCT P.nome, P.ID_pessoa, P.email, P.telefone, P.cpf, P.data_nascimento
                FROM discente D
                JOIN pessoa P ON D.pessoa_ID = P.ID_pessoa)
                UNION
                (SELECT DISTINCT P.nome, P.ID_pessoa, P.email, P.telefone, P.cpf, P.data_nascimento
                FROM discente D
                JOIN pessoa P ON D.pessoa_ID = P.ID_pessoa
                EXCEPT
                SELECT DISTINCT P.nome, P.ID_pessoa, P.email, P.telefone, P.cpf, P.data_nascimento
                FROM professor Pr
                JOIN pessoa P ON Pr.pessoa_ID = P.ID_pessoa);
                """;

        return jdbcTemplate.query(sql, (rs, _) -> {
            Pessoa pessoa = new Pessoa();

            pessoa.setIdPessoa(rs.getLong("ID_pessoa"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setTelefone(rs.getString("telefone"));
            pessoa.setCpf(rs.getString("cpf"));
            pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());

            return pessoa;
        });
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
        jdbcTemplate.update(sql, id);
    }

    public String pegarTipo(Long id) {
        String sql = """
                SELECT P.ID_pessoa, P.nome,
                    CASE
                        WHEN D.pessoa_ID IS NOT NULL AND Pr.pessoa_ID IS NOT NULL THEN 'Discente e Professor'
                        WHEN D.pessoa_ID IS NOT NULL THEN 'Discente'
                        WHEN Pr.pessoa_ID IS NOT NULL THEN 'Professor'
                        ELSE 'Nenhum'
                    END AS Papel
                FROM pessoa P
                LEFT JOIN discente D ON P.ID_pessoa = D.pessoa_ID
                LEFT JOIN professor Pr ON P.ID_pessoa = Pr.pessoa_ID
                WHERE P.ID_pessoa = ?;
                """;

        RowMapper<String> rowMapper = (rs, rowNum) -> new String(
                rs.getString("Papel")
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Long pegarIdProfessor(Long id) {
        String sql = """
                SELECT ID_professor
                FROM professor
                WHERE pessoa_ID = ?
                """;
        return jdbcTemplate.queryForObject(sql, Long.class, id);
    }

    public Long pegarIdDiscente(Long id) {
        String sql = """
                SELECT ID_discente
                FROM discente
                WHERE pessoa_ID = ?
                """;
        return jdbcTemplate.queryForObject(sql, Long.class, id);
    }
}

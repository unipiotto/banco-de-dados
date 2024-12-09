package com.bancoDeDados.repository;

import com.bancoDeDados.model.Departamento;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfessorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Professor> listar() {
        String sql = """
        SELECT pr.ID_professor, pr.data_contratacao,
               pe.ID_pessoa, pe.nome, pe.email, pe.telefone, pe.cpf, pe.data_nascimento,
               d.ID_departamento, d.nome_departamento
        FROM professor pr 
        JOIN pessoa pe ON pr.pessoa_ID = pe.ID_pessoa
        JOIN departamento d ON pr.departamento_ID = d.ID_departamento
        """;
        return jdbcTemplate.query(sql, (rs, _) -> {
            Professor professor = new Professor();
            professor.setIdProfessor(rs.getLong("ID_professor"));
            professor.setDataContratacao(rs.getDate("data_contratacao").toLocalDate());

            Pessoa pessoa = new Pessoa();
            pessoa.setIdPessoa(rs.getLong("ID_pessoa"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setTelefone(rs.getString("telefone"));
            pessoa.setCpf(rs.getString("cpf"));
            pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());

            Departamento departamento = new Departamento();
            departamento.setIdDepartamento(rs.getLong("ID_departamento"));
            departamento.setNomeDepartamento(rs.getString("nome_departamento"));

            professor.setPessoa(pessoa);
            professor.setDepartamento(departamento);

            return professor;
        });

    }
}

package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Curso;
import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Disciplina;
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
    @Autowired
    private CursoDAO cursoDAO;
    @Autowired
    private DisciplinaDAO disciplinaDAO;

    public Discente buscarDiscenteCompletoPorId(Long discenteId) {
        String sql = """
            SELECT
                d.id_discente,
                d.registro_academico,
                d.data_ingresso,
                UPPER(d.status_discente) AS status_discente,
                d.pessoa_id,
                d.curso_id
            FROM discente d
            WHERE id_discente = ?
        """;
        Discente discente = jdbcTemplate.queryForObject(sql, new DiscenteRowMapper(), discenteId);
        if (discente != null) {
            Pessoa pessoa = pessoaDAO.buscarPessoaComEnderecos(discente.getPessoaId());
            discente.setPessoa(pessoa);
            Curso curso = cursoDAO.buscarCursoPorId(discente.getCursoId());
            discente.setCurso(curso);
        }
        return discente;
    }

    public List<Discente> listarTodos() {
        String sql = """
            SELECT
                d.id_discente,
                d.registro_academico,
                d.data_ingresso,
                d.status_discente,
                d.curso_id,
                p.id_pessoa,
                p.nome,
                p.email,
                p.telefone,
                p.cpf,
                p.data_nascimento,
                c.id_curso,
                c.departamento_ID,
                c.professor_coordenador_ID,
                c.nome_curso
            FROM
                discente d
            JOIN
                pessoa p ON d.pessoa_id = p.id_pessoa
            JOIN
                curso c ON d.curso_id = c.id_curso
        """;
        return jdbcTemplate.query(sql, (rs, _) -> {
            Discente discente = new Discente();
            discente.setIdDiscente(rs.getLong("ID_discente"));
            discente.setRegistroAcademico(rs.getString("registro_academico"));
            discente.setDataIngresso(rs.getDate("data_ingresso").toLocalDate());
            discente.setStatus(Discente.StatusDiscente.valueOf(rs.getString("status_discente").toUpperCase()));
            discente.setCursoId(rs.getLong("curso_id"));

            Curso curso = new Curso();
            curso.setIdCurso(rs.getLong("id_curso"));
            curso.setDepartamentoId(rs.getLong("departamento_id"));
            curso.setIdProfessorCordernador(rs.getLong("professor_coordenador_ID"));
            curso.setNomeCurso(rs.getString("nome_curso"));
            discente.setCurso(curso);

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
        String sql = "INSERT INTO discente (pessoa_ID, registro_academico, data_ingresso, status_discente, curso_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, discente.getPessoaId(), discente.getRegistroAcademico(),
                discente.getDataIngresso(), discente.getStatusFormatado(), discente.getCursoId());
    }

    public int contarDiscentesCadastrados() {
        String sql = "SELECT COUNT(*) FROM discente";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}

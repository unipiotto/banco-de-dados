package com.bancoDeDados.repository;

import com.bancoDeDados.model.Disciplina;
import com.bancoDeDados.model.Professor;
import com.bancoDeDados.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DisciplinaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Disciplina> listarTodos() {
        try {
            String sql = "SELECT d.ID_disciplina, d.nome_disciplina, d.carga_horaria, d.valor_mensal, d.professor_ID FROM disciplina d ORDER BY d.ID_disciplina ASC";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Disciplina disciplina = new Disciplina();
                disciplina.setIdDisciplina(rs.getLong("ID_disciplina"));
                disciplina.setNomeDisciplina(rs.getString("nome_disciplina"));
                disciplina.setCargaHoraria(rs.getInt("carga_horaria"));
                disciplina.setValorMensal(rs.getBigDecimal("valor_mensal"));
                disciplina.setIdProfessor(rs.getLong("professor_ID"));
                return disciplina;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Professor> buscarProfessoresPorDisciplina(Long idDisciplina) {
        try {
            String sql = "SELECT p.ID_professor, p.pessoa_ID, p.departamento_ID, p.data_contratacao, pe.nome AS pessoa_nome " +
                    "FROM professor p " +
                    "JOIN disciplina d ON p.ID_professor = d.professor_ID " +
                    "JOIN pessoa pe ON p.pessoa_ID = pe.ID_pessoa " +
                    "WHERE d.ID_disciplina = ?";

            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Professor professor = new Professor();
                professor.setIdProfessor(rs.getLong("ID_professor"));
                professor.setIdPessoa(rs.getLong("pessoa_ID"));
                professor.setIdDepartamento(rs.getLong("departamento_ID"));
                professor.setDataContratacao(rs.getDate("data_contratacao").toLocalDate());

                // Crie a instância de Pessoa e atribua o nome
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(rs.getString("pessoa_nome"));
                professor.setPessoa(pessoa);

                return professor;
            }, idDisciplina);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Disciplina> buscarDisciplinasPorCurso(Long idCurso) {
        try {
            String sql = "SELECT d.ID_disciplina, d.nome_disciplina " +
                    "FROM disciplina d " +
                    "JOIN curso_disciplina cd ON d.ID_disciplina = cd.disciplina_ID " +
                    "JOIN curso c ON cd.curso_ID = c.ID_curso " +
                    "WHERE c.ID_curso = ?";

            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Disciplina disciplina = new Disciplina();
                disciplina.setIdDisciplina(rs.getLong("ID_disciplina"));
                disciplina.setNomeDisciplina(rs.getString("nome_disciplina"));

                return disciplina;
            }, idCurso);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}


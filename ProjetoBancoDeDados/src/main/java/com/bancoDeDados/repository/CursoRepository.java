package com.bancoDeDados.repository;

import com.bancoDeDados.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Curso> listarTodos() {
        try{
            String sql = "SELECT c.ID_curso, c.nome_curso, c.professor_coordenador_ID, departamento_ID FROM curso c ORDER BY c.ID_curso ASC";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getLong("ID_curso"));
                curso.setNomeCurso(rs.getString("nome_curso"));
                curso.setIdProfessorCordernador(rs.getLong("professor_coordenador_ID"));
                curso.setDepartamentoId(rs.getLong("departamento_ID"));
                return curso;
            });
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Curso> listarCursosPorDepartamento(Long departamentoId) {
        try {
            String sql = "SELECT c.ID_curso, c.nome_curso, c.professor_coordenador_ID, c.departamento_ID " +
                    "FROM curso c " +
                    "WHERE c.departamento_ID = ? " +
                    "ORDER BY c.ID_curso ASC";
            return jdbcTemplate.query(sql, new Object[]{departamentoId}, (rs, rowNum) -> {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getLong("ID_curso"));
                curso.setNomeCurso(rs.getString("nome_curso"));
                curso.setIdProfessorCordernador(rs.getLong("professor_coordenador_ID"));
                curso.setDepartamentoId(rs.getLong("departamento_ID"));
                return curso;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

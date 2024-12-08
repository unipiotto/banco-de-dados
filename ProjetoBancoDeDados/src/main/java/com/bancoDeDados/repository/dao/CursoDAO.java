package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Curso;
import com.bancoDeDados.model.mapper.CursoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;


@Repository
public class CursoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Curso buscarCursoPorId(Long id){
        String sql = "SELECT * FROM cursos WHERE ID_curso = ?";
        return jdbcTemplate.queryForObject(sql, new CursoRowMapper(), id);
    }

    public Curso atualizarCurso(Curso curso) {
        String sql = "UPDATE cursos SET nome_curso = ?, professor_coordenador_ID = ?, departamento_ID = ? WHERE ID_curso = ?";
        jdbcTemplate.update(sql, curso.getNomeCurso(), curso.getIdProfessorCordernador(), curso.getDepartamentoId(), curso.getIdCurso());
        return buscarCursoPorId(curso.getIdCurso());
    }

    public void deletarCurso(Long id) {
        String sql = "DELETE FROM cursos WHERE ID_curso = ?";
        jdbcTemplate.update(sql, id);
    }

    public Curso criarCurso(Curso curso) {
        String sql = "INSERT INTO cursos (nome_curso, professor_coordenador_ID, departamento_ID) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, curso.getNomeCurso());
            ps.setObject(2, curso.getIdProfessorCordernador());
            ps.setObject(3, curso.getDepartamentoId());
            return ps;
        }, keyHolder);
        Long idGerado = keyHolder.getKey().longValue();
        curso.setIdCurso(idGerado);
        return curso;
    }
}

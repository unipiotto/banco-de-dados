package com.bancoDeDados.repository;

import com.bancoDeDados.model.entities.Professor;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfessorRepository {

    private JdbcTemplate jdbcTemplate;

    public void salvar(Professor professor) {
        if(professor.getIdProfessor() == null){
            String sql = "INSERT INTO professor (idPessoa, idDepartamento, dataContratacao) VALUES (?,?,?)";
            jdbcTemplate.update(sql, professor.getIdPessoa(), professor.getIdDepartamento(), professor.getDataContratacao());
        }else{
            String sql = "UPDATE professor SET idPessoa = ?, idDepartamento = ?, dataContratacao = ? WHERE idProfessor = ?";
            jdbcTemplate.update(sql, professor.getIdPessoa(), professor.getIdDepartamento(), professor.getDataContratacao(), professor.getIdProfessor());
        }
    }

    public Optional<Professor> buscarPorId(Long id){
        String sql = "SELECT * FROM professor WHERE idProfessor = ?";
        try{
            Professor professor = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Professor.class));
            return Optional.of(professor);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deletar(Long id){
        String sql = "DELETE FROM professor WHERE idProfessor = ?";
        jdbcTemplate.update(sql, id);
    }
}

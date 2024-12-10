package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Horario;
import com.bancoDeDados.model.mapper.HorarioRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Time;


@Repository
public class HorarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Horario> buscarHorariosDeDisciplina(Long disciplinaId) {
        String sql = "SELECT * FROM horario WHERE disciplina_id = ?";
        return jdbcTemplate.query(sql, new Object[]{disciplinaId}, new HorarioRowMapper());
    }

    public void removerHorarioDaDisciplina(Long disciplinaId, Long horarioId) {
        String sql = "UPDATE horarios SET disciplina_ID = NULL WHERE disciplina_ID = ? AND ID_horario = ?";
        jdbcTemplate.update(sql, disciplinaId, horarioId);
    }

    public Map<String, List<Horario>> buscarHorariosDisciplinaProfessor(Long idDisciplina) {
        String sql = "SELECT h.*, d.professor_ID, p.nome " +
                "FROM horarios h " +
                "JOIN disciplinas d ON d.ID_disciplina = h.disciplina_ID " +
                "JOIN professores pr ON pr.ID_professor = d.professor_ID " +
                "JOIN pessoa p ON p.ID_pessoa = pr.pessoa_ID " +
                "WHERE h.disciplina_ID = ?";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, idDisciplina);

        Map<String, List<Horario>> horariosPorProfessor = new HashMap<>();
        for (Map<String, Object> row : rows) {
            String professorNome = (String) row.get("nome");

            Horario horario = new Horario();
            horario.setIdHora(((Number) row.get("id_horario")).longValue());
            horario.setIdDisciplina(((Number) row.get("disciplina_id")).longValue());
            horario.setDiaSemana(Horario.DiaSemana.fromString((String) row.get("dia_semana")));
            horario.setHoraInicio(((Time) row.get("horario_inicio")).toLocalTime());
            horario.setDuracao((Integer) row.get("duracao"));
            horario.setNumeroSala((String) row.get("numero_sala"));

            horariosPorProfessor
                    .computeIfAbsent(professorNome, k -> new ArrayList<>())
                    .add(horario);
        }

        return horariosPorProfessor;
    }

    public List<Horario> listarTodos(){
        String sql = "SELECT * FROM horarios";
        return jdbcTemplate.query(sql, new HorarioRowMapper());
    }

    public Horario buscarHorarioId(Long id){
        String sql = "SELECT * FROM horarios WHERE id_horario = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new HorarioRowMapper());
    }

    public void criarHorario(Horario horario) {
        String sql = "INSERT INTO horario (disciplina_ID, dia_semana, horario_inicio, duracao, numero_sala) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                horario.getIdDisciplina(),
                horario.getDiaSemana(),
                horario.getHoraInicio(), //
                horario.getDuracao(),
                horario.getNumeroSala()
        );
    }
}



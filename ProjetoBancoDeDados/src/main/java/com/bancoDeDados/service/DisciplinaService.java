package com.bancoDeDados.service;

import com.bancoDeDados.model.Disciplina;
import com.bancoDeDados.model.Horario;
import com.bancoDeDados.model.Professor;
import com.bancoDeDados.repository.dao.DisciplinaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaDAO disciplinaDao;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private HorarioService horarioService;

    public List<Disciplina> buscarDisciplinasDeDiscente(Long discenteId) {
        List<Disciplina> disciplinas = disciplinaDao.buscarDisciplinasDeDiscente(discenteId);

        for (Disciplina disciplina : disciplinas) {
            Professor professor = professorService.buscarPorId(disciplina.getIdProfessor());
            disciplina.setProfessor(professor);
            List<Horario> horarios = horarioService.buscarPorDisciplina(disciplina.getIdDisciplina());
            disciplina.setHorarios(horarios);
        }

        return disciplinas;
    }
}

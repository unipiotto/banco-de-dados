package com.bancoDeDados.service;

import com.bancoDeDados.model.*;
import com.bancoDeDados.repository.DisciplinaRepository;
import com.bancoDeDados.repository.dao.CursoDisciplinaDAO;
import com.bancoDeDados.repository.dao.DisciplinaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaDAO disciplinaDao;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private HorarioService horarioService;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private CursoDisciplinaDAO cursoDisciplinaDAO;

    @Autowired
    public DisciplinaService(DisciplinaRepository disciplinaRepository, DisciplinaDAO disciplinaDAO){
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaDao = disciplinaDAO;
    }

    public List<Disciplina> buscarDisciplinasDeDiscente(Long discenteId) {
        List<Disciplina> disciplinas = disciplinaDao.buscarDisciplinasDeDiscente(discenteId);

        return informacoesComplementaresDisciplina(disciplinas);
    }

    public List<Disciplina> buscarDisciplinasPorCurso(Long idCurso) {
        List<Disciplina> disciplinas = disciplinaDao.buscarDisciplinasPorCurso(idCurso);

        return informacoesComplementaresDisciplina(disciplinas);
    }

    private List<Disciplina> informacoesComplementaresDisciplina(List<Disciplina> disciplinas) {
        for (Disciplina disciplina : disciplinas) {
            Professor professor = professorService.buscarPorId(disciplina.getIdProfessor());
            disciplina.setProfessor(professor);
            List<Horario> horarios = horarioService.buscarPorDisciplina(disciplina.getIdDisciplina());
            disciplina.setHorarios(horarios);
        }

        return disciplinas;
    }

    public List<Disciplina> listarTodos(){
        List<Disciplina> disciplinas = disciplinaRepository.listarTodos();
        return disciplinas;
    }

    public Disciplina buscarDisciplinaId(Long id){
        return disciplinaDao.buscarDisciplinaPorId(id);
    }

    public Disciplina atualizarDisciplina(Disciplina disciplina){
        return disciplinaDao.atualizarDisciplina(disciplina);
    }

    public void deletarDisciplina(Long id){
        disciplinaDao.deletarDisciplina(id);
    }

    public List<Professor> buscarProfessoresPorDisciplina(Long id) {
        return disciplinaRepository.buscarProfessoresPorDisciplina(id);
    }

    public Disciplina criarDisciplina(Disciplina disciplina){
        return disciplinaDao.criarDisciplina(disciplina);
    }

    public void associarCursoDisciplina(Curso curso, Disciplina disciplina) {
        CursoDisciplina cursoDisciplina = new CursoDisciplina();
        cursoDisciplina.setIdCurso(curso.getIdCurso());
        cursoDisciplina.setIdDisciplina(disciplina.getIdDisciplina());

        cursoDisciplinaDAO.salvarRelacao(cursoDisciplina.getIdCurso(), disciplina.getIdDisciplina());
    }

}

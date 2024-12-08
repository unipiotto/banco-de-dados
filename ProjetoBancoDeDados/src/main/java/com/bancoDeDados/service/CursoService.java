package com.bancoDeDados.service;

import com.bancoDeDados.model.Curso;
import com.bancoDeDados.repository.CursoRepository;
import com.bancoDeDados.repository.DepartamentoRepository;
import com.bancoDeDados.repository.dao.CursoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoDAO cursoDAO;

    @Autowired
    public CursoService(CursoRepository cursoRepository, CursoDAO cursoDAO, DepartamentoRepository departamentoRepository) {
        this.cursoRepository = cursoRepository;
        this.cursoDAO = cursoDAO;
    }

    public List<Curso> listarTodos() {
        List<Curso> cursos = cursoRepository.listarTodos();
        return cursos;
    }

    public Curso buscarCursoId(Long id) {
        return cursoDAO.buscarCursoPorId(id);
    }

    public List<Curso> listarCursoPorDepartamento(Long id) {
        List<Curso> cursos = cursoRepository.listarCursosPorDepartamento(id);
        return cursos;
    }

    public Curso atualizarCurso(Curso curso) {
        return cursoDAO.atualizarCurso(curso);
    }

    public void deletarCurso(Long id) {
        cursoDAO.deletarCurso(id);
    }

    public Curso criarCurso(Curso curso) {
        return cursoDAO.criarCurso(curso);
    }
}

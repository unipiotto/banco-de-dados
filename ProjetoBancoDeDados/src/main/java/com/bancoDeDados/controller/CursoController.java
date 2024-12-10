package com.bancoDeDados.controller;

import com.bancoDeDados.model.*;
import com.bancoDeDados.repository.DisciplinaRepository;
import com.bancoDeDados.repository.dao.DepartamentoDAO;
import com.bancoDeDados.repository.dao.HorarioDAO;
import com.bancoDeDados.repository.dao.ProfessorDAO;
import com.bancoDeDados.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private DepartamentoDAO departamentoDAO;
    @Autowired
    private ProfessorDAO professorDAO;
    @Autowired
    private DepartamentoService departamentoService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private DisciplinaService disciplinaService;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private HorarioDAO horarioDAO;
    @Autowired
    private HorarioService horarioService;

    @GetMapping("/listar")
    public String listarCurso(Model model) {
        List<Curso> cursos = cursoService.listarTodos();
        for (Curso curso : cursos) {
            Departamento d = departamentoDAO.buscarDepartamentoPorId(curso.getDepartamentoId());
            curso.setDepartamento(d);
            Professor p = professorDAO.buscarPorId(curso.getIdProfessorCordernador());
            curso.setProfessor(p);
        }
        model.addAttribute("cursos", cursos);
        return "/curso/cursos";
    }

    @GetMapping("/{id}")
    public String buscarCurso(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Curso curso = cursoService.buscarCursoId(id);
            if (curso == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Curso não encontrado");
                return "redirect:/cursos/listar";
            }
            Departamento departamento = departamentoDAO.buscarDepartamentoPorId(curso.getDepartamentoId());
            Professor professor = professorDAO.buscarPorId(curso.getIdProfessorCordernador());
            List<Disciplina> disciplinas = disciplinaRepository.buscarDisciplinasPorCurso(id);

            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("professor", professor);
            model.addAttribute("departamento", departamento);
            model.addAttribute("curso", curso);
            return "/curso/cursoDetalhado";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao buscar curso: " + e.getMessage());
            return "redirect:/cursos/listar";
        }
    }

    @GetMapping("/editar/{id}")
    public String formularioEdicaoCurso(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Curso curso = cursoService.buscarCursoId(id);
        if (curso == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Curso não encontrado");
            return "redirect:/cursos/listar";
        }
        List<Departamento> departamentos = departamentoService.listarTodos();
        List<Professor> professores = professorService.listar();

        model.addAttribute("professores", professores);
        model.addAttribute("departamentos", departamentos);
        model.addAttribute("curso", curso);
        return "/curso/cursoEditar";
    }

    @PostMapping("/editar/{id}")
    public String editarCurso(@PathVariable Long id, @Valid @ModelAttribute("curso") Curso curso,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/curso/cursoEditar";
        }

        try {
            Curso cursoExistente = cursoService.buscarCursoId(id);
            if (cursoExistente == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Curso não encontrado");
                return "redirect:/cursos/listar";
            }

            cursoExistente.setNomeCurso(curso.getNomeCurso());
            cursoExistente.setIdProfessorCordernador(curso.getIdProfessorCordernador());
            cursoExistente.setDepartamentoId(curso.getDepartamentoId());
            cursoService.atualizarCurso(cursoExistente);

            redirectAttributes.addFlashAttribute("successMessage", "Curso atualizado com sucesso");
            return "redirect:/cursos/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar curso: " + e.getMessage());
            return "/curso/cursoEditar";
        }
    }

    @PostMapping("/deletar/{id}")
    public String deletarCurso(@PathVariable Long id) {
        cursoService.deletarCurso(id);
        return "redirect:/cursos/listar";
    }

    @GetMapping("/criar")
    public String criarFormCurso(Model model) {
        List<Departamento> departamentos = departamentoService.listarTodos();
        List<Professor> professores = professorService.listar();

        model.addAttribute("departamentos", departamentos);
        model.addAttribute("professores", professores);
        model.addAttribute("curso", new Curso());
        return "/curso/cursoForm";
    }

    @PostMapping("/criar")
    public String criarCurso(@Valid @ModelAttribute Curso curso,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "/curso/cursoForm";
        }

        try{
            cursoService.criarCurso(curso);
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao criar curso: " + e.getMessage());
        }
        return "redirect:/cursos/listar";
    }
}


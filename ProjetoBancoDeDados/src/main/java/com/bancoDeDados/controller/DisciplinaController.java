package com.bancoDeDados.controller;

import com.bancoDeDados.model.Curso;
import com.bancoDeDados.model.Disciplina;

import com.bancoDeDados.model.Professor;
import com.bancoDeDados.model.Horario;
import com.bancoDeDados.repository.dao.DisciplinaDAO;
import com.bancoDeDados.repository.dao.HorarioDAO;
import com.bancoDeDados.service.CursoService;
import com.bancoDeDados.service.DisciplinaService;
import com.bancoDeDados.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private DisciplinaDAO disciplinaDAO;
    @Autowired
    private HorarioDAO horarioDAO;
    @Autowired
    private CursoService cursoService;

    @GetMapping("/discente/{discenteId}")
    public String buscarDisciplinasDeDiscente(@PathVariable Long discenteId, Model model) {
        List<Disciplina> disciplinas = disciplinaService.buscarDisciplinasDeDiscente(discenteId);
        model.addAttribute("disciplinas", disciplinas);
        model.addAttribute("discenteId", discenteId);
        return "disciplina/listar";
    }

    @GetMapping("/listar")
    public String listarDisciplinas(Model model) {
        List<Disciplina> disciplinas = disciplinaService.listarTodos();
        model.addAttribute("disciplinas", disciplinas);
        return "disciplina/disciplinas";
    }

    @GetMapping("/editar/{id}")
    public String formulárioEdicaoDisciplina(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Disciplina disciplina = disciplinaService.buscarDisciplinaId(id);
        if (disciplina == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Disciplina não encontrada");
            return "redirect:/disciplinas/listar";
        }

        List<Professor> professores = professorService.listar();

        model.addAttribute("professores", professores);
        model.addAttribute("disciplina", disciplina);
        return "disciplina/disciplinaEditar";
    }

    @GetMapping("/{id}")
    public String buscarDisciplina(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Disciplina disciplina = disciplinaService.buscarDisciplinaId(id);
            if (disciplina == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Disciplina não encontrada");
                return "redirect:/disciplinas/listar";
            }

            Map<String, List<Horario>> horariosPorProfessor = horarioDAO.buscarHorariosDisciplinaProfessor(id);

            List<Professor> professoresRelacionados = disciplinaService.buscarProfessoresPorDisciplina(id);
            List<Professor> professoresSemAula = professoresRelacionados.stream()
                    .filter(professor -> professor != null
                            && professor.getPessoa() != null
                            && !horariosPorProfessor.containsKey(professor.getPessoa().getNome()))
                    .collect(Collectors.toList());

            model.addAttribute("disciplina", disciplina);
            model.addAttribute("horariosPorProfessor", horariosPorProfessor != null ? horariosPorProfessor : Collections.emptyMap());
            model.addAttribute("professoresSemAula", professoresSemAula != null ? professoresSemAula : Collections.emptyList());

            return "disciplina/disciplinaDetalhada";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao buscar disciplina: " + e.getMessage());
            return "redirect:/disciplinas/listar";
        }
    }

    @PostMapping("/editar/{id}")
    public String editarDisciplina(@PathVariable Long id, @Valid @ModelAttribute("disciplina") Disciplina disciplina,
                                   RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "disciplina/disciplinaEditar";
        }

        try {
            Disciplina disciplinaExistente = disciplinaService.buscarDisciplinaId(id);
            if (disciplinaExistente == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Disciplina inexistente");
                return "redirect:/disciplinas/listar";
            }



            disciplinaExistente.setNomeDisciplina(disciplina.getNomeDisciplina());
            disciplinaExistente.setCargaHoraria(disciplina.getCargaHoraria());
            disciplinaExistente.setValorMensal(disciplina.getValorMensal());
            disciplinaExistente.setIdProfessor(disciplina.getIdProfessor());
            disciplinaService.atualizarDisciplina(disciplinaExistente);

            redirectAttributes.addFlashAttribute("message", "Disciplina atualizada com sucesso");
            return "redirect:/disciplinas/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar disciplina: " + e.getMessage());
            return "disciplina/disciplinaEditar";
        }
    }


    @PostMapping("/deletar/{id}")
    public String deletarDisciplina(@PathVariable Long id) {
        disciplinaService.deletarDisciplina(id);
        return "redirect:/disciplinas/listar";
    }

    @GetMapping("/criar/{idCurso}")
    public String criarFormDisciplina(Model model, @PathVariable("idCurso") Long idCurso) {
        Curso curso = cursoService.buscarCursoId(idCurso);
        if (curso == null) {
            model.addAttribute("errorMessage", "Curso não encontrado.");
            return "redirect:/cursos/listar";
        }
        List<Professor> professores = professorService.listar();
        model.addAttribute("professores", professores);
        model.addAttribute("curso", curso);
        model.addAttribute("disciplina", new Disciplina());
        return "disciplina/disciplinaForm";
    }


    @PostMapping("/criar")
    public String criarDisciplina(@ModelAttribute Disciplina disciplina,
                                  @RequestParam("idProfessor") String idProfessor,
                                  @RequestParam("idCurso") String idCurso,
                                  RedirectAttributes redirectAttributes) {

        System.out.println(idCurso);
        Professor professor = professorService.buscarPorId(Long.parseLong(idProfessor));
        if (professor == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Professor não encontrado.");
            return "redirect:/disciplinas/criar";
        }
        disciplina.setProfessor(professor);

        System.out.println(disciplina.getIdDisciplina());

        disciplina = disciplinaService.criarDisciplina(disciplina);
        System.out.println(disciplina.getIdDisciplina());
        if (disciplina == null || disciplina.getIdDisciplina() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao criar a disciplina.");
            return "redirect:/disciplinas/criar";
        }

        Curso curso = cursoService.buscarCursoId(Long.parseLong(idCurso));

        if (curso == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Curso não encontrado.");
            return "redirect:/disciplinas/criar";
        }
        disciplinaService.associarCursoDisciplina(curso, disciplina);

        redirectAttributes.addFlashAttribute("successMessage", "Disciplina criada com sucesso!");
        return "redirect:/disciplinas/listar";
    }
}

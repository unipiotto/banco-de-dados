package com.bancoDeDados.controller;


import com.bancoDeDados.model.*;
import com.bancoDeDados.model.dto.DiscenteForm;
import com.bancoDeDados.model.dto.ProfessorForm;
import com.bancoDeDados.repository.dao.EnderecoDAO;
import com.bancoDeDados.repository.dao.PessoaDAO;
import com.bancoDeDados.repository.dao.ProfessorDAO;
import com.bancoDeDados.service.PessoaService;
import com.bancoDeDados.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private EnderecoDAO enderecoDAO;
    @Autowired
    private ProfessorDAO professorDAO;

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professorForm", new ProfessorForm());
//        model.addAttribute("departamentos", departamentoRepository.getAll());

        return "professores/formularioProfessores";
    }

    @PostMapping("/novo")
    public String adicionarProfessor(@Valid @ModelAttribute ProfessorForm professorForm,
                                     BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "professores/formularioProfessores";
        }

        try {
            professorService.adicionar(professorForm);
        } catch (Exception e) {
            e.printStackTrace();
            return "professores/erroAoCriarProfessor";
        }

        return "redirect:/professores";
    }

    @GetMapping
    public String listar(Model model) {
        List<Professor> professores = professorService.listar();
        model.addAttribute("professores", professores);
        return "professores/professores";
    }

    @GetMapping("/editar/{id}")
    public String editarProfessorForm(@PathVariable Long id, Model model) {
        Professor professor = professorService.buscarPorId(id);
        if (professor == null) {
            return "redirect:/professores";
        }

        ProfessorForm professorForm = professorService.converterProfessorParaForm(professor);

        model.addAttribute("professor", professor);
        model.addAttribute("professorForm", professorForm);
        return "professores/professorEditar";
    }

    @PostMapping("/editar/{id}")
    public String editarProfessor(@PathVariable Long id,
                                 @Valid @ModelAttribute("professorForm") ProfessorForm professorForm,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Professor professorOriginal = professorService.buscarPorId(id);
            model.addAttribute("professor", professorOriginal);
            model.addAttribute("professorForm", professorForm);
            return "professores/professorEditar";
        }

        try {

            professorService.editarProfessor(id, professorForm);
            redirectAttributes.addFlashAttribute("successMessage", "Professor atualizado com sucesso!");
            return "redirect:/professores";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao atualizar o professor: " + e.getMessage());
            return "redirect:/professores";
        }
    }

    @PostMapping("/deletar")
    public String deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        Professor professor = professorService.buscarPorId(id);
        pessoaService.deletar(professor.getIdPessoa());
        redirectAttributes.addFlashAttribute("message", "Professor deletado com sucesso.");
        return "redirect:/professores";
    }

    @GetMapping("/{id}")
    public String vizualizarProfessor(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Professor professor = professorService.buscarPorId(id);
            if (professor == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Professor não encontrado.");
                return "redirect:/professores";
            }
            model.addAttribute("professor", professor);
            return "professores/professorComDetalhes";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao buscar professor.");
            return "redirect:/professores";
        }

    }




}

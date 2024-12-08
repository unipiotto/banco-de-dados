package com.bancoDeDados.controller;


import com.bancoDeDados.model.AvaliacaoProfessor;
import com.bancoDeDados.model.Departamento;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.Professor;
import com.bancoDeDados.model.dto.AvaliacaoProfessorForm;
import com.bancoDeDados.model.dto.ProfessorForm;
import com.bancoDeDados.repository.AvaliacaoProfessorRepository;
import com.bancoDeDados.repository.PessoaRepository;
import com.bancoDeDados.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private AvaliacaoProfessorRepository avaliacaoProfessorRepository;
    @Autowired
    private AvaliacaoProfessorService avaliacaoProfessorService;
    @Autowired
    private DiscenteService discenteService;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professorForm", new ProfessorForm());
        model.addAttribute("departamentos", departamentoService.listarTodos());

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
            Departamento dp = departamentoService.buscarDepartamentoId(professor.getIdDepartamento());
            professor.setDepartamento(dp);
            model.addAttribute("professor", professor);
            return "professores/professorComDetalhes";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao buscar professor.");
            return "redirect:/professores";
        }

    }

    @GetMapping("/avaliacao/{id}")
    public String verAvaliacoes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        List<AvaliacaoProfessor> avaliacoes = avaliacaoProfessorRepository.listarPorIdProfessor(id);
        avaliacoes.forEach(avaliacaoProfessor -> {
            avaliacaoProfessor.setDiscente(discenteService.buscarDiscenteCompletoPorId(avaliacaoProfessor.getIdDiscente()));
        });
        Professor professor = professorService.buscarPorId(id);
        if (professor == null) {
            return "redirect:/professores";
        }
        Optional<Pessoa> pessoa = pessoaRepository.buscarPorId(professor.getIdPessoa());
        pessoa.ifPresent(professor::setPessoa);
        Departamento dp = departamentoService.buscarDepartamentoId(professor.getIdDepartamento());
        professor.setDepartamento(dp);

        model.addAttribute("avaliacoes", avaliacoes);
        model.addAttribute("professor", professor);
        return "professores/avaliacoes";
    }

    @GetMapping("/avaliar/{id}")
    public String avaliar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("discentes", discenteService.listarTodos());
        model.addAttribute("professores", professorService.listar());
        model.addAttribute("avaliacaoProfessorForm", new AvaliacaoProfessorForm());
        model.addAttribute("professor", professorService.buscarPorId(id));
        return "professores/avaliar";
    }

    @PostMapping("/avaliar/{id}")
    public String adicionarAvaliacao(@PathVariable Long id,
                                     @Valid @ModelAttribute AvaliacaoProfessorForm avaliacaoProfessorForm,
                                     BindingResult bindingResult) {

        avaliacaoProfessorForm.setProfessorId(id);

        if (bindingResult.hasErrors()) {
            return "redirect:/professores/avaliar/" + id;
        }

        try {
            avaliacaoProfessorService.adicionar(avaliacaoProfessorForm);
        } catch (Exception e) {
            e.printStackTrace();
            return "professores/erroAoAvaliarProfessor";
        }

        return "redirect:/professores/avaliacao/" + id;
    }


}

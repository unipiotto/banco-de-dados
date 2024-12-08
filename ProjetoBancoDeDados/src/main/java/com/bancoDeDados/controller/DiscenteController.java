package com.bancoDeDados.controller;

import com.bancoDeDados.model.Curso;
import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.dto.DiscenteForm;
import com.bancoDeDados.model.dto.DiscenteFormEditar;
import com.bancoDeDados.service.CursoService;
import com.bancoDeDados.service.DiscenteService;
import com.bancoDeDados.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/discentes")
public class DiscenteController {

    @Autowired
    private DiscenteService discenteService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private CursoService cursoService;

    @GetMapping("/formulario")
    public String abrirPaginaFormularioDiscente (Model model) {
        List<Curso> cursos = cursoService.listarTodos();
        model.addAttribute("discenteForm", new DiscenteForm());
        model.addAttribute("cursos", cursos);
        return "discente/formulario";
    }

    @PostMapping("/formulario")
    public String adicionarDiscente (@Valid @ModelAttribute DiscenteForm discenteForm
                                    ) {
        try {
            discenteService.adicionarDiscente(discenteForm);
        } catch (Exception e) {
            e.printStackTrace();
            return "discente/erro";
        }

        return "redirect:/discentes";
    }

    @GetMapping
    public String listarTodosDiscentes(Model model) {
        List<Discente> discentes = discenteService.listarTodos();
        model.addAttribute("discentes", discentes);
        return "discente/listar";
    }

    @GetMapping("/editar/{id}")
    public String abrirPaginaEditarDiscente(@PathVariable Long id, Model model) {
        Discente discente = discenteService.buscarDiscenteCompletoPorId(id);
        if (discente == null) {
            return "redirect:/discentes";
        }

        DiscenteForm discenteForm = discenteService.converterDiscenteParaForm(discente);

        model.addAttribute("discente", discente);
        model.addAttribute("discenteForm", discenteForm);

        return "discente/editar";
    }

    @PostMapping("/editar/{id}")
    public String editarDiscente(@PathVariable Long id,
                                 @ModelAttribute("discenteForm") DiscenteFormEditar discenteFormEditar,
                                 RedirectAttributes redirectAttributes) {
        try {
            discenteService.editarDiscente(id, discenteFormEditar);

            redirectAttributes.addFlashAttribute("successMessage", "Discente atualizado com sucesso!");
            return "redirect:/discentes";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao atualizar o discente: " + e.getMessage());
            return "redirect:/discentes";
        }
    }

    @PostMapping("/deletar")
    public String deletarDiscente(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        Discente discente = discenteService.buscarDiscenteCompletoPorId(id);
        pessoaService.deletar(discente.getPessoaId());
        redirectAttributes.addFlashAttribute("message", "Discente deletado com sucesso.");
        return "redirect:/discentes";
    }

    @GetMapping("/{id}")
    public String visualizarInformacoesDiscente(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Discente discente = discenteService.buscarDiscenteCompletoPorId(id);
            if (discente == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Discente não encontrado.");
                return "redirect:/discentes";
            }
            model.addAttribute("discente", discente);
            return "discente/informacoes";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao buscar discente.");
            return "redirect:/discentes";
        }
    }

}

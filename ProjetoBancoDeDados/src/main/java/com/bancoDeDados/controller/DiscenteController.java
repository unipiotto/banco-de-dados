package com.bancoDeDados.controller;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.dto.DiscenteForm;
import com.bancoDeDados.service.DiscenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/discentes")
public class DiscenteController {

    @Autowired
    private DiscenteService discenteService;

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("discenteForm", new DiscenteForm());
        return "formularioDiscentes";
    }

    @PostMapping("/novo")
    public String adicionarDiscente(@Valid @ModelAttribute DiscenteForm discenteForm,
                                    BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            return "formularioDiscentes";
        }

        Pessoa pessoa = Pessoa.builder()
                .nome(discenteForm.getNome())
                .email(discenteForm.getEmail())
                .telefone(discenteForm.getTelefone())
                .cpf(discenteForm.getCpf())
                .dataNascimento(discenteForm.getDataNascimento())
                .build();

        Discente discente = Discente.builder()
                .dataIngresso(LocalDate.now())
                .status(Discente.StatusDiscente.ATIVA)
                .build();

        try {
            discenteService.adicionarDiscente(pessoa, discente);
        } catch (Exception e) {
            e.printStackTrace();
            return "erroAoCriarDiscente";
        }

        return "redirect:/discentes/listar";
    }

    @GetMapping("/{id}")
    public String visualizarDiscente(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Discente discente = discenteService.buscarDiscenteCompletoPorId(id);
            if (discente == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Discente não encontrado.");
                return "redirect:/discentes/listar";
            }
            model.addAttribute("discente", discente);
            return "discenteComDetalhes";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao buscar discente.");
            return "redirect:/discentes/listar";
        }
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Discente> discentes = discenteService.listarTodos();
        model.addAttribute("discentes", discentes);
        return "discentes";
    }

    @PostMapping
    public String salvar(@ModelAttribute Discente discente) {
        discenteService.salvar(discente);
        return "redirect:discentes";
    }

//    @GetMapping("/editar/{id}")
//    public String editar(@PathVariable Long id, Model model) {
//        Discente discente = discenteService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Discente inválido: " + id));
//        model.addAttribute("discente", discente);
//        return "formularioDiscentes";
//    }
//
//    @GetMapping("/deletar/{id}")
//    public String deletar(@PathVariable Long id) {
//        discenteService.deletar(id);
//        return "redirect:discentes";
//    }
}

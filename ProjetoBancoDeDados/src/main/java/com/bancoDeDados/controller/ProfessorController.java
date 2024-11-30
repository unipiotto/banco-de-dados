package com.bancoDeDados.controller;


import com.bancoDeDados.model.entities.Discente;
import com.bancoDeDados.model.entities.Professor;
import com.bancoDeDados.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professor", new Professor());
        return "professores/formulario";
    }

    @PostMapping
    public String salvar(@ModelAttribute Professor professor) {
        professorService.salvar(professor);
        return "redirect:/professores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Professor professor = professorService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Professor inválido: " + id));
        return "professores/formulario";
    }

    @GetMapping("deletar/{id}")
    public String deletar(@PathVariable("id") Long id) {
        professorService.deletar(id);
        return "redirect:/professores";
    }
}

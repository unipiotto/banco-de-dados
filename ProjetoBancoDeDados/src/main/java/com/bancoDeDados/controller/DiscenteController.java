package com.bancoDeDados.controller;

import com.bancoDeDados.model.entities.Discente;
import com.bancoDeDados.service.DiscenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/discentes")
public class DiscenteController {

    @Autowired
    private DiscenteService discenteService;

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Discente());
        return "discentes/formulario";
    }

    public String listar(Model model) {
        List<Discente> discentes = discenteService.listarTodos();
        model.addAttribute("discentes", discentes);
        return "discentes/discentes.html";
    }

    @PostMapping
    public String salvar(@ModelAttribute Discente discente) {
        discenteService.salvar(discente);
        return "redirect:/discentes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Discente discente = discenteService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        model.addAttribute("discente", discente);
        return "discentes/formulario";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        discenteService.deletar(id);
        return "redirect:/discente";
    }
}

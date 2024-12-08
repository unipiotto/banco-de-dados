package com.bancoDeDados.controller;

import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.Professor;
import com.bancoDeDados.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        return "pessoas/formulario";
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute Pessoa pessoa) {
        pessoaService.salvar(pessoa);
        return "redirect:/pessoas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Pessoa pessoa = pessoaService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Pessoa inválida: " + id));
        model.addAttribute("pessoa", pessoa);
        return "pessoas/formulario";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable("id") Long id) {
        pessoaService.deletar(id);
        return "redirect:/pessoas";
    }

    @GetMapping
    public String listar(Model model) {
        List<Pessoa> pessoas = pessoaService.listar();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("service", pessoaService);
        return "pessoas/pessoas";
    }
}

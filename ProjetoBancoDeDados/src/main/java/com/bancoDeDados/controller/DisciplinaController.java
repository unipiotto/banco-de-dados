package com.bancoDeDados.controller;

import com.bancoDeDados.model.Disciplina;
import com.bancoDeDados.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping("/discente/{discenteId}")
    public String buscarDisciplinasDeDiscente(@PathVariable Long discenteId, Model model) {
        List<Disciplina> disciplinas = disciplinaService.buscarDisciplinasDeDiscente(discenteId);
        model.addAttribute("disciplinas", disciplinas);
        model.addAttribute("discenteId", discenteId);
        return "disciplina/listar";
    }
}

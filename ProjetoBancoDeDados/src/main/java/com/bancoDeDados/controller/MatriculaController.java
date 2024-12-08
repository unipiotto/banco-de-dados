package com.bancoDeDados.controller;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Disciplina;
import com.bancoDeDados.service.DiscenteService;
import com.bancoDeDados.service.DisciplinaService;
import com.bancoDeDados.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private DisciplinaService disciplinaService;
    @Autowired
    private DiscenteService discenteService;
    @Autowired
    private MatriculaService matriculaService;

    @GetMapping("/discente/{idDiscente}")
    public String adicionarMatricula(@PathVariable Long idDiscente, Model model) {
        Discente discente = discenteService.buscarDiscenteCompletoPorId(idDiscente);
        List<Disciplina> disciplinas = disciplinaService.buscarDisciplinasPorCurso(discente.getCursoId());
        model.addAttribute("disciplinas", disciplinas);
        model.addAttribute("idDiscente", idDiscente);
        return "matricula/adicionar";
    }

    @PostMapping("/{idDiscente}/adicionar/{disciplinaId}")
    public String adicionarMatricula(@PathVariable Long idDiscente, @PathVariable Long disciplinaId) {
        matriculaService.matricularDiscenteEmDisciplina(idDiscente, disciplinaId);
        return "redirect:/disciplinas/discente/{idDiscente}";
    }
}

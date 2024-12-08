package com.bancoDeDados.controller;

import com.bancoDeDados.model.Departamento;
import com.bancoDeDados.model.Curso;
import com.bancoDeDados.service.DepartamentoService;
import com.bancoDeDados.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;
    @Autowired
    private CursoService cursoService;

    @GetMapping("/listar")
    public String listarDepartamento(Model model) {
        List<Departamento> departamentos = departamentoService.listarTodos();
        model.addAttribute("departamentos", departamentos);
        return "/departamento/departamentos";
    }

    @GetMapping("/{id}")
    public String buscarDepartamento(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Departamento departamento = departamentoService.buscarDepartamentoId(id);
            if(departamento == null){
                redirectAttributes.addFlashAttribute("errorMessage", "Deparmento não foi encontrado");
                return "redirect:/departamentos";
            }

            List<Curso> cursos = cursoService.listarCursoPorDepartamento(id);

            model.addAttribute("cursos", cursos);
            model.addAttribute("departamento", departamento);
            return "/departamento/departamentoDetalhado";
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao buscar departamento");
            return "redirect:/departamentos";
        }
    }
}
package com.bancoDeDados.controller;

import com.bancoDeDados.model.Avaliacao;
import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Disciplina;
import com.bancoDeDados.model.dto.AvaliacaoForm;
import com.bancoDeDados.service.AvaliacaoService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;
    @Autowired
    private DiscenteService discenteService;
    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping("/{idDiscente}")
    public String avaliacao(@PathVariable Long idDiscente, Model model) {
        List<Avaliacao> avaliacoes = avaliacaoService.listarNotasDiscente(idDiscente);
        Discente discente = discenteService.buscarDiscenteCompletoPorId(idDiscente);
        List<Disciplina> disciplinas = disciplinaService.buscarDisciplinasDeDiscente(idDiscente);
        List<Long> idDisciplinas = disciplinaService.buscarDisciplinasDeDiscente(idDiscente).stream().map(Disciplina::getIdDisciplina).toList();

        model.addAttribute("disciplinas", disciplinas);
        model.addAttribute("disciplinasIds", idDisciplinas);
        model.addAttribute("discente", discente);
        model.addAttribute("avaliacoes", avaliacoes);
        model.addAttribute("avaliacaoService", avaliacaoService);
        model.addAttribute("matriculaService", matriculaService);
        return "avaliacoes/avaliacoes";
    }

    @GetMapping("/adicionar/{id}")//idMatricula
    public String avaliacaoFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("avaliacaoForm", new AvaliacaoForm());
        model.addAttribute("id", id);

        return "avaliacoes/avaliacoesForm";
    }

    @PostMapping("/adicionar/{id}")//idDiscente -> idMatricula
    public String adicionarAvaliacao(@PathVariable Long id, Model model, AvaliacaoForm avaliacaoForm, RedirectAttributes attributes) {
        try {
            avaliacaoForm.setIdMatricula(id);
            avaliacaoService.adicionar(avaliacaoForm);
            attributes.addFlashAttribute("successMessage", "Avaliação adicionada com sucesso!");
            return "redirect:/discentes";
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao adicionar a avaliação: " + e.getMessage());
            return "redirect:/discentes";
        }
    }
//    @GetMapping("/editar/{id}")
//    public String editarProfessorForm(@PathVariable Long id, Model model) {
//        Professor professor = professorService.buscarPorId(id);
//        if (professor == null) {
//            return "redirect:/professores";
//        }
//
//        ProfessorForm professorForm = professorService.converterProfessorParaForm(professor);
//
//        model.addAttribute("professor", professor);
//        model.addAttribute("professorForm", professorForm);
//        return "professores/professorEditar";
//    }

    @GetMapping("/editar/{id}")//idAvaliacao
    public String editarAvaliacaoForm(@PathVariable Long id, Model model) {
        Avaliacao avaliacao = avaliacaoService.buscarPorId(id);

        if(avaliacao == null) {
            return "redirect:/discentes";
        }

        AvaliacaoForm avaliacaoForm = avaliacaoService.converterAvaliacaoForm(avaliacao);

        model.addAttribute("avaliacaoForm", avaliacaoForm);

        model.addAttribute("avaliacao", avaliacao);
        return "avaliacoes/avaliacoesEditar";
    }

    @PostMapping("/editar/{id}")//idAvaliacao
    public String editarAvaliacao(@PathVariable Long id, AvaliacaoForm avaliacaoForm, RedirectAttributes attributes) {
        try{
            avaliacaoService.atualizar(avaliacaoForm, id);
            attributes.addFlashAttribute("successMessage", "Avaliação atualizada com sucesso!");
            return "redirect:/discentes";
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao atualizar a avaliação: " + e.getMessage());
            return "redirect:/discentes";
        }
    }


//    @GetMapping("/editar/{id}")
//    public String editarAvaliacaoForm(@PathVariable Long id, Model model) {
//        Avaliacao avaliacao = avaliacaoService.buscarPorId(id);
//
//        if(avaliacao == null) {
//            return "redirect:/avaliacoes/adicionar/" + avaliacaoService.buscarIdDiscentePorIdAvaliacao(id);
//        }
//
//        AvaliacaoForm avaliacaoForm = avaliacaoService.converterAvaliacaoForm(avaliacao);
//
//        model.addAttribute("avaliacaoForm", avaliacaoForm);
//
//        model.addAttribute("avaliacao", avaliacao);
//        return "avaliacoes/avaliacaoForm";
//    }
//
//    @PostMapping("/editar/{id}")
//    public String editarAvaliacao(@PathVariable Long id, AvaliacaoForm avaliacaoForm, RedirectAttributes attributes) {
//        try{
//            avaliacaoService.atualizar(avaliacaoForm);
//            attributes.addFlashAttribute("successMessage", "Avaliação atualizada com sucesso!");
//            return "redirect:/avaliacoes/adicionar/" + avaliacaoService.buscarIdDiscentePorIdAvaliacao(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            attributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao atualizar a avaliação: " + e.getMessage());
//            return "redirect:/avaliacoes/adicionar/" + avaliacaoService.buscarIdDiscentePorIdAvaliacao(id);
//        }
//    }




//
//    @PostMapping("/editar/{id}")
//    public String editarProfessor(@PathVariable Long id,
//                                 @Valid @ModelAttribute("professorForm") ProfessorForm professorForm,
//                                 BindingResult bindingResult,
//                                 Model model,
//                                 RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            Professor professorOriginal = professorService.buscarPorId(id);
//            model.addAttribute("professor", professorOriginal);
//            model.addAttribute("professorForm", professorForm);
//            return "professores/professorEditar";
//        }
//
//        try {
//
//            professorService.editarProfessor(id, professorForm);
//            redirectAttributes.addFlashAttribute("successMessage", "Professor atualizado com sucesso!");
//            return "redirect:/professores";
//        } catch (Exception e) {
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao atualizar o professor: " + e.getMessage());
//            return "redirect:/professores";
//        }
//    }
}

package com.bancoDeDados.controller;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Endereco;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.dto.DiscenteForm;
import com.bancoDeDados.model.dto.DiscenteFormEditar;
import com.bancoDeDados.repository.dao.EnderecoDAO;
import com.bancoDeDados.repository.dao.PessoaDAO;
import com.bancoDeDados.service.DiscenteService;
import com.bancoDeDados.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/discentes")
public class DiscenteController {

    @Autowired
    private DiscenteService discenteService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private EnderecoDAO enderecoDAO;

    @GetMapping("/formulario")
    public String abrirPaginaFormularioDiscente (Model model) {
        model.addAttribute("discenteForm", new DiscenteForm());
        return "discente/formulario";
    }

    @PostMapping("/formulario")
    public String adicionarDiscente (@Valid @ModelAttribute DiscenteForm discenteForm,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "discente/formulario";
        }

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
                                 @Valid @ModelAttribute("discenteForm") DiscenteForm discenteForm,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Discente discenteOriginal = discenteService.buscarDiscenteCompletoPorId(id);
            model.addAttribute("discente", discenteOriginal);
            model.addAttribute("discenteForm", discenteForm);
            return "discente/editar";
        }

        try {
            discenteService.editarDiscente(id, discenteForm);

            redirectAttributes.addFlashAttribute("successMessage", "Discente atualizado com sucesso!");
            return "redirect:/discentes";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao atualizar o discente: " + e.getMessage());
            return "redirect:/discentes";
        }
    }

//    @PostMapping("/editar/{id}")
//    public String editarDiscente(@PathVariable Long id,
//                                 @Valid @ModelAttribute("discenteForm") DiscenteFormEditar discenteForm,
//                                 BindingResult bindingResult,
//                                 RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            return "discente/editar";
//        }
//
//        try {
//            Discente discenteOriginal = discenteService.buscarDiscenteCompletoPorId(id);
//            if (discenteOriginal == null) {
//                redirectAttributes.addFlashAttribute("errorMessage", "Discente não encontrado.");
//                return "redirect:/discentes";
//            }
//
//            // Atualiza informações de Pessoa
//            Pessoa pessoaOriginal = discenteOriginal.getPessoa();
//            pessoaOriginal.setNome(discenteForm.getNome());
//            pessoaOriginal.setEmail(discenteForm.getEmail());
//            pessoaOriginal.setTelefone(discenteForm.getTelefone());
//
//            pessoaDAO.atualizarPessoa(pessoaOriginal);
//
//            // Deleta os endereços
//            enderecoDAO.deletarPorPessoaId(pessoaOriginal.getIdPessoa());
//
//            // Mapeia todos os novos endereços
//            List<Endereco> enderecosAtualizados = discenteForm.getEnderecos().stream()
//                    .map(enderecoForm -> Endereco.builder()
//                            .rua(enderecoForm.getRua())
//                            .numero(enderecoForm.getNumero())
//                            .complemento(enderecoForm.getComplemento())
//                            .cidade(enderecoForm.getCidade())
//                            .sigla(enderecoForm.getSiglaEstado())
//                            .cep(enderecoForm.getCep())
//                            .pessoaId(pessoaOriginal.getIdPessoa())
//                            .build())
//                    .collect(Collectors.toList());
//
//            for (Endereco end : enderecosAtualizados) {
//                enderecoDAO.inserirEndereco(end);
//            }
//
//            redirectAttributes.addFlashAttribute("successMessage", "Discente atualizado com sucesso!");
//            return "redirect:/discentes";
//        } catch (Exception e) {
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao atualizar o discente: " + e.getMessage());
//            return "redirect:/discentes";
//        }
//    }

    @PostMapping("/deletar")
    public String deletarDiscente(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        Discente discente = discenteService.buscarDiscenteCompletoPorId(id);
        pessoaService.deletar(discente.getIdPessoa());
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

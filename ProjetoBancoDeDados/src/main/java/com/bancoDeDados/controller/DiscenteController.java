package com.bancoDeDados.controller;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Endereco;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.dto.DiscenteForm;
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

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("discenteForm", new DiscenteForm());
        return "formularioDiscentes";
    }

    @PostMapping("/novo")
    public String adicionarDiscente(@Valid @ModelAttribute DiscenteForm discenteForm,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "formularioDiscentes";
        }

        // Construir objeto Pessoa
        Pessoa pessoa = Pessoa.builder()
                .nome(discenteForm.getNome())
                .email(discenteForm.getEmail())
                .telefone(discenteForm.getTelefone())
                .cpf(discenteForm.getCpf())
                .dataNascimento(discenteForm.getDataNascimento())
                .build();

        // Construir lista de Endereços
        List<Endereco> enderecos = discenteForm.getEnderecos().stream()
                .map(enderecoForm -> Endereco.builder()
                        .rua(enderecoForm.getRua())
                        .numero(enderecoForm.getNumero())
                        .complemento(enderecoForm.getComplemento())
                        .cidade(enderecoForm.getCidade())
                        .sigla(enderecoForm.getSiglaEstado())
                        .cep(enderecoForm.getCep())
                        .build())
                .collect(Collectors.toList());

        Discente discente = Discente.builder()
                .dataIngresso(LocalDate.now())
                .status(Discente.StatusDiscente.ATIVA)
                .build();

        try {
            discenteService.adicionarDiscente(pessoa, enderecos, discente);
        } catch (Exception e) {
            e.printStackTrace();
            return "erroAoCriarDiscente";
        }

        return "redirect:/discentes/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Discente> discentes = discenteService.listarTodos();
        model.addAttribute("discentes", discentes);
        return "discentes";
    }

    @GetMapping("/editar/{id}")
    public String editarDiscenteForm(@PathVariable Long id, Model model) {
        Discente discente = discenteService.buscarDiscenteCompletoPorId(id);
        if (discente == null) {
            return "redirect:/discentes/listar";
        }

        DiscenteForm discenteForm = discenteService.converterDiscenteParaForm(discente);

        model.addAttribute("discente", discente);
        model.addAttribute("discenteForm", discenteForm);

        return "discenteEditar";
    }

    @PostMapping("/editar/{id}")
    public String editarDiscente(@PathVariable Long id,
                                 @Valid @ModelAttribute("discenteForm") DiscenteForm discenteForm,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "discenteEditar";
        }

        try {
            Discente discenteOriginal = discenteService.buscarDiscenteCompletoPorId(id);
            if (discenteOriginal == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Discente não encontrado.");
                return "redirect:/discentes/listar";
            }

            // Atualiza informações de Pessoa
            Pessoa pessoaOriginal = discenteOriginal.getPessoa();
            pessoaOriginal.setNome(discenteForm.getNome());
            pessoaOriginal.setEmail(discenteForm.getEmail());
            pessoaOriginal.setTelefone(discenteForm.getTelefone());

            pessoaDAO.atualizarPessoa(pessoaOriginal);

            enderecoDAO.deletarPorPessoaId(pessoaOriginal.getIdPessoa());

            List<Endereco> enderecosAtualizados = discenteForm.getEnderecos().stream()
                    .map(enderecoForm -> Endereco.builder()
                            .rua(enderecoForm.getRua())
                            .numero(enderecoForm.getNumero())
                            .complemento(enderecoForm.getComplemento())
                            .cidade(enderecoForm.getCidade())
                            .sigla(enderecoForm.getSiglaEstado())
                            .cep(enderecoForm.getCep())
                            .pessoaId(pessoaOriginal.getIdPessoa())
                            .build())
                    .collect(Collectors.toList());

            for (Endereco end : enderecosAtualizados) {
                enderecoDAO.inserirEndereco(end);
            }

            redirectAttributes.addFlashAttribute("successMessage", "Discente atualizado com sucesso!");
            return "redirect:/discentes/listar";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro ao atualizar o discente: " + e.getMessage());
            return "redirect:/discentes/listar";
        }
    }

    @PostMapping("/deletar")
    public String deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        Discente discente = discenteService.buscarDiscenteCompletoPorId(id);
        pessoaService.deletar(discente.getIdPessoa());
        redirectAttributes.addFlashAttribute("message", "Discente deletado com sucesso.");
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

}

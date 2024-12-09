package com.bancoDeDados.controller;

import com.bancoDeDados.model.dto.Relatorio;
import com.bancoDeDados.service.RelatorioService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioPagamentoController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public String verRelatorioPagamento(Model model) {
        Relatorio relatorio = relatorioService.recuperarValores();
        model.addAttribute("relatorio", relatorio);
        return "/relatorio/relatorioPagamento";
    }
}

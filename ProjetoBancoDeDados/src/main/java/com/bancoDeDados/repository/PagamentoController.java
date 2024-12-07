package com.bancoDeDados.repository;

import com.bancoDeDados.model.Pagamento;
import com.bancoDeDados.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping("/{discenteId}")
    public String listarTodos(@PathVariable Long discenteId, Model model) {
        List<Pagamento> pagamentos = pagamentoService.pegarTodosPagamentoDeDiscente(discenteId);
        model.addAttribute("pagamentos", pagamentos);
        model.addAttribute("discenteId", discenteId);
        return "pagamento/listar";
    }

    @PostMapping("/{discenteId}/pagar/{idPagamento}")
    public String realizarPagamento(@PathVariable Long idPagamento, @PathVariable Long discenteId) {
        pagamentoService.atualizarPagamentoParaPago(idPagamento);
        return "redirect:/pagamentos/{discenteId}";
    }
}

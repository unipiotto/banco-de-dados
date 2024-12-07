package com.bancoDeDados.service;

import com.bancoDeDados.model.Pagamento;
import com.bancoDeDados.repository.dao.PagamentoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoDAO pagamentoDAO;

    public List<Pagamento> pegarTodosPagamentoDeDiscente(Long id) {
        List<Pagamento> pagamentos = pagamentoDAO.listarPagamentoPorPessoaId(id);
        return pagamentos;
    }

    public void atualizarPagamentoParaPago(Long id) {
        LocalDate dataPagamentoRealizado = LocalDate.now();
        pagamentoDAO.atualizarPagamento(id, dataPagamentoRealizado);
    }
}

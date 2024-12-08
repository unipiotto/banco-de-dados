package com.bancoDeDados.service;

import com.bancoDeDados.model.Pagamento;
import com.bancoDeDados.model.dto.Relatorio;
import com.bancoDeDados.repository.dao.PagamentoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class RelatorioService {

    @Autowired
    private PagamentoDAO pagamentoDAO;

    public Relatorio recuperarValores() {

        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();
        int mesAnterior = (mesAtual == 1) ? 12 : mesAtual - 1;
        int anoMesAnterior = (mesAtual == 1) ? anoAtual - 1 : anoAtual;

        // Dados do mês atual
        Optional<Pagamento> pagamentoMaiorValorMesAtual = pagamentoDAO.pegarPagamentoComMaiorValorMes(mesAtual, anoAtual);
        Optional<Pagamento> pagamentoMenorValorMesAtual = pagamentoDAO.pegarPagamentoComMenorValorMes(mesAtual, anoAtual);
        BigDecimal valoresTotalMesAtual = pagamentoDAO.valorDosPagamentosDoMes(mesAtual, anoAtual);
        BigDecimal valoresRecebidosDoMes = pagamentoDAO.valoresRecebidosDoMes(mesAtual, anoAtual);
        BigDecimal valoresPendentesDoMes = pagamentoDAO.valoresPendentesDoMes(mesAtual, anoAtual);
        BigDecimal mediaPagamentosDoMes = pagamentoDAO.mediaPagamentosDoMes(mesAtual, anoAtual);

        System.out.println(pagamentoMaiorValorMesAtual.map(Pagamento::getValor).orElse(BigDecimal.ZERO));

        // Dados do mês anterior
        Optional<Pagamento> pagamentoMaiorValorMesAnterior = pagamentoDAO.pegarPagamentoComMaiorValorMes(mesAnterior, anoMesAnterior);
        Optional<Pagamento> pagamentoMenorValorMesAnterior = pagamentoDAO.pegarPagamentoComMenorValorMes(mesAnterior, anoMesAnterior);
        BigDecimal valoresTotalMesAnterior = pagamentoDAO.valorDosPagamentosDoMes(mesAnterior, anoAtual);
        BigDecimal valoresRecebidosDoMesAnterior = pagamentoDAO.valoresRecebidosDoMes(mesAnterior, anoAtual);
        BigDecimal valoresPendentesDoMesAnterior = pagamentoDAO.valoresPendentesDoMes(mesAtual, anoAtual);
        BigDecimal mediaPagamentosDoMesAnterior = pagamentoDAO.mediaPagamentosDoMes(mesAnterior, anoAtual);

        Relatorio relatorio = Relatorio.builder()
                .maiorPagamento(pagamentoMaiorValorMesAtual.map(Pagamento::getValor).orElse(BigDecimal.ZERO))
                .menorPagamento(pagamentoMenorValorMesAtual.map(Pagamento::getValor).orElse(BigDecimal.ZERO))
                .mediaPagamento(mediaPagamentosDoMes)
                .totalValoresPagamentos(valoresTotalMesAtual)
                .pagamentosPendentes(valoresPendentesDoMes)
                .pagamentosRealizados(valoresRecebidosDoMes)
                .maiorPagamentoMesAnterior(pagamentoMaiorValorMesAnterior.map(Pagamento::getValor).orElse(BigDecimal.ZERO))
                .menorPagamentoMesAnterior(pagamentoMenorValorMesAnterior.map(Pagamento::getValor).orElse(BigDecimal.ZERO))
                .mediaPagamentoMesAnterior(mediaPagamentosDoMesAnterior)
                .pagamentosPendentesMesAnterior(valoresPendentesDoMesAnterior)
                .pagamentosRealizadosMesAnterior(valoresRecebidosDoMesAnterior)
                .totalValoresPagamentosMesAnterior(valoresTotalMesAnterior)
                .build();

        return relatorio;
    }
}

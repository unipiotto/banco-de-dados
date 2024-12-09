package com.bancoDeDados.model.dto;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio {

    private BigDecimal maiorPagamento;

    private BigDecimal menorPagamento;

    private BigDecimal pagamentosRealizados;

    private BigDecimal pagamentosPendentes;

    private BigDecimal mediaPagamento;

    private BigDecimal totalValoresPagamentos;

    private BigDecimal maiorPagamentoMesAnterior;

    private BigDecimal menorPagamentoMesAnterior;

    private BigDecimal mediaPagamentoMesAnterior;

    private BigDecimal pagamentosRealizadosMesAnterior;

    private BigDecimal pagamentosPendentesMesAnterior;

    private BigDecimal totalValoresPagamentosMesAnterior;
}

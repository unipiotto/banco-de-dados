package com.bancoDeDados.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
    private Long idPagamento;
    private Long idDiscente;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private StatusPagamento status;

    public enum StatusPagamento {
        PAGO,
        PENDENTE,
        CANCELADO
    }
}

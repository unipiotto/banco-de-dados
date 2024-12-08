package com.bancoDeDados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
    private Long idPagamento;
    private Long discenteId;
    private LocalDate dataVencimento;
    private String mesVencimento;
    private String anoVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private StatusPagamento status;

    public enum StatusPagamento {
        PAGO,
        PENDENTE;

        public static Pagamento.StatusPagamento fromString(String status) {
            if (status == null) {
                throw new IllegalArgumentException("Status não pode ser nulo");
            }
            switch (status.toUpperCase()) {
                case "PAGO":
                    return PAGO;
                case "PENDENTE":
                    return PENDENTE;
                default:
                    throw new IllegalArgumentException("Status inválido: " + status);
            }
        }
    }

    public String getMesEAnoDoVencimento() {
        return this.mesVencimento + "/" + this.anoVencimento;
    }

    public String getDataVencimentoFormatada() {
        if (dataVencimento == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataVencimento.format(formatter);
    }

    public String getDataPagamentoFormatada() {
        if (dataPagamento == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataPagamento.format(formatter);
    }

    public boolean isAtrasado() {
        if (dataPagamento != null) {
            return false;
        }
        LocalDate hoje = LocalDate.now();
        return hoje.isAfter(dataVencimento);
    }
}

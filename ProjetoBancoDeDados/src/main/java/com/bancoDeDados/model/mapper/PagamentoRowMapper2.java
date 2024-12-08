package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Pagamento;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PagamentoRowMapper2 implements RowMapper<Pagamento> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Pagamento mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pagamento pagamento = new Pagamento();

        pagamento.setIdPagamento(rs.getLong("id_pagamento"));
        String dateStringVencimento = rs.getString("data_vencimento");
        LocalDate dataVencimento = LocalDate.parse(dateStringVencimento, DATE_FORMATTER);
        pagamento.setDataVencimento(dataVencimento);
        String dateStringPagamento = rs.getString("data_pagamento");
        if (dateStringPagamento != null && !dateStringPagamento.trim().isEmpty()) {
            try {
                LocalDate dataPagamento = LocalDate.parse(dateStringPagamento, DATE_FORMATTER);
                pagamento.setDataPagamento(dataPagamento);
            } catch (DateTimeParseException e) {
                pagamento.setDataPagamento(null);
            }
        } else {
            pagamento.setDataPagamento(null);
        }
        pagamento.setValor(rs.getBigDecimal("valor"));
        String status = rs.getString("status_pagamento");
        pagamento.setStatus(Pagamento.StatusPagamento.fromString(status));

        return pagamento;
    }
}

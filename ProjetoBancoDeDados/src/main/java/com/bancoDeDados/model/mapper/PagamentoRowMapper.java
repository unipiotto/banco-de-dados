package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Pagamento;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PagamentoRowMapper implements RowMapper<Pagamento> {
    @Override
    public Pagamento mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setIdPagamento(rs.getLong("ID_pagamento"));
        pagamento.setDiscenteId(rs.getLong("discente_ID"));
        pagamento.setDataVencimento(rs.getDate("data_vencimento").toLocalDate());
        pagamento.setDataPagamento(rs.getDate("data_pagamento") != null
                ? rs.getDate("data_pagamento").toLocalDate()
                : null);
        pagamento.setValor(rs.getBigDecimal("valor"));
        String status = rs.getString("status_pagamento");
        pagamento.setStatus(Pagamento.StatusPagamento.fromString(status));
        return pagamento;
    }
}

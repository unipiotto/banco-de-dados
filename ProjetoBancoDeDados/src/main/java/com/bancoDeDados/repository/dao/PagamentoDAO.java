package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Pagamento;
import com.bancoDeDados.model.mapper.PagamentoRowMapper1;
import com.bancoDeDados.model.mapper.PagamentoRowMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PagamentoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Pagamento> listarPagamentoPorPessoaId(Long id) {
        String sql = """
            SELECT p.id_pagamento, discente_id,
            SUBSTRING(data_vencimento::TEXT, 1, 4) AS ano_vencimento,
            SUBSTRING(data_vencimento::TEXT, 6, 2) AS mes_vencimento,
            REPLACE(p.data_vencimento::TEXT, '-', '/') AS data_vencimento,
            REPLACE(p.data_pagamento::TEXT, '-', '/') AS data_pagamento,
            p.valor, p.status_pagamento FROM pagamentos p
            WHERE p.discente_id = ?
            ORDER BY data_vencimento DESC
        """;
        return jdbcTemplate.query(sql, new PagamentoRowMapper1(), id);
    }

    public void atualizarPagamento(Long id, LocalDate dataPagamento) {
        String sql = "UPDATE pagamentos SET data_pagamento = ?, status_pagamento = 'pago' WHERE id_pagamento = ?";
        jdbcTemplate.update(sql, dataPagamento, id);
    }

    public void atualizarValorPagamento(Long id, BigDecimal valorPagamento) {
        String sql = "UPDATE pagamentos SET valor = ? WHERE id_pagamento = ?";
        jdbcTemplate.update(sql, valorPagamento, id);
    }

    public Pagamento buscarPagamentoDoMes(int mesAtual, int anoAtual, Long idDiscente) {
        String sql = """
            SELECT *
            FROM pagamentos
            WHERE discente_ID = ?
              AND EXTRACT(YEAR FROM data_vencimento) = ?
              AND EXTRACT(MONTH FROM data_vencimento) = ?;
        """;

        return jdbcTemplate.queryForObject(sql, new PagamentoRowMapper2(), idDiscente, anoAtual, mesAtual);
    }
}

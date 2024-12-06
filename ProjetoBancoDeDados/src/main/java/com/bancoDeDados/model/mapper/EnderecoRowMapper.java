package com.bancoDeDados.model.mapper;

import com.bancoDeDados.model.Endereco;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoRowMapper implements RowMapper<Endereco> {

    @Override
    public Endereco mapRow(ResultSet rs, int rowNum) throws SQLException {
        Endereco endereco = new Endereco();

        endereco.setIdEndereco(rs.getLong("id_endereco"));
        endereco.setPessoaId(rs.getLong("pessoa_id"));
        endereco.setRua(rs.getString("rua"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setCep(rs.getString("cep"));
        endereco.setComplemento(rs.getString("complemento"));
        endereco.setCidade(rs.getString("cidade"));

        String siglaEstado = rs.getString("sigla_estado");
        if (siglaEstado != null) {
            endereco.setSigla(Endereco.SiglaEstado.valueOf(siglaEstado));
        }

        return endereco;
    }
}

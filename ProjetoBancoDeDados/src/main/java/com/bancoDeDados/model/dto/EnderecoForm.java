package com.bancoDeDados.model.dto;

import com.bancoDeDados.model.Endereco;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoForm {

    @NotEmpty(message = "Rua é obrigatório")
    private String rua;

    @NotEmpty(message = "Numero é obrigatório")
    private String numero;

    @NotEmpty(message = "CEP é obrigatório")
    private String cep;

    private String complemento;

    @NotNull(message = "Estado é obrigatório")
    private Endereco.SiglaEstado siglaEstado;

    @NotEmpty
    private String cidade;
}

package com.bancoDeDados.model.dto;

import com.bancoDeDados.model.Endereco;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "\\d{1,4}", message = "Número deve conter no máximo 4 dígitos")
    private String numero;

    @NotEmpty(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 números")
    private String cep;

    private String complemento;

    @NotNull(message = "Estado é obrigatório")
    private Endereco.SiglaEstado siglaEstado;

    @NotEmpty(message = "Cidade é obrigatório")
    private String cidade;
}

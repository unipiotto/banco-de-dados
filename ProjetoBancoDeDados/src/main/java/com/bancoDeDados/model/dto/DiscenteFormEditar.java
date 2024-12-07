package com.bancoDeDados.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscenteFormEditar {

    @NotEmpty(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email é obrigatório")
    private String email;

    @NotEmpty(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "Telefone deve conter exatamente 11 números")
    private String telefone;

    private String matricula;
    private String curso;

    @Valid
    @NotEmpty(message = "Pelo menos um endereço é obrigatório")
    private List<EnderecoForm> enderecos;

}

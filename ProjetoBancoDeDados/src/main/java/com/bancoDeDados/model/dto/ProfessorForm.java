package com.bancoDeDados.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorForm {

    @NotEmpty(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email é obrigatório")
    private String email;

    @Pattern(regexp = "\\d{11}", message = "Telefone deve conter exatamente 11 números")
    private String telefone;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 números")
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatório")
    private LocalDate dataNascimento;


    private Long departamentoId;

    @Valid
    @NotEmpty(message = "Pelo menos um endereço é obrigatório")
    private List<EnderecoForm> enderecos;

    public String getTelefoneFormatado() {
        if (telefone == null || telefone.length() != 11) {
            return telefone;
        }
        return String.format("(%s) %s-%s",
                telefone.substring(0, 2),
                telefone.substring(2, 7),
                telefone.substring(7));
    }

    public String getCpfFormatado() {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9));
    }
}

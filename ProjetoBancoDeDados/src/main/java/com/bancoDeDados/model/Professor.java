package com.bancoDeDados.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Professor {
    private Long idProfessor;
    private Long idPessoa;
    private Long idDepartamento;
    private LocalDate dataContratacao;

    private Pessoa pessoa;

    private Departamento departamento;

}

package com.bancoDeDados.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Matricula {
    private Long idMatricula;
    private Long idDiscente;
    private Long idDisciplina;
    private int anoMatricula;
    private int semestre;
}

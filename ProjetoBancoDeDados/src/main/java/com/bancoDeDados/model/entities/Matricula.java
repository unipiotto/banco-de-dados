package com.bancoDeDados.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Matricula {
    private Long idMatricula;
    private Long idDiscente;
    private Long idDisciplina;
    private BigDecimal notaFinal;
    private int anoMatricula;
    private int semestre;
}

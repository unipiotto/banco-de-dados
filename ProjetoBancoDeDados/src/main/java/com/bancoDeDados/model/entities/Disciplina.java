package com.bancoDeDados.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disciplina {
    private Long idDisciplina;
    private String nomeDisciplina;
    private int cargaHoraria;
    private BigDecimal valorMensal;
    private Long idProfessor;
}

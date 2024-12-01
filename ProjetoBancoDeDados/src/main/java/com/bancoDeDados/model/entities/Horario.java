package com.bancoDeDados.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
    private Long idHora;
    private Long idDisciplina;
    private DiaSemana diaSemana;
    private LocalDate horaInicio;
    private int duracao;
    private String numeroSala;

    public enum DiaSemana {
        SEGUNDA,
        TERCA,
        QUARTA,
        QUINTA,
        SEXTA
    }
}

package com.bancoDeDados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
    private Long idHora;
    private Long idDisciplina;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private int duracao;
    private String numeroSala;

    public enum DiaSemana {
        SEGUNDA,
        TERCA,
        QUARTA,
        QUINTA,
        SEXTA;

        public static Horario.DiaSemana fromString(String diaSemana) {
            if (diaSemana == null) {
                throw new IllegalArgumentException("Dia da semana não pode ser nulo");
            }
            switch (diaSemana.toUpperCase()) {
                case "SEGUNDA":
                    return SEGUNDA;
                case "TERÇA":
                    return TERCA;
                case "QUARTA":
                    return QUARTA;
                case "QUINTA":
                    return QUINTA;
                case "SEXTA":
                    return SEXTA;
                default:
                    throw new IllegalArgumentException("Dia da semana inválido: " + diaSemana);
            }
        }
    }

    public LocalTime getHoraFim() {
        return horaInicio.plusMinutes(duracao);
    }
}

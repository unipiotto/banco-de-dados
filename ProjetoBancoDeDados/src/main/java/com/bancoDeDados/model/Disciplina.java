package com.bancoDeDados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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
    private Professor professor;
    private List<Horario> horarios;

    public String valorMensalFormatado() {
        return "R$ " + this.valorMensal.toString();
    }

}

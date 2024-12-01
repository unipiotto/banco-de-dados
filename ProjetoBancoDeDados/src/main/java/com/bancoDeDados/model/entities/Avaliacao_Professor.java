package com.bancoDeDados.model.entities;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao_Professor {
    private Long idAvaliacao;
    private Long idDiscente;
    private Long idProfessor;

    @Setter(AccessLevel.NONE)
    private int notaAvaliacao;

    private String comentario;
    private LocalDate dataAvaliacao;

    public void setNotaAvaliacao(int notaAvaliacao) {
        if (notaAvaliacao < 1 || notaAvaliacao > 5) {
            throw new IllegalArgumentException("A nota de ensino deve estar entre 1 e 5");
        }
        this.notaAvaliacao = notaAvaliacao;
    }
}

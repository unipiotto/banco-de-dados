package com.bancoDeDados.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoProfessorForm {

    private int notaAvaliacao;

    private String comentario;

    private Long discenteId;
    private Long professorId;

}

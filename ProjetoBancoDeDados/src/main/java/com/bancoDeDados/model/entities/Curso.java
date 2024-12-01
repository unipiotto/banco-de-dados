package com.bancoDeDados.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    private Long idCurso;
    private String nomeCurso;
    private Long idProfessorCordernador;
}

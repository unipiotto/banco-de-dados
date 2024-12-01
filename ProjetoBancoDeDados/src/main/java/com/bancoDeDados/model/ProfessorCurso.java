package com.bancoDeDados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorCurso {
    private Long idProfessor;
    private Long idCurso;
    private LocalDate dataIngreso;
}

package com.bancoDeDados.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoForm {

    private Long idMatricula;

    private BigDecimal nota;
    private BigDecimal peso;
}

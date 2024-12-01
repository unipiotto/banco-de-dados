package com.bancoDeDados.model;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {
    private Long idAvaliacao;
    private Long idMatricula;

    @Setter(AccessLevel.NONE)
    private BigDecimal nota;

    @Setter(AccessLevel.NONE)
    private BigDecimal peso;

    public void setNota(BigDecimal nota){
        if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 100");
        }
        this.nota = nota;
    }


    public void setPeso(BigDecimal peso) {
        if (peso.compareTo(BigDecimal.ZERO) < 0 || peso.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("O peso deve estar entre 0 e 1");
        }
        this.peso = peso;
    }
}

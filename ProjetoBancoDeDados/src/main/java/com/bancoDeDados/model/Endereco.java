package com.bancoDeDados.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {
    private Long idEndereco;
    private Long pessoaId;
    private String rua;
    private String numero;
    private String cep;
    private String complemento;

    @Getter(AccessLevel.NONE)
    private SiglaEstado sigla;

    private String cidade;

    public String getSiglaEstado() {
        return sigla != null ? sigla.name() : null;
    }

    public SiglaEstado getSiglaEstadoEnum() {
        return this.sigla;
    }

    public enum SiglaEstado {
        // Região Norte
        AC, AP, AM, PA, RO, RR, TO,

        // Região Nordeste
        AL, BA, CE, MA, PB, PE, PI, RN, SE,

        // Região Centro-Oeste
        DF, GO, MT, MS,

        // Região Sudeste
        ES, MG, RJ, SP,

        // Região Sul
        PR, RS, SC;
    }
}

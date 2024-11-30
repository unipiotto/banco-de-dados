package com.bancoDeDados.model.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private Long idEndereco;
    private Long idPessoa;
    private String bairro;
    private String rua;
    private String numero;
    private String cep;
    private String complemento;
    @Setter(AccessLevel.NONE)
    private SiglaEstado sigla;
    private String cidade;

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

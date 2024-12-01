package com.bancoDeDados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discente {
    private Long idDiscente;
    private Long idPessoa;
    private String registroAcademico;
    private String dataIngresso;
    private StatusDiscente status;

    private Pessoa pessoa;

    public enum StatusDiscente {
        ATIVA,
        TRANCADA,
        CONCLUIDA
    }

    public String getStatusFormatado() {
        return status != null ? status.name().substring(0, 1).toUpperCase() + status.name().substring(1).toLowerCase() : "";
    }
}
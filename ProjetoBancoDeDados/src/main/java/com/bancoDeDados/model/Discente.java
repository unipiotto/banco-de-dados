package com.bancoDeDados.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discente {
    private Long idDiscente;
    private Long pessoaId;
    private String registroAcademico;
    private LocalDate dataIngresso;
    private StatusDiscente status;
    private Long cursoId;

    private Pessoa pessoa;

    public enum StatusDiscente {
        ATIVA,
        TRANCADA,
        CONCLUIDA;

        public static StatusDiscente fromString(String status) {
            if (status == null) {
                throw new IllegalArgumentException("Status não pode ser nulo");
            }
            switch (status.toUpperCase()) {
                case "ATIVA":
                    return ATIVA;
                case "TRANCADA":
                    return TRANCADA;
                case "CONCLUIDA":
                    return CONCLUIDA;
                default:
                    throw new IllegalArgumentException("Status inválido: " + status);
            }
        }
    }

    public String getStatusFormatado() {
        return status != null ? status.name().substring(0, 1).toUpperCase() + status.name().substring(1).toLowerCase() : "";
    }

}
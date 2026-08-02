package com.mmo.recrutamento_interno.dto.vaga;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VagaRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotBlank(message = "Os requisitos são obrigatórios")
        String requisitos
) {
}

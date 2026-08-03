package com.mmo.recrutamento_interno.dto.candidatura;

import com.mmo.recrutamento_interno.domain.enums.StatusCandidatura;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoCandidaturaRequestDTO(
        @NotNull(message = "O status é obrigatório")
        StatusCandidatura status,

        String feedback,

        @Min(value = 1, message = "A nota mínima é 1")
        @Max(value = 10, message = "A nota máxima é 10")
        Integer notaAvaliacao
) {
}

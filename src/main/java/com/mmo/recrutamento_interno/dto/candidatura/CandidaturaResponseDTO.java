package com.mmo.recrutamento_interno.dto.candidatura;

import com.mmo.recrutamento_interno.domain.entity.Candidatura;
import com.mmo.recrutamento_interno.domain.enums.StatusCandidatura;

import java.time.LocalDateTime;

public record CandidaturaResponseDTO(
        Long id,
        Long vagaId,
        String vagaTitulo,
        Long candidatoId,
        String candidatoNome,
        String candidatoEmail,
        StatusCandidatura status,
        String feedback,
        Integer notaAvaliacao,
        LocalDateTime dataAplicacao
) {
    public CandidaturaResponseDTO(Candidatura candidatura) {
        this(
                candidatura.getId(),
                candidatura.getVaga().getId(),
                candidatura.getVaga().getTitulo(),
                candidatura.getUsuario().getId(),
                candidatura.getUsuario().getNome(),
                candidatura.getUsuario().getEmail(),
                candidatura.getStatus(),
                candidatura.getFeedback(),
                candidatura.getNotaAvaliacao(),
                candidatura.getDataAplicacao()
        );
    }
}

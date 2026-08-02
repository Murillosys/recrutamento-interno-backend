package com.mmo.recrutamento_interno.dto.vaga;

import com.mmo.recrutamento_interno.domain.entity.Vaga;
import com.mmo.recrutamento_interno.domain.enums.StatusVaga;

import java.time.LocalDateTime;

public record VagaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        String requisitos,
        StatusVaga status,
        String criadoPorNome,
        LocalDateTime dataCriacao
) {
    public VagaResponseDTO(Vaga vaga) {
        this(
                vaga.getId(),
                vaga.getTitulo(),
                vaga.getDescricao(),
                vaga.getRequisitos(),
                vaga.getStatus(),
                vaga.getCriadoPor() != null ? vaga.getCriadoPor().getNome() : null,
                vaga.getDataCriacao()
        );
    }
}

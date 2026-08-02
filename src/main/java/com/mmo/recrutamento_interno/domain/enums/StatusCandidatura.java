package com.mmo.recrutamento_interno.domain.enums;

public enum StatusCandidatura {
    RECEBIDA("Recebida"),
    EM_ANALISE("Em Análise"),
    APROVADO("Aprovado"),
    REJEITADO("Rejeitado");

    private final String descricao;

    StatusCandidatura(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

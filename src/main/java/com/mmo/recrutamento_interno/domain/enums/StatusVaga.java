package com.mmo.recrutamento_interno.domain.enums;

public enum StatusVaga {
    ABERTA("Aberta"),
    ENCERRADA("Encerrada");

    private final String descricao;

    StatusVaga(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

package com.mmo.recrutamento_interno.domain.enums;

public enum Perfil {
    ROLE_ADMIN("ADMIN"),
    ROLE_CANDIDATO("CANDIDATO");

    private final String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

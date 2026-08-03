package com.mmo.recrutamento_interno.dto.auth;

public record TokenResponseDTO(
        String token,
        String type,
        String name,
        String email,
        String perfil
) {
    public TokenResponseDTO(String token, String name, String email, String perfil) {
        this(token, "Bearer", name, email, perfil);
    }
}

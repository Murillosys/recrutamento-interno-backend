package com.mmo.recrutamento_interno.dto.auth;

public record TokenResponseDTO(
        String token,
        String type,
        String email,
        String perfil
) {
    public TokenResponseDTO(String token, String email, String perfil) {
        this(token, "Bearer", email, perfil);
    }
}

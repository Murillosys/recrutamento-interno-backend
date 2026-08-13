package com.mmo.recrutamento_interno.controller;

import com.mmo.recrutamento_interno.domain.entity.Usuario;
import com.mmo.recrutamento_interno.domain.enums.Perfil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

class CandidaturaSecurityTest {

    private Usuario candidato;
    private Usuario admin;

    @BeforeEach
    void setUp() {
        candidato = Usuario.builder()
                .id(1L)
                .email("candidato@empresa.com")
                .perfil(Perfil.ROLE_CANDIDATO)
                .build();

        admin = Usuario.builder()
                .id(2L)
                .email("admin@empresa.com")
                .perfil(Perfil.ROLE_ADMIN)
                .build();
    }

    @Test
    @DisplayName("Garante que o candidato possui autoridade ROLE_CANDIDATO")
    void validaAutoridadeCandidato() {
        var auth = new UsernamePasswordAuthenticationToken(candidato, null, candidato.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        var usuarioLogado = SecurityContextHolder.getContext().getAuthentication();

        assertTrue(usuarioLogado.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CANDIDATO")));
        assertFalse(usuarioLogado.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    @DisplayName("Garante que o admin possui autoridade ROLE_ADMIN")
    void validaAutoridadeAdmin() {
        var auth = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        var usuarioLogado = SecurityContextHolder.getContext().getAuthentication();

        assertTrue(usuarioLogado.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }
}
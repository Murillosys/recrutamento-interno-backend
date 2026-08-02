package com.mmo.recrutamento_interno.controller;

import com.mmo.recrutamento_interno.domain.entity.Usuario;
import com.mmo.recrutamento_interno.dto.auth.LoginRequestDTO;
import com.mmo.recrutamento_interno.dto.auth.TokenResponseDTO;
import com.mmo.recrutamento_interno.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = authenticationManager.authenticate(authToken);
        var usuario = (Usuario) auth.getPrincipal();
        var token = tokenService.generateToken(usuario.getEmail());
        return ResponseEntity.ok(new TokenResponseDTO(token, usuario.getEmail(), usuario.getPerfil().name()));
    }
}
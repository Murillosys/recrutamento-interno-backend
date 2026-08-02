package com.mmo.recrutamento_interno.controller;

import com.mmo.recrutamento_interno.domain.entity.Usuario;
import com.mmo.recrutamento_interno.dto.vaga.VagaRequestDTO;
import com.mmo.recrutamento_interno.dto.vaga.VagaResponseDTO;
import com.mmo.recrutamento_interno.service.VagaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    @GetMapping
    public ResponseEntity<List<VagaResponseDTO>> listarAtivas() {
        return ResponseEntity.ok(vagaService.listarAtivas());
    }

    @GetMapping("/todas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VagaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(vagaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vagaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDTO> criar(@RequestBody @Valid VagaRequestDTO dto, @AuthenticationPrincipal Usuario usuarioLogado) {
        VagaResponseDTO criada = vagaService.criar(dto, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid VagaRequestDTO dto) {
        return ResponseEntity.ok(vagaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> encerrarVaga(@PathVariable Long id) {
        vagaService.encerrarVaga(id);
        return ResponseEntity.noContent().build();
    }
}

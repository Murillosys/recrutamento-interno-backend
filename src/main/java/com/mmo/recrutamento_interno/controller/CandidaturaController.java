package com.mmo.recrutamento_interno.controller;

import com.mmo.recrutamento_interno.domain.entity.Usuario;
import com.mmo.recrutamento_interno.dto.candidatura.AvaliacaoCandidaturaRequestDTO;
import com.mmo.recrutamento_interno.dto.candidatura.CandidaturaResponseDTO;
import com.mmo.recrutamento_interno.service.CandidaturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaService candidaturaService;

    @PostMapping("/vagas/{vagaId}")
    @PreAuthorize("hasRole('CANDIDATO')")
    public ResponseEntity<CandidaturaResponseDTO> aplicar(@PathVariable Long vagaId, @AuthenticationPrincipal Usuario candidatoLogado) {
        CandidaturaResponseDTO dto = candidaturaService.aplicarParaVaga(vagaId, candidatoLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/minhas")
    @PreAuthorize("hasRole('CANDIDATO')")
    public ResponseEntity<List<CandidaturaResponseDTO>> listarMinhas(@AuthenticationPrincipal Usuario candidatoLogado) {
        return ResponseEntity.ok(candidaturaService.listarMinhasCandidaturas(candidatoLogado.getId()));
    }

    @GetMapping("/vagas/{vagaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CandidaturaResponseDTO>> listarPorVaga(@PathVariable Long vagaId) {
        return ResponseEntity.ok(candidaturaService.listarPorVaga(vagaId));
    }

    @PatchMapping("/{candidaturaId}/avaliar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CandidaturaResponseDTO> avaliar(@PathVariable Long candidaturaId, @RequestBody @Valid AvaliacaoCandidaturaRequestDTO dto) {
        return ResponseEntity.ok(candidaturaService.avaliarCandidatura(candidaturaId, dto));
    }
}

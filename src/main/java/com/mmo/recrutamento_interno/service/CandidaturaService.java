package com.mmo.recrutamento_interno.service;

import com.mmo.recrutamento_interno.domain.entity.Candidatura;
import com.mmo.recrutamento_interno.domain.entity.Usuario;
import com.mmo.recrutamento_interno.domain.entity.Vaga;
import com.mmo.recrutamento_interno.domain.enums.StatusCandidatura;
import com.mmo.recrutamento_interno.domain.enums.StatusVaga;
import com.mmo.recrutamento_interno.dto.candidatura.AvaliacaoCandidaturaRequestDTO;
import com.mmo.recrutamento_interno.dto.candidatura.CandidaturaResponseDTO;
import com.mmo.recrutamento_interno.exception.BusinessException;
import com.mmo.recrutamento_interno.exception.ResourceNotFoundException;
import com.mmo.recrutamento_interno.repository.CandidaturaRepository;
import com.mmo.recrutamento_interno.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private EmailNotificationService notificationService;

    @Transactional
    public CandidaturaResponseDTO aplicarParaVaga(Long vagaId, Usuario candidatoLogado) {
        Vaga vaga = vagaRepository.findById(vagaId).orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada."));

        if (vaga.getStatus() == StatusVaga.ENCERRADA) {
            throw new BusinessException("Esta vaga já está encerrada.");
        }

        boolean jaCandidatado = candidaturaRepository.existsByUsuarioIdAndVagaId(candidatoLogado.getId(), vagaId);
        if (jaCandidatado) {
            throw new BusinessException("Você já se candidatou para esta vaga.");
        }

        Candidatura candidatura = Candidatura.builder()
                .usuario(candidatoLogado)
                .vaga(vaga)
                .status(StatusCandidatura.RECEBIDA)
                .build();

        Candidatura salva = candidaturaRepository.save(candidatura);
        notificationService.notificarCandidaturaRealizada(candidatoLogado.getEmail(), vaga.getTitulo());
        return new CandidaturaResponseDTO(salva);
    }

    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> listarMinhasCandidaturas(Long usuarioId) {
        return candidaturaRepository.findByUsuarioIdOrderByDataAplicacaoDesc(usuarioId).stream()
                .map(CandidaturaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> listarPorVaga(Long vagaId) {
        return candidaturaRepository.findByVagaId(vagaId).stream()
                .map(CandidaturaResponseDTO::new)
                .toList();
    }

    @Transactional
    public CandidaturaResponseDTO avaliarCandidatura(Long candidaturaId, AvaliacaoCandidaturaRequestDTO dto) {
        Candidatura candidatura = candidaturaRepository.findById(candidaturaId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidatura não encontrada."));

        candidatura.setStatus(dto.status());
        candidatura.setFeedback(dto.feedback());
        candidatura.setNotaAvaliacao(dto.notaAvaliacao());
        Candidatura atualizada = candidaturaRepository.save(candidatura);
        notificationService.notificarFeedbackAtualizado(
                candidatura.getUsuario().getEmail(),
                candidatura.getVaga().getTitulo(),
                dto.status().name()
        );
        return new CandidaturaResponseDTO(atualizada);
    }
}

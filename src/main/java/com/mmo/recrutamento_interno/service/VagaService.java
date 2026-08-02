package com.mmo.recrutamento_interno.service;

import com.mmo.recrutamento_interno.domain.entity.Usuario;
import com.mmo.recrutamento_interno.domain.entity.Vaga;
import com.mmo.recrutamento_interno.domain.enums.StatusVaga;
import com.mmo.recrutamento_interno.dto.vaga.VagaRequestDTO;
import com.mmo.recrutamento_interno.dto.vaga.VagaResponseDTO;
import com.mmo.recrutamento_interno.exception.ResourceNotFoundException;
import com.mmo.recrutamento_interno.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    @Transactional(readOnly = true)
    public List<VagaResponseDTO> listarTodas() {
        return vagaRepository.findAll().stream()
                .map(VagaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VagaResponseDTO> listarAtivas() {
        return vagaRepository.findByStatus(StatusVaga.ABERTA).stream()
                .map(VagaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public VagaResponseDTO buscarPorId(Long id) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada com id: " + id));
        return new VagaResponseDTO(vaga);
    }

    @Transactional
    public VagaResponseDTO criar(VagaRequestDTO dto, Usuario usuarioLogado) {
        Vaga vaga = Vaga.builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .requisitos(dto.requisitos())
                .status(StatusVaga.ABERTA)
                .criadoPor(usuarioLogado)
                .build();

        Vaga salva = vagaRepository.save(vaga);
        return new VagaResponseDTO(salva);
    }

    @Transactional
    public VagaResponseDTO atualizar(Long id, VagaRequestDTO dto) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada com id: " + id));

        vaga.setTitulo(dto.titulo());
        vaga.setDescricao(dto.descricao());
        vaga.setRequisitos(dto.requisitos());

        return new VagaResponseDTO(vagaRepository.save(vaga));
    }

    @Transactional
    public void encerrarVaga(Long id) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada com id: " + id));
        vaga.setStatus(StatusVaga.ENCERRADA);
        vagaRepository.save(vaga);
    }
}
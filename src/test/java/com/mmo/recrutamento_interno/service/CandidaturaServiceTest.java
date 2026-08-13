package com.mmo.recrutamento_interno.service;

import com.mmo.recrutamento_interno.domain.entity.Candidatura;
import com.mmo.recrutamento_interno.domain.entity.Usuario;
import com.mmo.recrutamento_interno.domain.entity.Vaga;
import com.mmo.recrutamento_interno.domain.enums.Perfil;
import com.mmo.recrutamento_interno.domain.enums.StatusCandidatura;
import com.mmo.recrutamento_interno.domain.enums.StatusVaga;
import com.mmo.recrutamento_interno.dto.candidatura.AvaliacaoCandidaturaRequestDTO;
import com.mmo.recrutamento_interno.dto.candidatura.CandidaturaResponseDTO;
import com.mmo.recrutamento_interno.exception.BusinessException;
import com.mmo.recrutamento_interno.exception.ResourceNotFoundException;
import com.mmo.recrutamento_interno.repository.CandidaturaRepository;
import com.mmo.recrutamento_interno.repository.VagaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidaturaServiceTest {

    @Mock
    private CandidaturaRepository candidaturaRepository;

    @Mock
    private VagaRepository vagaRepository;

    @Mock
    private EmailNotificationService notificationService;

    @InjectMocks
    private CandidaturaService candidaturaService;

    private Usuario candidato;
    private Vaga vagaAberta;
    private Vaga vagaEncerrada;

    @BeforeEach
    void setUp() {
        candidato = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@empresa.com")
                .perfil(Perfil.ROLE_CANDIDATO)
                .build();

        vagaAberta = Vaga.builder()
                .id(10L)
                .titulo("Desenvolvedor Java Pleno")
                .status(StatusVaga.ABERTA)
                .build();

        vagaEncerrada = Vaga.builder()
                .id(20L)
                .titulo("Analista de QA")
                .status(StatusVaga.ENCERRADA)
                .build();
    }

    @Test
    @DisplayName("Deve aplicar para vaga com sucesso quando vaga aberta e sem candidatura prévia")
    void aplicarParaVaga_Sucesso() {
        when(vagaRepository.findById(10L)).thenReturn(Optional.of(vagaAberta));
        when(candidaturaRepository.existsByUsuarioIdAndVagaId(1L, 10L)).thenReturn(false);

        Candidatura candidaturaSalva = Candidatura.builder()
                .id(100L)
                .usuario(candidato)
                .vaga(vagaAberta)
                .status(StatusCandidatura.RECEBIDA)
                .build();

        when(candidaturaRepository.save(any(Candidatura.class))).thenReturn(candidaturaSalva);

        CandidaturaResponseDTO resultado = candidaturaService.aplicarParaVaga(10L, candidato);

        assertNotNull(resultado);
        assertEquals(100L, resultado.id());
        assertEquals("Desenvolvedor Java Pleno", resultado.vagaTitulo());
        assertEquals(StatusCandidatura.RECEBIDA, resultado.status());

        verify(candidaturaRepository, times(1)).save(any(Candidatura.class));
        verify(notificationService, times(1)).notificarCandidaturaRealizada(candidato.getEmail(), vagaAberta.getTitulo());
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar se candidatar para uma vaga ENCERRADA")
    void aplicarParaVaga_ErroVagaEncerrada() {

        when(vagaRepository.findById(20L)).thenReturn(Optional.of(vagaEncerrada));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            candidaturaService.aplicarParaVaga(20L, candidato);
        });

        assertEquals("Esta vaga já está encerrada.", exception.getMessage());
        verify(candidaturaRepository, never()).save(any(Candidatura.class));
        verify(notificationService, never()).notificarCandidaturaRealizada(anyString(), anyString());
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar se candidatar duas vezes na mesma vaga")
    void aplicarParaVaga_ErroCandidaturaDuplicada() {

        when(vagaRepository.findById(10L)).thenReturn(Optional.of(vagaAberta));
        when(candidaturaRepository.existsByUsuarioIdAndVagaId(1L, 10L)).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            candidaturaService.aplicarParaVaga(10L, candidato);
        });

        assertEquals("Você já se candidatou para esta vaga.", exception.getMessage());
        verify(candidaturaRepository, never()).save(any(Candidatura.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o id da vaga não for encontrado")
    void aplicarParaVaga_ErroVagaNaoEncontrada() {

        when(vagaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            candidaturaService.aplicarParaVaga(99L, candidato);
        });

        verify(candidaturaRepository, never()).save(any(Candidatura.class));
    }

    @Test
    @DisplayName("Deve listar as candidaturas do candidato logado")
    void listarMinhasCandidaturas_Sucesso() {
        Candidatura candidatura = Candidatura.builder()
                .id(100L)
                .usuario(candidato)
                .vaga(vagaAberta)
                .status(StatusCandidatura.RECEBIDA)
                .build();

        when(candidaturaRepository.findByUsuarioIdOrderByDataAplicacaoDesc(1L)).thenReturn(List.of(candidatura));

        List<CandidaturaResponseDTO> resultado = candidaturaService.listarMinhasCandidaturas(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).id());
        verify(candidaturaRepository, times(1)).findByUsuarioIdOrderByDataAplicacaoDesc(1L);
    }

    @Test
    @DisplayName("Deve listar candidaturas por vaga quando chamada por ADMIN")
    void listarPorVaga_Sucesso() {
        Candidatura candidatura = Candidatura.builder()
                .id(100L)
                .usuario(candidato)
                .vaga(vagaAberta)
                .status(StatusCandidatura.RECEBIDA)
                .build();
        when(candidaturaRepository.findByVagaId(10L)).thenReturn(List.of(candidatura));
        List<CandidaturaResponseDTO> resultado = candidaturaService.listarPorVaga(10L);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(candidaturaRepository, times(1)).findByVagaId(10L);
    }

    @Test
    @DisplayName("Deve retornar lista vazia ao listar candidaturas de uma vaga sem registros")
    void listarPorVaga_VagaSemCandidaturas() {
        when(candidaturaRepository.findByVagaId(99L)).thenReturn(List.of());

        List<CandidaturaResponseDTO> resultado = candidaturaService.listarPorVaga(99L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(candidaturaRepository, times(1)).findByVagaId(99L);
    }

    @Test
    @DisplayName("Deve avaliar candidatura com sucesso quando dados válidos")
    void avaliarCandidatura_Sucesso() {
        Candidatura candidaturaExistente = Candidatura.builder()
                .id(100L)
                .usuario(candidato)
                .vaga(vagaAberta)
                .status(StatusCandidatura.RECEBIDA)
                .build();

        AvaliacaoCandidaturaRequestDTO dto = new AvaliacaoCandidaturaRequestDTO(
                StatusCandidatura.APROVADO,
                "Candidato atende aos requisitos técnicos",
                9
        );

        when(candidaturaRepository.findById(100L)).thenReturn(Optional.of(candidaturaExistente));
        when(candidaturaRepository.save(any(Candidatura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CandidaturaResponseDTO resultado = candidaturaService.avaliarCandidatura(100L, dto);

        assertNotNull(resultado);
        assertEquals(StatusCandidatura.APROVADO, resultado.status());
        assertEquals("Candidato atende aos requisitos técnicos", resultado.feedback());
        assertEquals(9, resultado.notaAvaliacao());

        verify(candidaturaRepository, times(1)).save(candidaturaExistente);
        verify(notificationService, times(1)).notificarFeedbackAtualizado(
                candidato.getEmail(),
                vagaAberta.getTitulo(),
                "APROVADO"
        );
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar avaliar candidatura inexistente")
    void avaliarCandidatura_ErroCandidaturaNaoEncontrada() {
        AvaliacaoCandidaturaRequestDTO dto = new AvaliacaoCandidaturaRequestDTO(
                StatusCandidatura.REJEITADO,
                "Perfil desalinhado",
                4
        );

        when(candidaturaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            candidaturaService.avaliarCandidatura(999L, dto);
        });

        verify(candidaturaRepository, never()).save(any(Candidatura.class));
    }
}
package com.mmo.recrutamento_interno.domain.entity;

import com.mmo.recrutamento_interno.domain.enums.StatusVaga;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vagas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String requisitos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusVaga status = StatusVaga.ABERTA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por_id")
    private Usuario criadoPor;

    @Column(name = "data_criacao", insertable = false, updatable = false)
    private LocalDateTime dataCriacao;
}

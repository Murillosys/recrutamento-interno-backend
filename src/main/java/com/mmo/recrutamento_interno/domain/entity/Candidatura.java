package com.mmo.recrutamento_interno.domain.entity;

import com.mmo.recrutamento_interno.domain.enums.StatusCandidatura;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidaturas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "vaga_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusCandidatura status = StatusCandidatura.RECEBIDA;

    @Column(name = "data_aplicacao", insertable = false, updatable = false)
    private LocalDateTime dataAplicacao;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "nota_avaliacao")
    private Integer notaAvaliacao;
}
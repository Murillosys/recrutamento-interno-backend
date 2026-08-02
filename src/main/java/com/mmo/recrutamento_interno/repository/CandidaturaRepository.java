package com.mmo.recrutamento_interno.repository;

import com.mmo.recrutamento_interno.domain.entity.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {


    boolean existsByUsuarioIdAndVagaId(Long usuarioId, Long vagaId);

    List<Candidatura> findByUsuarioIdOrderByDataAplicacaoDesc(Long usuarioId);

    List<Candidatura> findByVagaId(Long vagaId);
}

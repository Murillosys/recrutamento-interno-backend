package com.mmo.recrutamento_interno.repository;

import com.mmo.recrutamento_interno.domain.entity.Vaga;
import com.mmo.recrutamento_interno.domain.enums.StatusVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

    List<Vaga> findByStatus(StatusVaga status);

    List<Vaga> findByTituloContainingIgnoreCaseOrRequisitosContainingIgnoreCase(String titulo, String requisitos);
}

package com.mmo.recrutamento_interno.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    public void notificarCandidaturaRealizada(String emailCandidato, String tituloVaga) {
        log.info("[NOTIFICAÇÃO] E-mail enviado para {}: Candidatura na vaga '{}' recebida com sucesso!",
                emailCandidato, tituloVaga);
    }

    public void notificarFeedbackAtualizado(String emailCandidato, String tituloVaga, String status) {
        log.info("[NOTIFICAÇÃO] E-mail enviado para {}: A sua candidatura para a vaga '{}' foi atualizada para: {}.",
                emailCandidato, tituloVaga, status);
    }
}

INSERT INTO usuarios (nome, email, senha, perfil)
VALUES ('Recrutador RH', 'admin@empresa.com', '$2a$10$5DAD16yCAkaisuGlyrPbU.64TjtdHL9DKtITukCINSd09cCxuFscS','ROLE_ADMIN'),
       ('João Silva', 'joao.candidato@empresa.com', '$2a$10$5DAD16yCAkaisuGlyrPbU.64TjtdHL9DKtITukCINSd09cCxuFscS','ROLE_CANDIDATO'),
       ('Maria Souza', 'maria.candidato@empresa.com', '$2a$10$5DAD16yCAkaisuGlyrPbU.64TjtdHL9DKtITukCINSd09cCxuFscS','ROLE_CANDIDATO');

INSERT INTO vagas (titulo, descricao, requisitos, status, criado_por_id)
VALUES ('Desenvolvedor Full Stack Pleno',
        'Atuação no desenvolvimento de novas funcionalidades para a plataforma interna de RH.',
        'Java 8+, Spring Boot, Angular 2+, PostgreSQL, Git.',
        'ABERTA',
        1),
       ('Analista de Dados',
        'Responsável por estruturar dashboards e relatórios operacionais.',
        'SQL Avançado, Python, Power BI.',
        'ABERTA',
        1);

INSERT INTO candidaturas (usuario_id, vaga_id, status, feedback, nota_avaliacao)
VALUES (2, 1, 'EM_ANALISE', 'Perfil técnico condizente com a vaga. Agendada primeira conversa.', 4),
       (3, 1, 'RECEBIDA', NULL, NULL);
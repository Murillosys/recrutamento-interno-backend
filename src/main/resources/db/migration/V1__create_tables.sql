CREATE TABLE usuarios
(
    id           BIGSERIAL PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    email        VARCHAR(150) NOT NULL UNIQUE,
    senha        VARCHAR(255) NOT NULL,
    perfil       VARCHAR(20)  NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vagas
(
    id            BIGSERIAL PRIMARY KEY,
    titulo        VARCHAR(100) NOT NULL,
    descricao     TEXT         NOT NULL,
    requisitos    TEXT         NOT NULL,
    status        VARCHAR(20)  NOT NULL DEFAULT 'ABERTA',
    criado_por_id BIGINT       REFERENCES usuarios (id) ON DELETE SET NULL,
    data_criacao  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE candidaturas
(
    id             BIGSERIAL PRIMARY KEY,
    usuario_id     BIGINT      NOT NULL REFERENCES usuarios (id) ON DELETE CASCADE,
    vaga_id        BIGINT      NOT NULL REFERENCES vagas (id) ON DELETE CASCADE,
    status         VARCHAR(20) NOT NULL DEFAULT 'RECEBIDA',
    data_aplicacao TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    feedback       TEXT,
    nota_avaliacao INT CHECK (nota_avaliacao BETWEEN 1 AND 5),
    CONSTRAINT uk_usuario_vaga UNIQUE (usuario_id, vaga_id)
);
# 🚀 Plataforma de Recrutamento Interno - Backend RESTful API

[![Java 21+](https://img.shields.io/badge/Java-21%2B-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot 3.4](https://img.shields.io/badge/Spring%20Boot-3.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker Compose](https://img.shields.io/badge/Docker%20Compose-Enabled-blue.svg)](https://www.docker.com/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-green.svg)](http://localhost:8080/swagger-ui/index.html)

Solução backend desenvolvida para gerenciar processos de **Recrutamento Interno**, permitindo que colaboradores pesquisem e se candidatem a vagas internas, enquanto o time de RH (Administradores) gerencia posições, analisa perfis e fornece feedbacks.

---

## 📌 Principais Funcionalidades

- **Autenticação & Segurança Stateless:** Controle de acesso baseado em perfis (`ROLE_ADMIN` e `ROLE_CANDIDATO`) via **Spring Security** e **JWT (JSON Web Token)**.
- **Gestão de Vagas (RH):** Cadastro, atualização, consulta e encerramento de vagas com requisitos dinâmicos.
- **Inscrição & Trava de Segregação (Candidato):** Regra de negócio impedindo candidaturas duplicadas no mesmo processo ou inscrições em vagas já encerradas.
- **Painel do Candidato:** Acompanhamento em tempo real do status das candidaturas (`RECEBIDA`, `EM_ANALISE`, `APROVADO`, `REJEITADO`) e consulta a feedbacks.
- **Avaliação de Candidatos (RH):** Atribuição de notas (1 a 5) e pareceres técnicos sobre os participantes.
- **Sistema de Notificação:** Notificação por e-mail/console no ato da candidatura e atualização de parecer.
- **Versionamento de Banco de Dados:** Migrações DDL e DML gerenciadas via **Flyway**.

---

## 🏗️ Estrutura do Projeto

Estruturação arquitetural em camadas desacopladas seguindo boas práticas de **Clean Code**, **Domain-Driven Design (DDD)** e padrões **RESTful**:

```text
recrutamento-interno/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mmo/recrutamento_interno/
│   │   │       ├── config/           # Configurações globais (OpenAPI/Swagger, Beans)
│   │   │       ├── controller/       # Controllers RESTful (Endpoints e Swagger Annotations)
│   │   │       ├── domain/           # Camada de Domínio / Regras de Negócio
│   │   │       │   ├── entity/       # Entidades JPA (Usuario, Vaga, Candidatura)
│   │   │       │   └── enums/        # Enums de Controle (Perfil, StatusVaga, StatusCandidatura)
│   │   │       ├── dto/              # Data Transfer Objects (Java Records e Jakarta Validation)
│   │   │       │   ├── auth/         # DTOs de Autenticação e Token
│   │   │       │   ├── candidatura/  # DTOs de Candidatura e Avaliação
│   │   │       │   └── vaga/         # DTOs de Cadastro e Resposta de Vagas
│   │   │       ├── exception/        # Handler Global de Exceções e Erros Customizados
│   │   │       ├── repository/       # Repositórios JPA de Acesso ao Banco
│   │   │       ├── security/         # Infraestrutura de Segurança (Filtros JWT e SecurityConfig)
│   │   │       └── service/          # Camada de Serviços, Lógicas e Notificação
│   │   └── resources/
│   │       ├── db/migration/         # Scripts de Migração Flyway (V1, V2...)
│   │       └── application.properties # Parâmetros de Configuração da Aplicação
│   └── test/
│       └── java/
│           └── com/mmo/recrutamento_interno/service/
│               └── CandidaturaServiceTest.java # Testes Unitários (JUnit 5 + Mockito)
├── .gitignore
├── docker-compose.yml                # Subida do Container PostgreSQL
├── pom.xml                           # Dependências do Projeto (Maven)
└── README.md                         # Documentação da Solução
```
---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21 / 25
- **Framework:** Spring Boot 3+ (Spring Web, Spring Security, Spring Data JPA, Spring Validation)
- **Banco de Dados:** PostgreSQL 16
- **Versionamento DB:** Flyway Migration
- **Segurança:** Authentication via JWT (JSON Web Token) + BCrypt Password Encoder
- **Documentação:** Springdoc OpenAPI 3 / Swagger UI
- **Testes:** JUnit 5, Mockito & AssertJ
- **Containerização:** Docker & Docker Compose
- **Boilerplate Reduction:** Lombok

---

## ⚡ Como Rodar o Projeto

### Pré-requisitos
- **Java 21** (ou superior) instalado
- **Docker** e **Docker Compose** instalados e em execução
- **Git**

### Passo a Passo

1. **Clonar o Repositório:**
   ```bash
   git clone https://github.com/Murillosys/recrutamento-interno-backend.git
   cd recrutamento-interno
   ```
2. **Subir o Container do Banco de Dados PostgreSQL:**
   ```bash
   docker compose up -d
   ```

3. **Executar a Aplicação Spring Boot:
   No seu terminal ou IDE (IntelliJ/VSCode), execute o comando Maven:**
   ```bash
   ./mvnw clean spring-boot:run
   ```
---
## 📄 Documentação dos Endpoints (Swagger UI)

Com a aplicação rodando, acesse a documentação interativa e testável no seu navegador:

👉 **URL do Swagger:** http://localhost:8080/swagger-ui/index.html

### Como Testar Endpoints Autenticados no Swagger:
1. Faça uma requisição `POST /auth/login` enviando o e-mail e senha cadastrados.
2. Copie o `token` retornado na resposta.
3. Clique no botão **Authorize** (no canto superior direito do Swagger).
4. Cole o token no campo de texto e confirme. Agora você pode executar todos os endpoints protegidos.

---

## 🧪 Testes Unitários

Para garantir a qualidade e corretude das regras de negócio, a camada de serviço foi coberta por testes automatizados com Mockito.

Para rodar a suíte de testes unitários:

   ```bash
  ./mvnw test
   ```
---
## 👥 Credenciais de Teste Padrão (Carga Inicial Flyway)

| Nome | E-mail | Senha Padrão | Perfil |
| :--- | :--- | :--- | :--- |
| **Recrutador RH** | `admin@empresa.com` | `admin1234` | `ROLE_ADMIN` |
| **João Silva** | `joao.candidato@empresa.com` | `admin1234` | `ROLE_CANDIDATO` |
| **Maria Souza** | `maria.candidato@empresa.com` | `admin1234` | `ROLE_CANDIDATO` |
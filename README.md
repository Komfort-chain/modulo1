# **Módulo 1 — API de Pessoas (Komfort Chain)**

O **Módulo 1** da suíte **Komfort Chain** implementa a API REST responsável pela **gestão de pessoas**.
O serviço fornece operações de CRUD completas com **persistência em banco PostgreSQL**, **validação de dados**, logs estruturados via **Graylog**, além de integração total com o pipeline de qualidade (SonarCloud + OWASP Dependency-Check).

A aplicação segue os princípios de **Clean Architecture**, **SOLID** e boas práticas de **engenharia de software corporativa**.

---

## **Status do Projeto**

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)
[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)
[![Docker Hub](https://img.shields.io/badge/DockerHub-magyodev/api--pessoas-blue)](https://hub.docker.com/repository/docker/magyodev/api-pessoas)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)

---

## **Tecnologias Utilizadas**

| Categoria            | Tecnologia / Ferramenta              |
| -------------------- | ------------------------------------ |
| **Linguagem**        | Java 21                              |
| **Framework**        | Spring Boot 3.5.7                    |
| **Banco de Dados**   | PostgreSQL 16                        |
| **Logs**             | Logback GELF → Graylog 5.2           |
| **Build**            | Maven Wrapper (`mvnw`)               |
| **Testes**           | JUnit 5 + Spring Boot Test + JaCoCo  |
| **Análise Estática** | SonarCloud + OWASP Dependency-Check  |
| **Containerização**  | Docker + Docker Compose              |
| **Arquitetura**      | Clean Architecture • SOLID • RESTful |

---

## **Estrutura do Projeto**

```bash
modulo1/
├── docker-compose.yml
├── Dockerfile
├── .github/workflows/
│   ├── full-ci.yml
│   └── release.yml
│
└── pessoas/
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    ├── src/main/java/com/cabos/pessoas/
    │   ├── application/
    │   │   ├── dto/PessoaDTO.java
    │   │   └── service/PessoaService.java
    │   ├── domain/Pessoa.java
    │   ├── infrastructure/persistence/repository/PessoaRepository.java
    │   └── presentation/
    │       ├── controller/PessoaController.java
    │       ├── handler/GlobalExceptionHandler.java
    │       └── mapper/PessoaMapper.java
    └── src/main/resources/
        ├── application.yml
        └── logback-spring.xml
```

### **Fluxo Arquitetural**

```
Controller → Service → Repository → Domain
```

---

## **Execução Local**

### **1. Clonar o repositório**

```bash
git clone https://github.com/Komfort-chain/modulo1.git
cd modulo1
```

### **2. Gerar o artefato**

```bash
cd pessoas
./mvnw clean package -DskipTests
```

### **3. Subir os containers**

```bash
docker compose build
docker compose up -d
```

### **Serviços esperados**

| Serviço        | Porta | Descrição                         |
| -------------- | ----- | --------------------------------- |
| API de Pessoas | 8081  | Endpoints REST                    |
| PostgreSQL     | 5432  | Armazenamento de pessoas          |
| Graylog        | 9009  | Logs centralizados                |
| SonarQube (*)  | 9000  | Análise estática local (opcional) |

---

## **Endpoints Principais**

| Método | Endpoint        | Descrição        |
| ------ | --------------- | ---------------- |
| POST   | `/pessoas`      | Cria nova pessoa |
| GET    | `/pessoas`      | Lista paginada   |
| GET    | `/pessoas/{id}` | Busca por ID     |
| PUT    | `/pessoas/{id}` | Atualiza pessoa  |
| DELETE | `/pessoas/{id}` | Remove pessoa    |

### **Exemplo de criação (POST)**

```json
{
  "nome": "Rita de Cássia Silva",
  "ativo": true,
  "dtNascimento": "1994-05-10"
}
```

### **Exemplo de listagem paginada**

```
GET http://localhost:8081/pessoas?page=0&size=5
```

---

## **Pipeline CI/CD**

### **Workflow Principal — `full-ci.yml`**

Inclui:

1. Build + testes
2. SonarCloud
3. OWASP Dependency-Check
4. JaCoCo coverage
5. Build & push para o Docker Hub

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)

### **Workflow de Release — `release.yml`**

Gera automaticamente:

* Tag semântica
* Release notes
* Imagens docker versionadas (`vX.Y.Z`)

[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)

---

## **Imagem Docker**

| Serviço        | Docker Hub                                                                                                                     |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| API de Pessoas | [https://hub.docker.com/repository/docker/magyodev/api-pessoas](https://hub.docker.com/repository/docker/magyodev/api-pessoas) |

Tags disponíveis:

* `latest`
* `${run_number}` (CI/CD)
* `vX.Y.Z` (Releases)

---

## **Logs e Monitoramento**

Logs em tempo real:

```bash
docker logs -f api-pessoas
```

---

## **Diagrama Simplificado**

```
┌────────────┐       ┌──────────────────────┐       ┌───────────────┐
│   Cliente  │ ───▶  │  API de Pessoas      │ ───▶  │  PostgreSQL   │
└────────────┘       └──────────────────────┘       └───────────────┘
                              │
                              ▼
                       ┌────────────┐
                       │  Graylog   │
                       └────────────┘
```

---

## **Contribuição**

1. Fork
2. Branch: `feature/nova-funcionalidade`
3. Commits semânticos
4. Pull Request para `main`

---

## **Autor**

**Alan de Lima Silva (MagyoDev)**
* [GitHub](https://github.com/MagyoDev)
* [Docker Hub](https://hub.docker.com/u/magyodev) 
* [E-mail](mailto:magyodev@gmail.com)

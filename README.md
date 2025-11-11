# Módulo 1 — API de Pessoas (Komfort Chain)

O **Módulo 1** da suíte **Komfort Chain** implementa a API REST responsável pela **gestão de pessoas**.  
O serviço fornece operações de CRUD completas com **persistência em banco PostgreSQL**, **validação de dados**, **logs centralizados via Graylog** e integração com o pipeline de qualidade (SonarCloud + OWASP Dependency-Check).  

A aplicação segue os princípios de **Clean Architecture**, **SOLID** e boas práticas de **engenharia de software corporativa**.

---

## Status do Projeto

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)
[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)

---

## Tecnologias Utilizadas

| Categoria            | Tecnologia / Ferramenta                      |
| -------------------- | ------------------------------------------- |
| **Linguagem**        | Java 21                                     |
| **Framework**        | Spring Boot 3.5.7                            |
| **Banco de Dados**   | PostgreSQL 16                               |
| **Logs**             | Logback GELF → Graylog 5.2                  |
| **Build**            | Maven Wrapper (`mvnw`)                      |
| **Testes**           | JUnit 5 + Spring Boot Test + JaCoCo         |
| **Análise Estática** | SonarCloud + OWASP Dependency Check         |
| **Containerização**  | Docker e Docker Compose                     |
| **Arquitetura**      | Clean Architecture / SOLID / RESTful        |

---

## Estrutura do Projeto

```bash
modulo1/
├── docker-compose.yml
├── Dockerfile
├── .github/workflows/
│   ├── full-ci.yml       # CI/CD completo: build, testes, análise e publicação
│   └── release.yml       # Geração automática de releases
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
````

### Fluxo Arquitetural

```
Controller → Service → Repository → Domain
```

---

## Execução Local

### 1. Clonar o repositório

```bash
git clone https://github.com/Komfort-chain/modulo1.git
cd modulo1
```

### 2. Gerar o artefato

```bash
cd pessoas
./mvnw clean package -DskipTests
```

### 3. Subir os containers

```bash
docker compose build
docker compose up -d
```

**Serviços esperados:**

| Serviço        | Porta | Descrição                             |
| -------------- | ----- | ------------------------------------- |
| API de Pessoas | 8081  | Endpoints REST                        |
| PostgreSQL     | 5432  | Armazenamento de pessoas              |
| Graylog        | 9009  | Monitoramento e logs centralizados    |
| SonarQube (*)  | 9000  | Análise estática de código (opcional) |

---

## Endpoints Principais

| Método | Endpoint        | Descrição                         |
| ------ | --------------- | --------------------------------- |
| POST   | `/pessoas`      | Cria uma nova pessoa              |
| GET    | `/pessoas`      | Lista todas as pessoas (paginado) |
| GET    | `/pessoas/{id}` | Busca uma pessoa pelo ID          |
| PUT    | `/pessoas/{id}` | Atualiza uma pessoa               |
| DELETE | `/pessoas/{id}` | Remove uma pessoa                 |

### Exemplo POST

```http
POST http://localhost:8081/pessoas
Content-Type: application/json
```

```json
{
  "nome": "Rita de Cássia Silva",
  "ativo": true,
  "dtNascimento": "1994-05-10"
}
```

### Exemplo GET Paginado

```
GET http://localhost:8081/pessoas?page=0&size=5
```

### Exemplo DELETE

```
DELETE http://localhost:8081/pessoas/1
```

---

## Pipeline CI/CD

### Workflow Principal — `full-ci.yml`

Executa automaticamente:

1. Build e testes com Maven Wrapper;
2. Análise de qualidade com **SonarCloud**;
3. Verificação de vulnerabilidades via **OWASP Dependency-Check**;
4. Geração de relatórios de cobertura (JaCoCo);
5. Build e publicação da imagem Docker no **Docker Hub**.

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)

### Workflow de Release — `release.yml`

Cria automaticamente um release no GitHub a cada tag do tipo `vX.Y.Z`, gerando as notas de versão.

[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)

---

## Imagem Docker

A imagem é gerada e publicada automaticamente no **Docker Hub**:

| Serviço        | Repositório Docker Hub                                                                |
| -------------- | ------------------------------------------------------------------------------------- |
| API de Pessoas | [magyodev/api-pessoas](https://hub.docker.com/repository/docker/magyodev/api-pessoas) |

Cada módulo possui seu próprio `Dockerfile` e é construído de forma independente pelo pipeline.

---

## Logs e Monitoramento

A observabilidade é implementada com **Logback GELF**, enviando logs estruturados para o **Graylog**.

Visualizar logs em tempo real:

```bash
docker logs -f api-pessoas
```

Cada registro inclui timestamp, nível, classe e mensagem do evento.

---

## Diagrama Simplificado

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

## Contribuição

1. Faça um fork do projeto;
2. Crie uma branch: `feature/nova-funcionalidade`;
3. Realize as alterações e commits semânticos;
4. Envie um Pull Request para a branch `main`.

---

## Autor

**Alan de Lima Silva (MagyoDev)**
[GitHub](https://github.com/MagyoDev) • [Docker Hub](https://hub.docker.com/u/magyodev) • [E-mail](mailto:magyodev@gmail.com)

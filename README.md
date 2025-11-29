# **Módulo 1 — API de Pessoas (Komfort Chain)**

O **Módulo 1** da suíte **Komfort Chain** implementa uma API REST para **gestão de pessoas**, incluindo criação, consulta, atualização, listagem paginada e exclusão lógica (campo `ativo = false`).
O módulo segue boas práticas de arquitetura (separação clara entre camadas), princípios SOLID aplicados ao serviço e uso de padrões adequados para DTOs, mapeamento, domínio e infraestrutura.

O projeto foi desenvolvido em **Java 21**, utilizando **Spring Boot 3.5.7**, e conta com toda a observabilidade e análise estática necessária para evolução segura ao longo dos próximos módulos.

---

## **Status do Projeto**

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)
[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Docker Hub](https://img.shields.io/badge/DockerHub-magyodev%2Fapi--pessoas-blue)](https://hub.docker.com/repository/docker/magyodev/api-pessoas)

---

## **Tecnologias Utilizadas**

| Categoria        | Tecnologias / Ferramentas                       |
| ---------------- | ----------------------------------------------- |
| Linguagem        | Java 21                                         |
| Framework        | Spring Boot 3.5.7                               |
| Banco de Dados   | PostgreSQL 16                                   |
| Logs             | Logback GELF → Graylog 5.2                      |
| Testes           | JUnit 5 • Spring Boot Test • JaCoCo             |
| Análise Estática | SonarCloud • OWASP Dependency-Check • CycloneDX |
| Build            | Maven Wrapper (mvnw)                            |
| Containers       | Docker e Docker Compose                         |

---

# **Arquitetura e Organização Interna**

A estrutura do projeto segue uma separação clara em:

* **api** → controllers e tratamento de exceções
* **application** → DTOs, mapper e serviço contendo as regras de negócio
* **domain** → entidade principal e exceções de domínio
* **infrastructure/repository** → persistência com Spring Data JPA

Estrutura real do projeto:

```bash
src/main/java/com/cabos/pessoas/
├── PessoasApplication.java
│
├── api/
│   ├── PessoaController.java
│   └── GlobalExceptionHandler.java
│
├── application/
│   ├── dto/
│   │   ├── PessoaRequestDTO.java
│   │   └── PessoaResponseDTO.java
│   ├── mapper/
│   │   └── PessoaMapper.java
│   └── service/
│       └── PessoaService.java
│
├── domain/
│   ├── Pessoa.java
│   └── exception/
│       └── PessoaNaoEncontradaException.java
│
└── infrastructure/
    └── repository/
        └── PessoaRepository.java
```

## **Resumo das responsabilidades**

### **API (Camada de Entrada)**

* Exposição dos endpoints REST
* Validação de entrada
* Mapeamento DTO ↔ domínio
* Tratamento global de exceções

### **Application (Regras de Negócio)**

* Implementação das operações: criar, atualizar, listar ativos, buscar, deletar
* Encapsulamento da lógica central
* Conversões e resposta padronizada

### **Domain (Modelo)**

* Entidade `Pessoa`
* Métodos de negócio: `atualizar()` e `desativar()`
* Exceções específicas

### **Infrastructure (Persistência)**

* Repositório JPA
* Filtro para retornar apenas registros ativos

---

# **Execução Local**

## 1. Clone do projeto

```bash
git clone https://github.com/Komfort-chain/modulo1.git
cd modulo1
```

## 2. Build

```bash
./mvnw clean package -DskipTests
```

## 3. Subir com Docker Compose

```bash
docker compose up -d --build
```

---

# **Serviços Disponíveis**

| Serviço     | Porta | Descrição         |
| ----------- | ----- | ----------------- |
| API Pessoas | 8081  | Endpoints REST    |
| PostgreSQL  | 5432  | Banco relacional  |
| Graylog     | 9009  | Logs estruturados |

---

# **Endpoints (API v1)**

Base URL:

```
http://localhost:8081/api/v1/pessoas
```

| Método | Rota  | Função                   |
| ------ | ----- | ------------------------ |
| POST   | /     | Criar pessoa             |
| GET    | /     | Listar paginado (ativos) |
| GET    | /{id} | Buscar por ID            |
| PUT    | /{id} | Atualizar                |
| DELETE | /{id} | Remover (inativar)       |

---

# **CI/CD**

A pipeline completa está em `.github/workflows/`.

### **full-ci.yml**

Executado a cada push:

* Build Maven
* Testes + JaCoCo
* SonarCloud
* OWASP Dependency Check
* Build e push da imagem Docker

### **release.yml**

Executado em tags `vX.Y.Z`:

* Build final da aplicação
* Changelog automático
* Publicação da release
* Upload do `.jar`
* Push da imagem versionada

---

# **Imagem Docker Oficial**

```bash
docker pull magyodev/api-pessoas
```

Tags:

* `latest`
* `vX.Y.Z`
* `run-<pipeline>`

---

# **Contribuição**

1. Criar fork
2. Criar branch `feature/...`
3. Commits semânticos
4. Abrir PR

---

# **Autor**

**Alan de Lima Silva (MagyoDev)**
* GitHub: [https://github.com/MagyoDev](https://github.com/MagyoDev)
* Docker Hub: [https://hub.docker.com/u/magyodev](https://hub.docker.com/u/magyodev)
* E-mail: [magyodev@gmail.com](mailto:magyodev@gmail.com)
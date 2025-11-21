# **Módulo 1 — API de Pessoas (Komfort Chain)**

O **Módulo 1** inicia a suíte **Komfort Chain**, oferecendo uma API REST para gerenciamento de pessoas, incluindo criação, listagem paginada, consulta por ID, atualização e remoção.
A arquitetura foi construída com foco em **separação de responsabilidades**, **baixa complexidade**, **testabilidade** e **previsibilidade**, seguindo princípios de **Clean Architecture** e **SOLID**.

O módulo integra banco PostgreSQL, logs estruturados com Graylog, verificação de qualidade via SonarCloud, testes automatizados e pipelines completos de CI/CD.

---

## **Status do Projeto**

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)
[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Docker Hub](https://img.shields.io/badge/DockerHub-magyodev%2Fapi--pessoas-blue)](https://hub.docker.com/repository/docker/magyodev/api-pessoas)

---

## **Tecnologias Utilizadas**

| Categoria        | Tecnologia                                |
| ---------------- | ----------------------------------------- |
| Linguagem        | Java 21                                   |
| Framework        | Spring Boot 3.5.7                         |
| Banco de Dados   | PostgreSQL 16                             |
| Logs             | Logback GELF → Graylog 5.2                |
| Testes           | JUnit 5 • Spring Boot Test • JaCoCo       |
| Build            | Maven Wrapper (mvnw)                      |
| Análise Estática | SonarCloud • OWASP Dependency-Check       |
| Containerização  | Docker • Docker Compose                   |
| Arquitetura      | Clean Architecture • SOLID • RESTful APIs |

---

## **Arquitetura Geral**

Fluxo central de execução da aplicação:

```text
Controller → Service → Repository → Domain
```

Essa divisão garante:

* **camadas independentes** e fáceis de testar;
* **domínio puro**, sem dependências externas;
* **infraestrutura substituível** sem afetar serviços ou controladores;
* **lógica de negócio concentrada no serviço**, seguindo Clean Architecture.

### Relação com Clean Architecture

* **presentation** → entrega/entrada HTTP
* **application** → casos de uso
* **domain** → entidades puras
* **infrastructure** → persistência e detalhes externos

### Relação com SOLID

* **SRP**: cada classe executa um papel claro (controller, serviço, mapper, repositório).
* **OCP**: novas regras podem ser adicionadas sem alterar estruturas existentes.
* **DIP**: serviço depende da **interface** `PessoaRepository`, nunca da implementação concreta.

---

## **Organização da Estrutura de Pastas**

A estrutura segue o mesmo padrão adotado em todos os módulos da suíte, garantindo consistência.

```text
modulo1/
├── docker-compose.yml
├── Dockerfile
├── README.md
│
├── .github/workflows/
│   ├── full-ci.yml
│   └── release.yml
│
└── pessoas/
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    │
    ├── src/main/java/com/cabos/pessoas/
    │   ├── application/
    │   │   ├── dto/
    │   │   └── service/
    │   │
    │   ├── domain/
    │   │   └── model/
    │   │
    │   ├── infrastructure/
    │   │   └── persistence/repository/
    │   │
    │   └── presentation/
    │       ├── controller/
    │       ├── handler/
    │       └── mapper/
    │
    └── src/main/resources/
        ├── application.yml
        └── logback-spring.xml
```

Agora, a explicação detalhada de cada camada no mesmo estilo do Módulo 2:

---

## **2.1. `application/` – Casos de Uso**

Contém regras de aplicação e lógica que coordena o fluxo entre domínio e infraestrutura.

### **dto/**

Contém DTOs como:

* `PessoaDTO`

Esses objetos:

* evitam expor a entidade `Pessoa` diretamente para a API;
* padronizam entrada e saída da aplicação;
* seguem a ideia de **fronteira limpa** entre camadas;
* aplicam **SRP**, já que cada DTO define apenas os atributos necessários ao transporte.

### **service/**

Contém classes como:

* `PessoaService`

Responsável por:

* executar regras de negócio (filtragem por ativo, paginação, alterações etc.);
* manipular o domínio sem expor detalhes técnicos;
* utilizar abstrações (interfaces) em vez de implementações concretas (**DIP**).

---

## **2.2. `domain/` – Regras de Negócio**

Contém o núcleo da aplicação.

### **model/**

* `Pessoa`

A entidade principal:

* não tem dependências externas;
* representa o modelo real de pessoa;
* pode evoluir sem impactar controllers ou banco.

Em Clean Architecture, essa é a camada mais estável.

---

## **2.3. `infrastructure/` – Detalhes Técnicos**

A infraestrutura concretiza as interfaces definidas na aplicação e no domínio.

### **persistence/repository/**

* `PessoaRepository`

Um contrato de acesso ao banco.
Implementado via Spring Data JPA.

Pontos principais:

* responsabilidades de persistência ficam isoladas;
* banco pode ser trocado sem afetar camadas superiores;
* segue o **Princípio da Inversão de Dependência**.

---

## **2.4. `presentation/` – Interface HTTP**

Tudo que envolve comunicação com o usuário externo (HTTP).

### **controller/**

* `PessoaController`

Responsável por expor endpoints REST:

* `POST /pessoas`
* `GET /pessoas`
* `GET /pessoas/{id}`
* `PUT /pessoas/{id}`
* `DELETE /pessoas/{id}`

Importante:

* controller **não sabe nada sobre o banco**;
* apenas recebe solicitações, chama o serviço e retorna respostas.

### **handler/**

* `GlobalExceptionHandler`

O papel é o mesmo do módulo 2:

* interceptar exceções e gerar respostas padronizadas;
* evitar duplicação de try/catch;
* centralizar mensagens e códigos de erro.

### **mapper/**

* `PessoaMapper`

Converte:

* `Pessoa` ↔ `PessoaDTO`

Serve para:

* manter camadas desacopladas;
* evitar poluir o domínio com detalhes de apresentação.

---

## **Execução Local**

### Clonar o repositório

```bash
git clone https://github.com/Komfort-chain/modulo1.git
cd modulo1
```

### Build

```bash
cd pessoas
./mvnw clean package -DskipTests
```

### Executar com Docker

```bash
docker compose up --build -d
```

---

## **Serviços Disponíveis**

| Serviço     | Porta | Descrição                      |
| ----------- | ----- | ------------------------------ |
| API Pessoas | 8081  | Endpoints REST                 |
| PostgreSQL  | 5432  | Banco de dados                 |
| Graylog     | 9009  | Logs estruturados da aplicação |

---

## **Endpoints**

| Método | Rota          | Descrição                          |
| ------ | ------------- | ---------------------------------- |
| POST   | /pessoas      | Criar pessoa                       |
| GET    | /pessoas      | Listar pessoas (paginado + ativos) |
| GET    | /pessoas/{id} | Buscar por ID                      |
| PUT    | /pessoas/{id} | Atualizar dados                    |
| DELETE | /pessoas/{id} | Remover pessoa                     |

---

## **Testes Automatizados**

A estrutura de testes segue o padrão das camadas internas:

```text
src/test/java/com/cabos/pessoas/
    application/service/
    presentation/controller/
    presentation/mapper/
```

Os testes validam:

* regras de negócio;
* conversões de DTOs;
* comportamento dos endpoints;
* responses e status HTTP.

O conjunto garante:

* cobertura para SonarCloud;
* estabilidade do módulo;
* fácil detecção de regressões.

---

## **CI/CD**

### **full-ci.yml**

Executado em push/PR:

* build + testes
* cobertura JaCoCo
* SonarCloud
* OWASP Dependency-Check
* build/push Docker

### **release.yml**

Executado ao criar tag SemVer:

* build completo
* changelog automático
* upload de artefatos
* imagens Docker versionadas

---

## **Imagem Docker Oficial**

```
magyodev/api-pessoas
```

Tags:

* `latest`
* `${run_number}`
* `vX.Y.Z`

---

## **Autor**

**Alan de Lima Silva (MagyoDev)**
* GitHub: [https://github.com/MagyoDev](https://github.com/MagyoDev)
* Docker Hub: [https://hub.docker.com/u/magyodev](https://hub.docker.com/u/magyodev)
* Email: [magyodev@gmail.com](mailto:magyodev@gmail.com)

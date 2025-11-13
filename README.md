# **Módulo 1 — API de Pessoas (Komfort Chain)**

O **Módulo 1** da suíte **Komfort Chain** implementa uma API REST completa para **gestão de pessoas**, incluindo CRUD, paginação, validação e persistência utilizando **PostgreSQL**.
O serviço segue rigorosamente os princípios de **Clean Architecture**, **SOLID** e boas práticas corporativas de desenvolvimento, adicionando **observabilidade centralizada (Graylog)** e integração com pipeline de qualidade (**SonarCloud**, **OWASP Dependency-Check** e **Docker Build Automation**).

---

## **Status do Projeto**

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)
[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Docker Hub](https://img.shields.io/badge/DockerHub-magyodev/api--pessoas-blue)](https://hub.docker.com/repository/docker/magyodev/api-pessoas)

---

## **Tecnologias Utilizadas**

| Categoria            | Ferramenta / Tecnologia              |
| -------------------- | ------------------------------------ |
| **Linguagem**        | Java 21                              |
| **Framework**        | Spring Boot 3.5.7                    |
| **Banco de Dados**   | PostgreSQL 16                        |
| **Logs**             | Logback GELF → Graylog 5.2           |
| **Build**            | Maven Wrapper (`mvnw`)               |
| **Testes**           | JUnit 5 + Spring Boot Test + JaCoCo  |
| **Análise Estática** | SonarCloud + OWASP Dependency Check  |
| **Containerização**  | Docker e Docker Compose              |
| **Arquitetura**      | Clean Architecture / SOLID / RESTful |

---

## **Arquitetura**

A aplicação segue uma arquitetura limpa dividida em camadas independentes:

```
Controller → Service → Repository → Domain
```

Para observabilidade, logs estruturados são enviados via GELF ao Graylog.
O PostgreSQL armazena os dados persistentes da API.

---

## **Estrutura do Projeto**

```bash
modulo1/
├── docker-compose.yml
├── Dockerfile
├── README.md
│
├── .github/workflows/
│   ├── full-ci.yml        # CI/CD completo: build, testes, análise e publicação
│   └── release.yml        # Automação de releases
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
    │
    └── src/main/resources/
        ├── application.yml
        └── logback-spring.xml
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
cd ..
```

### **3. Subir a stack completa**

```bash
docker compose up --build -d
```

### **Serviços esperados**

| Serviço        | Porta | Descrição                        |
| -------------- | ----- | -------------------------------- |
| API de Pessoas | 8081  | Endpoints REST                   |
| PostgreSQL     | 5432  | Persistência de dados de pessoas |
| Graylog        | 9009  | Logs centralizados               |
| SonarQube (*)  | 9000  | Análise estática (opcional)      |

---

## **Endpoints Principais**

| Método | Endpoint        | Descrição                         |
| ------ | --------------- | --------------------------------- |
| POST   | `/pessoas`      | Cria uma nova pessoa              |
| GET    | `/pessoas`      | Lista todas as pessoas (paginado) |
| GET    | `/pessoas/{id}` | Busca uma pessoa pelo ID          |
| PUT    | `/pessoas/{id}` | Atualiza uma pessoa               |
| DELETE | `/pessoas/{id}` | Remove uma pessoa                 |

### **Exemplo POST**

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

### **Exemplo GET Paginado**

```
GET http://localhost:8081/pessoas?page=0&size=5
```

### **Exemplo DELETE**

```
DELETE http://localhost:8081/pessoas/1
```

---

## **Pipeline CI/CD**

### **Workflow Principal — `full-ci.yml`**

Executado automaticamente:

1. Build e testes com Maven Wrapper
2. Análise estática com **SonarCloud**
3. Verificação de vulnerabilidades com **OWASP Dependency-Check**
4. Geração de relatórios (Surefire, JaCoCo)
5. Build e publicação da imagem Docker no **Docker Hub**

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)

---

### **Workflow de Release — `release.yml`**

Gera automaticamente:

* Releases versionadas no formato `vX.Y.Z`
* Notas de versão
* Imagens Docker tagueadas com a versão

---

## **Imagem Docker Oficial**

A imagem é publicada automaticamente pelo pipeline:

| Serviço        | Docker Hub                                                                            |
| -------------- | ------------------------------------------------------------------------------------- |
| API de Pessoas | [magyodev/api-pessoas](https://hub.docker.com/repository/docker/magyodev/api-pessoas) |

Tags disponíveis:

* `latest`
* `${run_number}` (CI/CD)
* `vX.Y.Z` (Releases)

---

## **Logs e Monitoramento**

A API envia logs estruturados via **GELF** para o **Graylog**, incluindo:

* Timestamp
* Nível de severidade
* Classe e método
* Mensagem
* Stacktrace (quando aplicável)

Visualizar em tempo real:

```bash
docker logs -f api-pessoas
```

---

## **Diagrama Simplificado**

```
┌────────────┐       ┌──────────────────────┐       ┌───────────────┐
│   Cliente  │ ───▶  │   API de Pessoas     │ ───▶  │   PostgreSQL  │
└────────────┘       └──────────────────────┘       └───────────────┘
                           │
                           ▼
                    ┌────────────┐
                    │  Graylog   │
                    └────────────┘
```

---

## **Contribuição**

1. Faça um fork do repositório;
2. Crie uma branch: `feature/nova-funcionalidade`;
3. Utilize commits semânticos;
4. Envie um Pull Request para a branch `main`.

---

## **Autor**

**Alan de Lima Silva (MagyoDev)**
* [GitHub](https://github.com/MagyoDev) 
* [Docker Hub](https://hub.docker.com/u/magyodev) 
* [E-mail](mailto:magyodev@gmail.com)


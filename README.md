# **Módulo 1 — API de Pessoas (Komfort Chain)**

O **Módulo 1** marca o início da suíte **Komfort Chain**, trazendo uma API REST para cadastro, consulta, atualização e remoção de pessoas.
O módulo foi desenvolvido com foco em **organização por camadas**, **baixa complexidade**, **testabilidade** e **estrutura previsível**, seguindo princípios de **Clean Architecture** e **SOLID**.

A API oferece paginação, filtragem automática de registros ativos, integração com PostgreSQL, logs estruturados via Graylog e pipelines completos de CI/CD com SonarCloud e OWASP Dependency-Check.

---

## **Status do Projeto**

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)
[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Docker Hub](https://img.shields.io/badge/DockerHub-magyodev%2Fapi--pessoas-blue)](https://hub.docker.com/repository/docker/magyodev/api-pessoas)
![Java](https://img.shields.io/badge/Java-21-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-brightgreen)

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

A API segue um fluxo simples, limpo e alinhado ao padrão utilizado em toda a suíte:

```
Controller → Service → Repository → Domain
```

### Como isso se relaciona com Clean Architecture?

* **Camada de apresentação (Controller)** não contém lógica de negócio.
* **Camada de aplicação (Service)** concentra regras e casos de uso.
* **Camada de infraestrutura (Repository)** é apenas acesso ao banco.
* **Camada de domínio (Model)** não depende de nada externo.

### Como isso se relaciona com SOLID?

* **SRP**: cada classe tem uma responsabilidade objetiva.
* **OCP**: serviços podem ser estendidos sem alterar controladores.
* **DIP**: serviço depende de **interface** (repositório), não da implementação do banco.

---

## **Estrutura do Projeto**

```
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
    │   │   ├── dto/PessoaDTO.java
    │   │   └── service/PessoaService.java
    │   │
    │   ├── domain/
    │   │   └── Pessoa.java
    │   │
    │   ├── infrastructure/
    │   │   └── persistence/repository/PessoaRepository.java
    │   │
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

## **Explicação Pasta por Pasta — e por que esses nomes existem**
---

### **1. application/**

Responsável por orquestrar regras, validações e operações de uso da API.

#### **dto/PessoaDTO.java**

* Representa os dados que entram e saem pela API.
* Evita expor diretamente a entidade `Pessoa`.
* Segue o princípio **DTO como fronteira da aplicação**.

#### **service/PessoaService.java**

* Contém regras de negócio e operações principais.
* Isola detalhes do banco e da API.
* Reflete o conceito de **use case** dentro do Clean Architecture.

---

### **2. domain/**

A parte mais pura da aplicação.

#### **Pessoa.java**

* É o modelo de domínio.
* Não depende de frameworks.
* Representa o “contrato real” da entidade.
* Segue o princípio **Entities** do Clean Architecture.

---

### **3. infrastructure/**

Interações técnicas e externas.

#### **persistence/repository/PessoaRepository.java**

* Interface Spring Data que acessa o banco.
* É uma implementação de “detalhe técnico”, não de negócio.
* Segue o princípio **DIP — dependência em abstrações**.

---

### **4. presentation/**

Interface HTTP da aplicação.

#### **controller/PessoaController.java**

* Define os endpoints.
* Não contém lógica de negócio.
* Apenas delega ao serviço.
* Mantém o padrão REST limpo e previsível.

#### **handler/GlobalExceptionHandler.java**

* Centraliza o tratamento de erros.
* Retorna respostas padronizadas.
* Evita duplicação no controller.

#### **mapper/PessoaMapper.java**

* Converte entre `Pessoa` ↔ `PessoaDTO`.
* Mantém **baixa acoplagem** entre as camadas.

---

### **5. src/main/resources/**

Arquivos essenciais para execução.

#### **application.yml**

* Configurações da aplicação.
* Porta, PostgreSQL, profiles etc.

#### **logback-spring.xml**

* Configuração de logs estruturados.
* Integração com Graylog via GELF.

---

### **6. Arquivos de build**

#### **pom.xml**

* Declara dependências e plugins.
* Configura JaCoCo e Sonar.

#### **mvnw / mvnw.cmd**

* Garante mesma versão do Maven em qualquer máquina.

---

### **7. Infraestrutura da raiz**

#### **docker-compose.yml**

* Sobe API + PostgreSQL + Graylog.

#### **Dockerfile**

* Gera a imagem final usada no CI/CD.

#### **workflows/**

* full-ci: testes, análise, dependências, build, push.
* release: gera changelog + publica release + imagens versionadas.

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

### Subir a stack

```bash
docker compose up -d --build
```

---

## **Endpoints Principais**

| Método | Rota          | Ação                                   |
| ------ | ------------- | -------------------------------------- |
| POST   | /pessoas      | Criar pessoa                           |
| GET    | /pessoas      | Listar (paginado e filtrado por ativo) |
| GET    | /pessoas/{id} | Buscar por ID                          |
| PUT    | /pessoas/{id} | Atualizar dados                        |
| DELETE | /pessoas/{id} | Remover                                |

---

## **CI/CD — Workflows**

### full-ci.yml

Executado a cada push/pull request:

* Build + Testes
* SonarCloud
* OWASP Dependency-Check
* Publicação da imagem no Docker Hub
* Upload de relatórios (JaCoCo e Surefire)

### release.yml

Executado ao criar uma tag SemVer:

* build completo
* changelog automático
* release no GitHub
* imagens versionadas (`vX.Y.Z`)

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

## **Contribuição**

1. Faça um fork
2. Crie uma branch
3. Commit semântico
4. Abra um PR para `main`

---

## **Autor**

**Alan de Lima Silva (MagyoDev)**
* GitHub: [https://github.com/MagyoDev](https://github.com/MagyoDev)
* Docker Hub: [https://hub.docker.com/u/magyodev](https://hub.docker.com/u/magyodev)
* E-mail: [magyodev@gmail.com](mailto:magyodev@gmail.com)


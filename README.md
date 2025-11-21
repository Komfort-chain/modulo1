# **Módulo 1 — API de Pessoas (Komfort Chain)**

O **Módulo 1** da suíte **Komfort Chain** implementa uma API REST voltada à gestão de pessoas, oferecendo operações de CRUD, paginação e persistência em banco de dados PostgreSQL.
A estrutura interna foi organizada para permitir manutenção simples, isolamento entre camadas e clareza no fluxo da aplicação. As escolhas de nomes, pastas e workflows seguem práticas que facilitam leitura, teste e evolução do código.

O projeto aplica princípios de **Clean Architecture**, **SOLID** e organização modular, mantendo observabilidade via **Graylog** e garantindo qualidade contínua com **SonarCloud**, **OWASP Dependency-Check** e automação de builds Docker.

---

## **Status do Projeto**

[![Full CI/CD](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/full-ci.yml)
[![Release](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml/badge.svg)](https://github.com/Komfort-chain/modulo1/actions/workflows/release.yml)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=Komfort-chain_modulo1\&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Komfort-chain_modulo1)
[![Docker Hub](https://img.shields.io/badge/DockerHub-magyodev/api--pessoas-blue)](https://hub.docker.com/repository/docker/magyodev/api-pessoas)

---

## **Tecnologias Utilizadas**

| Categoria        | Ferramenta / Tecnologia              |
| ---------------- | ------------------------------------ |
| Linguagem        | Java 21                              |
| Framework        | Spring Boot 3.5.7                    |
| Banco de Dados   | PostgreSQL 16                        |
| Logs             | Logback GELF → Graylog 5.2           |
| Build            | Maven Wrapper (mvnw)                 |
| Testes           | JUnit 5 + Spring Boot Test + JaCoCo  |
| Análise Estática | SonarCloud + OWASP Dependency Check  |
| Containerização  | Docker e Docker Compose              |
| Arquitetura      | Clean Architecture / SOLID / RESTful |

---

## **Arquitetura**

A arquitetura foi organizada para separar de forma clara as responsabilidades de cada camada. Essa divisão facilita testes, permite substituição de componentes e reduz acoplamento.

```
Controller → Service → Repository → Domain
```

**Motivos da escolha dessa arquitetura:**

* Mantém o código previsível e fácil de navegar.
* Isola detalhes de infraestrutura (como JPA e PostgreSQL).
* Permite alterar regras de negócio no serviço sem impactar o controller.
* A camada domain representa apenas o modelo real, sem dependências externas.
* A camada de apresentação (presentation) concentra tudo que está ligado à API HTTP.

Além disso, a observabilidade é tratada desde o início, com logs estruturados enviados para o Graylog.

---

## **Organização das Pastas e Justificativa da Estrutura**

A estrutura foi planejada para tornar o projeto fácil de manter e escalar. Cada diretório cumpre um papel específico e evita misturar responsabilidades.

### **1. Raiz do projeto (`modulo1/`)**

Contém arquivos que se referem ao módulo como um todo:

* `docker-compose.yml`: sobe banco, Graylog e a aplicação.
* `Dockerfile`: define a imagem da API para execução ou publicação.
* `.github/workflows`: pipelines de automação.
* `README.md`: documentação do módulo.

**Motivo da escolha:** deixar claro quais arquivos pertencem à infraestrutura e quais pertencem ao código Java.

---

### **2. Diretório principal da aplicação (`pessoas/`)**

Separa o código Java do restante do repositório, mantendo o módulo isolado.

Contém:

* `pom.xml`
* `mvnw`
* código-fonte em `src/main/java`
* configurações em `src/main/resources`

**Motivo da estrutura separada:** manter cada módulo da suíte Komfort Chain independente, permitindo que cada um tenha seu próprio ciclo de build, Dockerfile e workflows.

---

### **3. Divisão por camadas dentro de `com/cabos/pessoas/`**

```
application/
domain/
infrastructure/
presentation/
```

#### **application/**

Contém serviços e DTOs.

Motivo:

* Regras operacionais da API devem ficar isoladas da camada HTTP.
* DTOs evitam expor entidades diretamente.

#### **domain/**

Contém a entidade `Pessoa`.

Motivo:

* Reflete o modelo de negócio real, sem dependências de frameworks.

#### **infrastructure/**

Contém repositórios (Spring Data).

Motivo:

* Repositório é detalhe técnico; deve ser intercambiável.

#### **presentation/**

Contém controllers, handlers e mappers.

Motivo:

* Centralizar toda a interface HTTP da aplicação.
* Tratar erros em um único ponto (`GlobalExceptionHandler`).
* Mapper explícito deixa o código previsível.

---

## **Por que esses nomes foram escolhidos?**

Os nomes seguem convenções do ecossistema Java e Spring Boot:

* **Pessoa**: representa o modelo do domínio.
* **PessoaDTO**: transporte seguro e controlado de dados na API.
* **PessoaService**: centraliza operações e regras.
* **PessoaRepository**: padrão Spring Data para acessos ao banco.
* **PessoaMapper**: conversão explícita entre camadas.
* **GlobalExceptionHandler**: é uma boa prática padronizar erros num único lugar.
* **full-ci.yml**: indica pipeline completo.
* **release.yml**: dedicado exclusivamente a criação de releases.

Essa padronização facilita que qualquer desenvolvedor entenda o projeto rapidamente.

---

## **Estrutura do Projeto**

```bash
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

---

## **Serviços Esperados**

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

---

## **Pipeline CI/CD**

A automação foi dividida em dois workflows para separar responsabilidades.

### **Workflow Principal — `full-ci.yml`**

Executado em **push** e **pull requests**.
Responsável por:

1. Compilação e testes
2. Análise estática (SonarCloud)
3. Verificação de vulnerabilidades (OWASP)
4. Relatórios de cobertura
5. Build e push da imagem Docker

Motivo da separação:

> O CI precisa rodar continuamente durante o desenvolvimento, garantindo qualidade a cada alteração.

---

### **Workflow de Release — `release.yml`**

Executado apenas quando uma tag no formato `vX.Y.Z` é criada.
Responsável por:

* Gerar changelog
* Criar release no GitHub
* Anexar artefato .jar
* Criar imagem Docker versionada

Motivo da separação:

> Releases representam versões estáveis e não devem ocorrer automaticamente em cada commit.

---

## **Imagem Docker Oficial**

| Serviço        | Docker Hub                                                                                                                     |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| API de Pessoas | [https://hub.docker.com/repository/docker/magyodev/api-pessoas](https://hub.docker.com/repository/docker/magyodev/api-pessoas) |

Tags:

* `latest`
* `${run_number}`
* `vX.Y.Z`

---

## **Logs e Monitoramento**

A aplicação utiliza logs estruturados via GELF, que são enviados ao Graylog.
Esse formato facilita consultas, auditoria e rastreamento de problemas.

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

1. Faça um fork do repositório.
2. Crie uma branch: `feature/nova-funcionalidade`.
3. Utilize commits semânticos.
4. Abra um Pull Request para `main`.

---

## **Autor**

**Alan de Lima Silva (MagyoDev)**
* GitHub: [https://github.com/MagyoDev](https://github.com/MagyoDev)
* Docker Hub: [https://hub.docker.com/u/magyodev](https://hub.docker.com/u/magyodev)
* E-mail: [magyodev@gmail.com](mailto:magyodev@gmail.com)
  


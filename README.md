# Módulo 1 — API de Pessoas (Komfort Chain)

O **Módulo 1** é uma API REST de gestão de pessoas desenvolvida como parte do projeto **Komfort Chain**, uma suíte modular voltada à automação e integração de sistemas distribuídos.
O objetivo deste módulo é implementar um CRUD de Pessoa com persistência em banco de dados, logs centralizados e arquitetura limpa.

---

## Tecnologias Utilizadas

| Categoria         | Tecnologia                     |
| ----------------- | ------------------------------ |
| Linguagem         | Java 21                        |
| Framework         | Spring Boot 3.5.7              |
| Banco de Dados    | PostgreSQL 16                  |
| Observabilidade   | Graylog 5.2 (via Logback GELF) |
| Build             | Maven                          |
| Containerização   | Docker e Docker Compose        |
| Testes            | JUnit + Spring Boot Test       |
| Análise de Código | SonarQube 25.11                |
| Arquitetura       | Clean Architecture + SOLID     |

---

## Estrutura do Projeto

```
pessoas/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/cabos/pessoas/
│   │   │   ├── application/
│   │   │   │   ├── dto/
│   │   │   │   │   └── PessoaDTO.java
│   │   │   │   └── service/
│   │   │   │       └── PessoaService.java
│   │   │   ├── domain/
│   │   │   │   └── Pessoa.java
│   │   │   ├── infrastructure/
│   │   │   │   └── persistence/repository/
│   │   │   │       └── PessoaRepository.java
│   │   │   └── presentation/
│   │   │       ├── controller/
│   │   │       │   └── PessoaController.java
│   │   │       ├── handler/
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       └── mapper/
│   │   │           └── PessoaMapper.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── logback-spring.xml
│   └── test/
│       └── java/com/cabos/pessoas/
│           └── PessoasApplicationTests.java
```

Fluxo arquitetural:

```
Controller → Service → Repository → Domain
```

---

## Como Executar

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/komfortchain-modulo1.git
cd komfortchain-modulo1
```

### 2. Buildar e executar a aplicação com Docker

```bash
cd pessoas
.\mvnw clean package -DskipTests -U
cd ..
docker compose build app
docker compose up -d app
```

Esses comandos:

* Compilam e empacotam o projeto em `pessoas/target/app.jar`;
* Constroem a imagem Docker;
* Sobem o container da aplicação conectado aos serviços (PostgreSQL, Graylog, etc.).

---

## Serviços

| Serviço        | Porta | Descrição                   |
| -------------- | ----- | --------------------------- |
| API de Pessoas | 8081  | Endpoints REST              |
| Graylog        | 9009  | Central de logs             |
| PostgreSQL     | 5432  | Banco de dados da aplicação |
| MongoDB        | 27017 | Base do Graylog             |
| OpenSearch     | 9200  | Engine de busca Graylog     |
| SonarQube      | 9000  | Análise estática de código  |

---

## Descrição do Projeto

A API realiza operações CRUD sobre entidades de Pessoa, armazenando os dados em um banco relacional.
Somente registros com o atributo `ativo = true` são retornados, e as respostas são paginadas (10 itens por página).
Os logs da aplicação são enviados para o Graylog para monitoramento centralizado.

---

**Autor:** Alan de Lima Silva (MagyoDev)
- **GitHub:** [https://github.com/MagyoDev](https://github.com/MagyoDev)
- **E-mail:** [magyodev@gmail.com](mailto:magyodev@gmail.com)


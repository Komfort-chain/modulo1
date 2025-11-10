# 🧩 Módulo 1 — API de Pessoas (Komfort Chain)

O **Módulo 1** é uma API REST de gestão de pessoas desenvolvida como parte do projeto **Komfort Chain**, uma suíte modular voltada à automação e integração de sistemas distribuídos.  
O objetivo deste módulo é implementar um CRUD de Pessoa com persistência em banco de dados, logs centralizados e arquitetura limpa.

## 🧾 Descrição do Projeto

A API realiza operações CRUD sobre entidades de Pessoa, armazenando os dados em um banco relacional.
Somente registros com o atributo `ativo = true` são retornados, e as respostas são paginadas (10 itens por página).
Os logs da aplicação são enviados para o Graylog para monitoramento centralizado.

---

## 🧠 Tecnologias Utilizadas

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

## 🗂️ Estrutura do Projeto

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

````

---

## ⚙️ Como Executar

### 1. Clonar o repositório

```bash
git clone https://github.com/Komfort-chain/modulo1.git
cd modulo1
````

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

## 🧰 Serviços

| Serviço        | Porta | Descrição                   |
| -------------- | ----- | --------------------------- |
| API de Pessoas | 8081  | Endpoints REST              |
| Graylog        | 9009  | Central de logs             |
| PostgreSQL     | 5432  | Banco de dados da aplicação |
| MongoDB        | 27017 | Base do Graylog             |
| OpenSearch     | 9200  | Engine de busca Graylog     |
| SonarQube      | 9000  | Análise estática de código  |

---

## 🧪 Testes da API (via Postman)

### 🔹 Endpoints Principais

| Método     | Endpoint        | Descrição                         |
| ---------- | --------------- | --------------------------------- |
| **POST**   | `/pessoas`      | Cria uma nova pessoa              |
| **GET**    | `/pessoas`      | Lista todas as pessoas (paginado) |
| **GET**    | `/pessoas/{id}` | Busca uma pessoa pelo ID          |
| **PUT**    | `/pessoas/{id}` | Atualiza uma pessoa               |
| **DELETE** | `/pessoas/{id}` | Remove uma pessoa                 |

---

### 🔹 Exemplo de Criação (POST)

**URL:**

```
http://localhost:8081/pessoas
```

**Header:**

```
Content-Type: application/json
```

**Body (JSON):**

```json
{
  "nome": "Rita de Cássia Silva",
  "ativo": true,
  "dtNascimento": "1994-05-10"
}
```

---

### 🔹 Exemplo de Atualização (PUT)

**URL:**

```
http://localhost:8081/pessoas/1
```

**Body (JSON):**

```json
{
  "nome": "Rita de Cássia",
  "ativo": true,
  "dtNascimento": "1994-05-10"
}
```

> ⚠️ Caso receba o erro `Required request body is missing`, verifique se:
>
> * O `Content-Type` é `application/json`;
> * O corpo (Body → raw → JSON) está preenchido corretamente.

---

### 🔹 Listagem Paginada

**Exemplo:**

```
GET http://localhost:8081/pessoas?page=0&size=5
GET http://localhost:8081/pessoas?page=1&size=5
```

**Parâmetros:**

| Parâmetro | Descrição                          | Exemplo            |
| --------- | ---------------------------------- | ------------------ |
| `page`    | Número da página (começa em 0)     | `page=0`, `page=1` |
| `size`    | Quantidade de registros por página | `size=5`           |
| `sort`    | Campo de ordenação (opcional)      | `sort=nome,asc`    |

**Retorno esperado:**

```json
{
  "content": [ /* lista de pessoas */ ],
  "totalElements": 15,
  "totalPages": 3,
  "number": 0,
  "size": 5
}
```

---

### 🔹 Exemplo de Remoção

```
DELETE http://localhost:8081/pessoas/1
```

Retorna `204 No Content` em caso de sucesso.

---

## 🧭 Paginação e Ordenação

**Paginação:**

```
GET /pessoas?page=0&size=5
```

**Exemplo de retorno paginado:**

```json
{
  "content": [
    { "id": 1, "nome": "Rita", "dtNascimento": "1994-05-10", "ativo": true },
    { "id": 2, "nome": "Alan", "dtNascimento": "1997-11-12", "ativo": true }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5
  },
  "totalPages": 2,
  "totalElements": 10
}
```

---

## 👨‍💻 Autor

**Alan de Lima Silva (MagyoDev)**

* **GitHub:** [https://github.com/MagyoDev](https://github.com/MagyoDev)
* **E-mail:** [magyodev@gmail.com](mailto:magyodev@gmail.com)

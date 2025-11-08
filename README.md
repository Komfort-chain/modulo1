# 🧩 Módulo 1 — API de Pessoas (Komfort Chain)

O **Módulo 1** é uma API de gestão de pessoas desenvolvida como parte do projeto **Komfort Chain**, uma suíte modular voltada à automação e integração de sistemas distribuídos.  
Este serviço implementa princípios de **Clean Architecture** e **SOLID**, com logs centralizados no **Graylog** e banco relacional **PostgreSQL** em container Docker.

---

## 🚀 Tecnologias Utilizadas

| Categoria | Tecnologia |
|------------|-------------|
| Linguagem  | Java 21 |
| Framework  | Spring Boot 3.5.7 |
| Banco de Dados | PostgreSQL 16 |
| Observabilidade | Graylog 5.2 (via Logback GELF) |
| Build | Maven |
| Containerização | Docker e Docker Compose |
| Testes | JUnit + Spring Boot Test |
| Arquitetura | Clean Architecture + SOLID |

---

## 🧱 Estrutura do Projeto

```

modulo1/
├── docker-compose.yml
├── Dockerfile
└── pessoas/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/cabos/pessoas/
│   │   │   ├── PessoasApplication.java       # Classe principal
│   │   │   ├── domain/                      # Entidades (camada de domínio)
│   │   │   ├── repo/                        # Repositórios (persistence)
│   │   │   ├── service/                     # Regras de negócio (use cases)
│   │   │   └── web/                         # Controladores REST e DTOs
│   │   │       ├── dto/
│   │   │       ├── handler/                 # GlobalExceptionHandler
│   │   │       └── mapper/                  # Conversores DTO ↔ Entidade
│   │   └── resources/
│   │       ├── application.yml              # Configurações do Spring
│   │       └── logback-spring.xml           # Configuração de logs (Graylog)
│   └── test/                                # Testes automatizados
└── target/                                  # Artefatos de build

```

**Fluxo arquitetural:**
```

Controller → Service → Repository → Domain

````

---

## 🧠 Padrões Implementados

- ✅ **SOLID Principles**
- ✅ **Clean Architecture**
- ✅ **DTO e Mapper** (separa domínio e camada de exposição)
- ✅ **Handler global** (captura exceções personalizadas)
- ✅ **Logs estruturados no Graylog**
- ✅ **Testes de integração e unidade**

---

## ⚙️ Como Executar

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/seu-usuario/komfortchain-modulo1.git
cd komfortchain-modulo1
````

### 2️⃣ Buildar e executar a aplicação com Docker

```bash
cd pessoas
.\mvnw clean package -DskipTests -U
cd ..
docker compose build app
docker compose up -d app
```

Esses comandos:

* Compilam e empacotam o projeto em `pessoas/target/app.jar`
* Constroem a imagem Docker do módulo
* Sobem o container da aplicação conectado aos serviços (Postgres, Graylog, etc.)

---

### 3️⃣ Verificar os serviços

| Serviço        | Porta   | Descrição                   |
| -------------- | ------- | --------------------------- |
| API de Pessoas | `8081`  | Endpoints REST              |
| Graylog        | `9009`  | Central de logs             |
| PostgreSQL     | `5432`  | Banco de dados da aplicação |
| MongoDB        | `27017` | Base do Graylog             |
| OpenSearch     | `9200`  | Engine de busca do Graylog  |

---

## 🧩 Endpoints Principais

| Método   | Endpoint        | Descrição                       |
| -------- | --------------- | ------------------------------- |
| `GET`    | `/pessoas`      | Lista pessoas ativas (paginado) |
| `GET`    | `/pessoas/{id}` | Busca uma pessoa pelo ID        |
| `POST`   | `/pessoas`      | Cria uma nova pessoa            |
| `PUT`    | `/pessoas/{id}` | Atualiza uma pessoa existente   |
| `DELETE` | `/pessoas/{id}` | Remove uma pessoa               |

### 📦 Exemplo de criação:

```bash
curl -X POST http://localhost:8081/pessoas \
-H "Content-Type: application/json" \
-d '{"nome": "Alan Silva", "dtNascimento": "1995-05-20", "ativo": true}'
```

---

## 🪵 Logs e Observabilidade

Os logs da aplicação são enviados automaticamente para o **Graylog** via **Logback GELF**, contendo informações como:

| Campo       | Descrição                          |
| ----------- | ---------------------------------- |
| `app`       | Nome do módulo (`modulo1-pessoas`) |
| `source`    | Container de origem                |
| `timestamp` | Data/hora do evento                |
| `message`   | Mensagem de log da aplicação       |

Acesse o painel do Graylog:
👉 [http://localhost:9009](http://localhost:9009)

---

## 🌐 Variáveis de Ambiente

| Variável                     | Descrição                | Valor padrão                                |
| ---------------------------- | ------------------------ | ------------------------------------------- |
| `GRAYLOG_HOST`               | Host do servidor Graylog | `graylog`                                   |
| `GRAYLOG_PORT`               | Porta UDP do Graylog     | `12201`                                     |
| `SPRING_DATASOURCE_URL`      | URL JDBC do Postgres     | `jdbc:postgresql://pessoas_db:5432/pessoas` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do Postgres      | `pessoas`                                   |
| `SPRING_DATASOURCE_PASSWORD` | Senha do Postgres        | `pessoas`                                   |

---

## 🧪 Testes

Para executar os testes automatizados:

```bash
cd pessoas
mvn test
```

Após a execução, consulte os relatórios:

```
pessoas/target/surefire-reports/
```

---

## 🧑‍💻 Autor

**Alan de Lima Silva (MagyoDev)**
- 📧 E-mail: [magyodev@gmail.com](mailto:magyodev@gmail.com)
- 🌐 GitHub: [https://github.com/MagyoDev](https://github.com/MagyoDev)

---

## 🧾 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).


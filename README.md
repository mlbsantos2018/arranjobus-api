# Transport API

API para gestão de transporte de congressos e assembleias.

## 🏗️ Arquitetura

- **Java 21**
- **Spring Boot 3.5.x**
- **MongoDB Atlas**
- **Spring Security 6 + JWT**
- **Arquitetura Hexagonal**
- **Clean Architecture**
- **DDD (Domain-Driven Design)**

## 📋 Requisitos

- Java 21+
- Maven 3.8+
- Docker (opcional, para MongoDB)
- MongoDB Atlas (ou MongoDB local)

## 🚀 Como Executar

### 1. Preparar o Ambiente

#### Opção A: Com Docker

```bash
docker-compose up -d
```

Isso iniciará um MongoDB local na porta 27017.

#### Opção B: Com MongoDB Atlas

1. Crie uma conta em [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
2. Crie um cluster e obtenha a URI de conexão

### 2. Configurar Variáveis de Ambiente

Defina as seguintes variáveis de ambiente:

```bash
# Para local (docker)
export MONGODB_URI="mongodb://user:password@localhost:27017/transport-db"

# Para produção (Atlas)
export MONGODB_URI="mongodb+srv://user:password@cluster.mongodb.net/transport-db?retryWrites=true&w=majority"

# JWT Secret (recomendado usar um secret mais seguro em produção)
export JWT_SECRET="sua-chave-secreta-super-segura"

# JWT Expiration em millisegundos (86400000 = 24 horas)
export JWT_EXPIRATION="86400000"
```

### 3. Compilar o Projeto

```bash
mvn clean install
```

### 4. Executar a Aplicação

```bash
mvn spring-boot:run
```

Ou diretamente com Java:

```bash
java -jar target/transport-api-0.0.1-SNAPSHOT.jar
```

### 5. Acessar a Aplicação

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

## 📚 Documentação da API

Todos os endpoints estão documentados no Swagger. Acesse http://localhost:8080/swagger-ui.html após iniciar a aplicação.

Também foi criada uma documentação complementar com visão de negócio e técnica:

- [API_DOCUMENTACAO_NEGOCIO_TECNICA.md](API_DOCUMENTACAO_NEGOCIO_TECNICA.md)

### Autenticação

1. Faça login em `POST /api/v1/auth/login` com:
```json
{
  "username": "seu_username",
  "password": "sua_senha"
}
```

2. Use o token JWT retornado no header `Authorization: Bearer <token>` para fazer requisições autenticadas.

## 🔑 Endpoints Principais

### Autenticação
- `POST /api/v1/auth/login` - Login
- `GET /api/v1/auth/me` - Usuário atual

### Pessoas
- `POST /api/v1/people` - Criar pessoa
- `GET /api/v1/people/{id}` - Obter pessoa
- `GET /api/v1/people` - Listar pessoas
- `GET /api/v1/people/search?termo=X` - Pesquisar pessoas
- `PUT /api/v1/people/{id}` - Atualizar pessoa
- `DELETE /api/v1/people/{id}` - Excluir pessoa

### Eventos
- `POST /api/v1/events` - Criar evento
- `GET /api/v1/events/{id}` - Obter evento
- `GET /api/v1/events` - Listar eventos
- `PUT /api/v1/events/{id}` - Atualizar evento
- `PATCH /api/v1/events/{id}/close` - Encerrar evento
- `PATCH /api/v1/events/{id}/archive` - Arquivar evento

### Participações
- `POST /api/v1/participations` - Adicionar participação
- `DELETE /api/v1/participations/{id}` - Remover participação
- `GET /api/v1/events/{eventId}/participants` - Listar participantes
- `GET /api/v1/events/{eventId}/participants/day/{dia}` - Listar por dia

### Pagamentos
- `POST /api/v1/payments` - Criar pagamento
- `GET /api/v1/payments/{participationId}` - Obter pagamento
- `GET /api/v1/payments/{paymentId}/history` - Histórico de pagamento
- `GET /api/v1/payments/summary` - Resumo de pagamentos

### Relatórios
- `GET /api/v1/reports/events/{eventId}/friday` - Relatório sexta
- `GET /api/v1/reports/events/{eventId}/saturday` - Relatório sábado
- `GET /api/v1/reports/events/{eventId}/sunday` - Relatório domingo
- `GET /api/v1/reports/events/{eventId}/excel?dia=X` - Exportar Excel

### Usuários
- `POST /api/v1/users` - Criar usuário
- `GET /api/v1/users` - Listar usuários
- `PATCH /api/v1/users/{id}/role` - Alterar role
- `PATCH /api/v1/users/{id}/status` - Alterar status

### Crianças de Colo
- `POST /api/v1/lap-children` - Registrar criança
- `GET /api/v1/lap-children/{id}` - Obter criança
- `GET /api/v1/lap-children` - Listar crianças
- `DELETE /api/v1/lap-children/{id}` - Excluir criança

## 📦 Estrutura de Pacotes

```
com.transporte
├── domain
│   ├── model
│   ├── enums
│   ├── exception
│   └── ports
│       ├── in
│       └── out
├── application
│   ├── dto
│   │   ├── request
│   │   └── response
│   ├── mapper
│   ├── service
│   └── usecase
├── adapters
│   ├── in
│   │   └── rest
│   └── out
│       └── persistence
│           └── mongo
│               ├── document
│               ├── repository
│               ├── mapper
│               └── adapter
└── infrastructure
    ├── config
    ├── security
    └── exception
```

## 🔐 Segurança

- JWT Stateless Authentication
- BCrypt Password Encryption
- Role-Based Access Control (RBAC)
- Roles disponíveis: ADMIN, ORGANIZADOR, VISUALIZADOR

## 💾 Banco de Dados

MongoDB com as seguintes coleções:

- `people` - Pessoas
- `events` - Eventos
- `participations` - Participações
- `payments` - Pagamentos
- `users` - Usuários
- `lap_children` - Crianças de Colo

## ✅ Regras de Negócio Implementadas

- ✓ Não permite documento duplicado
- ✓ Não permite participante duplicado no mesmo evento
- ✓ Não permite participação em evento encerrado
- ✓ Controla vagas por dia
- ✓ Bloqueia quando lotado
- ✓ Validação de idade de criança de colo
- ✓ Cálculo automático de status de pagamento
- ✓ Relatórios por dia

## 📋 Enums

### TipoDocumento
- CPF
- RG
- PASSAPORTE
- OUTRO

### TipoEvento
- ASSEMBLEIA
- CONGRESSO

### DiaEvento
- SEXTA
- SABADO
- DOMINGO

### FormaPagamento
- PIX
- DINHEIRO
- OUTROS

### StatusEvento
- ATIVO
- ENCERRADO
- ARQUIVADO

### StatusParticipacao
- CONFIRMADO
- CANCELADO
- LISTA_ESPERA

### StatusPagamento
- PENDENTE
- PARCIAL
- PAGO

### Role
- ADMIN
- ORGANIZADOR
- VISUALIZADOR

## 🛠️ Ferramentas Utilizadas

- **Lombok** - Redução de boilerplate
- **OpenAPI/Swagger** - Documentação interativa
- **Apache POI** - Geração de Excel
- **iText** - Geração de PDF (preparado para uso futuro)
- **JWT (JJWT)** - Autenticação JWT

## 📝 Exemplo de Uso

### 1. Criar um Usuário

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "organizador1",
    "password": "senha123",
    "role": "ORGANIZADOR"
  }'
```

### 2. Fazer Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "organizador1",
    "password": "senha123"
  }'
```

### 3. Criar um Evento

```bash
curl -X POST http://localhost:8080/api/v1/events \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nome": "Congresso Nacional 2024",
    "tipo": "CONGRESSO",
    "valorPassagem": 150.00,
    "vagasTotais": 100,
    "limiteIdadeCriancaColo": 3,
    "temas": ["Tecnologia", "Inovação"]
  }'
```

## 🐛 Troubleshooting

### Erro de Conexão MongoDB
- Verifique se MongoDB está rodando
- Confira a URI de conexão nas variáveis de ambiente
- Verifique credenciais de acesso

### Erro de Port
- Porta 8080 já está em uso? Altere com `--server.port=8081`
- Porta 27017 já está em uso? Altere a porta do MongoDB

### Erro de Compilação
- Limpe o cache: `mvn clean`
- Atualize as dependências: `mvn dependency:resolve`

## 📞 Suporte

Para dúvidas ou problemas, consulte a documentação Swagger ou abra uma issue no repositório.

---


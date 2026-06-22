# ⚡ QUICK START - TRANSPORT API

## 🚀 Começar em 5 minutos

### 1️⃣ Pré-requisitos
- [x] Java 21+ instalado
- [x] Docker & Docker Compose instalado
- [x] Maven 3.8+ instalado

Verificar:
```bash
java -version
docker --version
mvn --version
```

---

### 2️⃣ Iniciar MongoDB (30 segundos)

Na pasta raiz do projeto:
```bash
docker-compose up -d
```

Verificar se está rodando:
```bash
docker-compose ps
```

---

### 3️⃣ Compilar Projeto (1 minuto)

```bash
mvn clean compile
```

---

### 4️⃣ Executar Aplicação (30 segundos)

```bash
mvn spring-boot:run
```

Aguarde até ver:
```
Started TransportApiApplication in X.XXX seconds
```

---

### 5️⃣ Testar (1 minuto)

Abra no navegador:
```
http://localhost:8080/swagger-ui.html
```

---

## 🔐 Primeiro Login

### 1. Criar Usuário (no Swagger)

Endpoint: `POST /api/v1/users`

Body:
```json
{
  "username": "admin1",
  "password": "admin123456",
  "role": "ADMIN"
}
```

Response: `201 Created`

---

### 2. Fazer Login (no Swagger)

Endpoint: `POST /api/v1/auth/login`

Body:
```json
{
  "username": "admin1",
  "password": "admin123456"
}
```

Response: 
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400000
}
```

**Copie o token!**

---

### 3. Usar Token no Swagger

1. Clique no botão **"Authorize"** (cadeado no topo)
2. Cole: `Bearer <seu_token>`
3. Clique "Authorize"
4. Agora todos os endpoints autenticados funcionarão!

---

## 📋 Testes Rápidos

### Criar Pessoa
```bash
curl -X POST http://localhost:8080/api/v1/people \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nomeCompleto": "João Silva",
    "tipoDocumento": "CPF",
    "numeroDocumento": "12345678901",
    "telefone": "(11) 98765-4321"
  }'
```

### Criar Evento
```bash
curl -X POST http://localhost:8080/api/v1/events \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nome": "Congresso 2024",
    "tipo": "CONGRESSO",
    "valorPassagem": 150.00,
    "vagasTotais": 100
  }'
```

### Listar Eventos (sem auth)
```bash
curl http://localhost:8080/api/v1/events
```

---

## 📊 Testes Completos

Para testes mais detalhados, veja:
- [`GUIA_DE_TESTES.md`](GUIA_DE_TESTES.md) - 20+ casos de teste
- [`test-api.sh`](test-api.sh) - Script interativo
- [`Transport-API.postman_collection.json`](Transport-API.postman_collection.json) - Collection Postman

---

## 📚 Documentação

| Documento | Descrição |
|-----------|-----------|
| [`README.md`](README.md) | Setup, endpoints, troubleshooting |
| [`RESUMO_EXECUTIVO.md`](RESUMO_EXECUTIVO.md) | Visão completa do projeto |
| [`ARQUIVOS_CRIADOS.md`](ARQUIVOS_CRIADOS.md) | Listagem de 132 arquivos Java |
| [`GUIA_DE_TESTES.md`](GUIA_DE_TESTES.md) | 20+ casos de teste com exemplos |

---

## 🐛 Troubleshooting Rápido

| Problema | Solução |
|----------|---------|
| MongoDB não inicia | `docker-compose down && docker-compose up -d` |
| Porta 8080 ocupada | `mvn spring-boot:run -Dserver.port=8081` |
| Token expirado | Faça login novamente |
| Erro 409 (Documento duplicado) | Use um documento único |
| Erro 401 (Sem autenticação) | Adicione header `Authorization: Bearer <token>` |

---

## 🎯 Casos de Uso Comuns

### Caso 1: Criar um Congresso

1. **Criar Evento** (POST /api/v1/events)
   ```json
   {
     "nome": "Congresso Nacional 2024",
     "tipo": "CONGRESSO",
     "valorPassagem": 150.00,
     "vagasTotais": 100,
     "limiteIdadeCriancaColo": 3
   }
   ```

2. **Adicionar Pessoas** (POST /api/v1/people)
   - Crie quantas pessoas forem necessárias

3. **Registrar Participações** (POST /api/v1/participations)
   ```json
   {
     "pessoaId": "id_da_pessoa",
     "eventoId": "id_do_evento",
     "dias": ["SEXTA", "SABADO"]
   }
   ```

4. **Registrar Pagamentos** (POST /api/v1/payments)
   ```json
   {
     "participacaoId": "id_da_participacao",
     "valorTotal": 300.00,
     "valorPago": 100.00,
     "formaPagamento": "PIX"
   }
   ```

5. **Gerar Relatório** (GET /api/v1/reports/events/{eventId}/excel?dia=SEXTA)
   - Exporta Excel com participantes por dia

---

### Caso 2: Gestão de Usuários

1. **Criar Usuário** (POST /api/v1/users)
   ```json
   {
     "username": "organizador1",
     "password": "senha123",
     "role": "ORGANIZADOR"
   }
   ```

2. **Alterar Role** (PATCH /api/v1/users/{id}/role?role=VISUALIZADOR)

3. **Desativar Usuário** (PATCH /api/v1/users/{id}/status?ativo=false)

---

## 🔑 Endpoints Principais

```
# Autenticação
POST   /api/v1/auth/login
GET    /api/v1/auth/me

# Pessoas
POST   /api/v1/people
GET    /api/v1/people
GET    /api/v1/people/{id}
GET    /api/v1/people/search?termo=João
PUT    /api/v1/people/{id}
DELETE /api/v1/people/{id}

# Eventos
POST   /api/v1/events
GET    /api/v1/events
GET    /api/v1/events/{id}
PUT    /api/v1/events/{id}
PATCH  /api/v1/events/{id}/close
PATCH  /api/v1/events/{id}/archive

# Participações
POST   /api/v1/participations
DELETE /api/v1/participations/{id}
GET    /api/v1/events/{eventId}/participants
GET    /api/v1/events/{eventId}/participants/day/SEXTA

# Pagamentos
POST   /api/v1/payments
GET    /api/v1/payments/{participationId}
GET    /api/v1/payments/summary

# Relatórios
GET    /api/v1/reports/events/{eventId}/friday
GET    /api/v1/reports/events/{eventId}/excel?dia=SEXTA

# Usuários
POST   /api/v1/users
GET    /api/v1/users
PATCH  /api/v1/users/{id}/role
PATCH  /api/v1/users/{id}/status

# Crianças
POST   /api/v1/lap-children
GET    /api/v1/lap-children
DELETE /api/v1/lap-children/{id}
```

---

## 💡 Dicas Importantes

1. **Antes de tudo**: Certifique-se que MongoDB está rodando (`docker-compose ps`)

2. **Autenticação**: Todos os endpoints exceto GET /api/v1/events requerem token JWT

3. **Documento Único**: Cada pessoa deve ter um documento único (CPF/RG/etc)

4. **Pagamento Automático**: Status de pagamento muda automaticamente:
   - PENDENTE (R$ 0,00)
   - PARCIAL (R$ > 0,00 e < total)
   - PAGO (R$ = total)

5. **Relatórios**: Exportação em Excel com Apache POI

6. **Segurança**: Senhas são criptografadas com BCrypt

---

## ❓ Perguntas Frequentes

**P: Como resetar o banco de dados?**  
R: `docker-compose down && docker volume rm transport-api_mongodb-data && docker-compose up -d`

**P: Como mudar a porta?**  
R: `mvn spring-boot:run -Dserver.port=9090`

**P: Como ver logs?**  
R: `docker-compose logs mongo` ou check console do Maven

**P: Posso usar MongoDB Atlas (cloud)?**  
R: Sim, altere `MONGODB_URI` em `application.yml` com sua connection string

**P: Como gerar relatório em PDF?**  
R: Já há estrutura com iText 8.0.3, endpoint pode ser adicionado em RelatorioController

---

Acesse Swagger: http://localhost:8080/swagger-ui.html

---

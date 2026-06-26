# Documentacao da API - Visao de Negocio e Tecnica

## 1. Visao de Negocio

### 1.1 Objetivo da API
A API de Transporte gerencia o ciclo completo de eventos (Congresso e Assembleia), participantes e pagamentos para operacao de viagens em grupo.

### 1.2 Principais capacidades de negocio
- Cadastro e manutencao de pessoas.
- Criacao e gestao de eventos com status e capacidade.
- Vinculo de participantes aos eventos por dia.
- Controle financeiro por participacao e por evento.
- Relatorios operacionais por dia (sexta, sabado, domingo) e exportacao em Excel.
- Controle de usuarios e permissao de acesso por perfil.

### 1.3 Regras de negocio relevantes
- Nao permite documento de pessoa duplicado.
- Nao permite participacao duplicada da mesma pessoa no mesmo evento (quando ativa).
- Nao permite participacao em evento encerrado.
- Controla lotacao por dia do evento.
- Valida crianca de colo por idade limite do evento.
- Atualiza status de pagamento conforme lancamentos.
- Exclusao de evento remove participacoes relacionadas e pagamentos associados.

### 1.4 Perfis e seguranca
- A API usa JWT Bearer Token.
- Endpoint aberto: `POST /api/v1/auth/login`.
- Endpoint autenticado: `GET /api/v1/auth/me`.
- Endpoints de negocio em `/api/v1/**` exigem role `ADMIN`.
- Endpoints de usuarios exigem `ADMIN` explicitamente.

---

## 2. Visao Tecnica

### 2.1 Stack
- Java 21
- Spring Boot 3.5.x
- Spring Security + JWT
- MongoDB
- Arquitetura Hexagonal (Ports and Adapters)

### 2.2 Estrutura de alto nivel
- `adapters/in/rest`: Controllers HTTP.
- `application/service`: regras de aplicacao.
- `domain`: entidades, enums e excecoes de dominio.
- `adapters/out/persistence/mongo`: persistencia MongoDB.
- `infrastructure/security`: autenticacao, autorizacao e filtros JWT.
- `infrastructure/exception`: tratamento global de erros.

### 2.3 Base URL
- Local: `http://localhost:8080`

### 2.4 Padrao de autenticacao
1. Fazer login para obter token.
2. Enviar header em chamadas protegidas:

```http
Authorization: Bearer <TOKEN>
```

### 2.5 Tratamento de erros
Erros retornam payload padrao com campos como:
- `status`
- `message`
- `error`
- `timestamp`

Possiveis codigos frequentes:
- `400` validacao/regra de negocio
- `401` autenticacao
- `403` autorizacao
- `404` recurso nao encontrado
- `409` conflito (duplicidade)
- `500` erro interno

---

## 3. Guia de Uso Rapido

### 3.1 Login

**Endpoint**: `POST /api/v1/auth/login`

**Request**
```json
{
  "username": "admin",
  "password": "senha"
}
```

**Response**
```json
{
  "token": "...",
  "type": "Bearer",
  "expiresIn": 86400000
}
```

### 3.2 Exemplo com cURL

```bash
curl -X GET "http://localhost:8080/api/v1/events" \
  -H "Authorization: Bearer SEU_TOKEN"
```

---

## 4. Catalogo de Endpoints

## 4.1 Auth

### POST /api/v1/auth/login
- Finalidade: autenticar usuario e retornar JWT.
- Autenticacao: nao.

### GET /api/v1/auth/me
- Finalidade: retornar dados do usuario autenticado.
- Autenticacao: sim.

---

## 4.2 Pessoas (`/api/v1/people`)

### POST /api/v1/people
- Finalidade: criar pessoa.

**Request (PessoaCreateRequest)**
```json
{
  "nomeCompleto": "Maria Silva",
  "tipoDocumento": "CPF",
  "numeroDocumento": "12345678900",
  "criancaColo": false,
  "telefone": "21999999999",
  "observacaoCurta": "Lider de equipe",
  "observacaoDetalhada": "Preferencia assento frente"
}
```

### GET /api/v1/people/{id}
- Finalidade: consultar pessoa por id.

### GET /api/v1/people
- Finalidade: listar pessoas.

### GET /api/v1/people/search?termo={termo}
- Finalidade: pesquisa textual de pessoas.

### PUT /api/v1/people/{id}
- Finalidade: atualizar dados da pessoa.

**Request (PessoaUpdateRequest)**
```json
{
  "nomeCompleto": "Maria Silva Souza",
  "criancaColo": false,
  "telefone": "21988888888",
  "observacaoCurta": "Atualizada",
  "observacaoDetalhada": "Sem restricoes"
}
```

### DELETE /api/v1/people/{id}
- Finalidade: excluir pessoa.

---

## 4.3 Eventos (`/api/v1/events`)

### POST /api/v1/events
- Finalidade: criar evento.

**Request (EventoCreateRequest)**
```json
{
  "nome": "Congresso 2026",
  "tipo": "CONGRESSO",
  "valorPassagem": 159.0,
  "vagasTotais": 46,
  "limiteIdadeCriancaColo": 5,
  "temas": ["Felicidade Eterna"],
  "dataOcorrencia": "2026-08-07T09:00:00",
  "diaAssembleia": null
}
```

### GET /api/v1/events/{id}
- Finalidade: obter evento simples por id.

### GET /api/v1/events/{id}/details
- Finalidade: obter evento consolidado (participantes, arrecadacao, percentuais).

### GET /api/v1/events
- Finalidade: listar eventos.

### PUT /api/v1/events/{id}
- Finalidade: atualizar evento.

### PATCH /api/v1/events/{id}/close
- Finalidade: encerrar evento.

### PATCH /api/v1/events/{id}/archive
- Finalidade: arquivar evento.

### DELETE /api/v1/events/{id}
- Finalidade: exclusao permanente.
- Comportamento: remove participacoes e pagamentos relacionados.

---

## 4.4 Participacoes (`/api/v1/participations`)

### POST /api/v1/participations
- Finalidade: adicionar participante ao evento.

**Request (ParticipacaoCreateRequest)**
```json
{
  "pessoaId": "0276284f-8e09-4115-b492-42bb71037a7e",
  "eventoId": "11d7ad83-0a96-4b50-8621-6c9b0679f796",
  "criancaColoId": null,
  "dias": ["SEXTA", "SABADO", "DOMINGO"]
}
```

### DELETE /api/v1/participations/{id}?keepPayment={true|false}
- Finalidade: remover participacao.
- `keepPayment=true`: cancela participacao mantendo historico de pagamento.
- `keepPayment=false`: remove participacao e pagamentos associados.

### GET /api/v1/participations/events/{eventId}/participants
- Finalidade: listar participantes do evento.

### GET /api/v1/participations/events/{eventId}/participants/day/{dia}
- Finalidade: listar participantes por dia (`SEXTA`, `SABADO`, `DOMINGO`).

---

## 4.5 Pagamentos (`/api/v1/payments`)

### POST /api/v1/payments
- Finalidade: criar pagamento para participacao.

**Request (PagamentoCreateRequest)**
```json
{
  "participacaoId": "588ef027-ddd5-44c4-a1a1-482cb5b768ad",
  "valorTotal": 159.0,
  "formaPagamento": "PIX",
  "dataPagamento": "2026-06-26T16:40:00",
  "observacao": "Entrada"
}
```

### POST /api/v1/payments/{paymentId}/installments
- Finalidade: registrar parcela/lancamento.

**Request (LancamentoPagamentoCreateRequest)**
```json
{
  "valor": 50.0,
  "formaPagamento": "DINHEIRO",
  "dataPagamento": "2026-06-26T18:00:00",
  "observacao": "Parcela 1",
  "dia": "SABADO"
}
```

### PUT /api/v1/payments/{paymentId}/installments/{installmentId}
- Finalidade: atualizar dados de parcela.

**Request (LancamentoPagamentoUpdateRequest)**
```json
{
  "valor": 60.0,
  "formaPagamento": "PIX",
  "dataPagamento": "2026-06-27T10:00:00",
  "observacao": "Ajuste de parcela",
  "dia": "DOMINGO"
}
```

### GET /api/v1/payments/{participationId}
- Finalidade: obter pagamento da participacao.

### GET /api/v1/payments/{paymentId}/history
- Finalidade: obter historico completo de um pagamento.

### GET /api/v1/payments/summary
- Finalidade: listar/resumir pagamentos.

---

## 4.6 Relatorios (`/api/v1/reports`)

### GET /api/v1/reports/events/{eventId}/friday
- Finalidade: relatorio de participantes de sexta.

### GET /api/v1/reports/events/{eventId}/saturday
- Finalidade: relatorio de participantes de sabado.

### GET /api/v1/reports/events/{eventId}/sunday
- Finalidade: relatorio de participantes de domingo.

### GET /api/v1/reports/events/{eventId}/excel?dia={DIA}
- Finalidade: exportar relatorio em Excel.
- Retorno: arquivo `.xlsx` com `Content-Disposition` para download.

---

## 4.7 Usuarios (`/api/v1/users`) - Admin

### POST /api/v1/users
- Finalidade: criar usuario.

**Request (UsuarioCreateRequest)**
```json
{
  "username": "operador",
  "password": "senhaForte",
  "role": "ADMIN"
}
```

### GET /api/v1/users
- Finalidade: listar usuarios.

### PATCH /api/v1/users/{id}/role?role={ROLE}
- Finalidade: alterar role do usuario.

### PATCH /api/v1/users/{id}/status?ativo={true|false}
- Finalidade: ativar/desativar usuario.

---

## 4.8 Criancas de colo (`/api/v1/lap-children`)

### POST /api/v1/lap-children
- Finalidade: registrar crianca de colo.

**Request (CriancaColoCreateRequest)**
```json
{
  "nomeCompleto": "Ana Souza",
  "documento": "ABC123",
  "dataNascimento": "2022-04-10"
}
```

### GET /api/v1/lap-children/{id}
- Finalidade: consultar crianca por id.

### GET /api/v1/lap-children
- Finalidade: listar criancas.

### DELETE /api/v1/lap-children/{id}
- Finalidade: excluir crianca.

---

## 5. Fluxos recomendados de uso

### 5.1 Fluxo de onboarding operacional
1. Login (`/auth/login`).
2. Cadastro/listagem de pessoas.
3. Criacao de evento.
4. Criacao de participacoes.
5. Registro de pagamentos e parcelas.
6. Consulta de resumo financeiro e relatorios.

### 5.2 Fluxo de encerramento de evento
1. Validar pagamentos pendentes.
2. Gerar relatorios por dia e Excel.
3. Encerrar (`/events/{id}/close`) ou arquivar (`/events/{id}/archive`).

---

## 6. Boas praticas de integracao

- Sempre validar token antes de chamadas de negocio.
- Tratar `409` para conflitos de duplicidade.
- Tratar `400` para regras de dominio (evento encerrado, vagas lotadas, idade invalida).
- Em operacoes sensiveis, usar idempotencia no cliente para evitar envio duplicado.
- Usar endpoint de detalhes de evento para dashboards consolidados (`/events/{id}/details`).

---

## 7. Referencias

- Swagger UI: `GET /swagger-ui/index.html`
- OpenAPI JSON: `GET /v3/api-docs`

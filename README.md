# BioERP

Plataforma web para gestão ambiental e de licenciamento construída com backend Spring Boot e frontend Next.js/React. O projeto segue princípios de Arquitetura Limpa, SOLID e ACID para garantir separação de responsabilidades, consistência transacional e extensibilidade.

## Visão geral
- **Backend:** Java 17, Spring Boot 3, H2 em memória para desenvolvimento (modo PostgreSQL) e suporte a PostgreSQL em produção. Estruturado em camadas de domínio, aplicação e interfaces.
- **Frontend:** Next.js 14 com TypeScript, Tailwind e componentes reutilizáveis. Todas as chamadas de dados agora apontam para a API real, sem dados fictícios.
- **Infra:** Migrações Flyway em `backend/src/main/resources/db/migration` garantem criação das tabelas e entidades principais.

## Pré-requisitos
- Java 17+
- Maven 3.9+
- Node.js 20+
- npm 10+
- PostgreSQL (opcional em desenvolvimento; obrigatório em produção)

## Como executar
### Backend
```bash
cd backend
mvn spring-boot:run
```
- A aplicação inicia na porta `8080` (configurável via `PORT`).
- Perfil padrão `local` usa H2 em memória (`MODE=PostgreSQL`) e migrações Flyway. Perfil `prod` valida o schema em PostgreSQL real.
- Configurações de banco no perfil `prod`:
  - `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`
- Endpoints principais:
  - `GET /api/health` — saúde da aplicação
  - `GET /api/clients`, `POST /api/clients`
  - `GET /api/units/{id}`
  - `GET /api/licenses`, `POST /api/units/{unitId}/licenses`, `GET /api/licenses/{id}`
  - `GET /api/alerts`, `PATCH /api/alerts/{id}/read`

### Frontend
```bash
cd frontend
npm install
npm run dev
```
- A aplicação roda em `http://localhost:5173`.
- Defina `NEXT_PUBLIC_API_BASE_URL` (ou `API_BASE_URL`) para apontar para a instância do backend, ex.: `http://localhost:8080/api`.
- A UI mostra listas vazias até que você cadastre dados reais via formulários ou chamadas diretas à API.

## Testes
- Backend: `cd backend && mvn test`
- Frontend: `cd frontend && npm run build`

## Principais pastas
```
backend/
  src/main/java/com/bioerp/...   # domínio, casos de uso e controladores REST
  src/main/resources/db/migration # migrações Flyway
frontend/
  app/                           # rotas Next.js (painel e APIs internas)
  components/                    # componentes reutilizáveis (UI e dashboards)
  lib/services/api-client.ts     # cliente para a API real
```

## Dados iniciais
O sistema sobe sem dados fictícios. Cadastre clientes, unidades e licenças pela interface ou via API para popular os painéis. Os módulos de KPI e listas exibem mensagens de vazio até que haja registros reais.

## Alinhamento visual
Os cartões de KPIs e painéis foram ajustados para manter alinhamento uniforme mesmo quando não há dados carregados. Conforme você incluir informações reais, os grids são preenchidos automaticamente.

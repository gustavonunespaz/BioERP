# BioERP

Plataforma web para consultorias ambientais construída com arquitetura limpa, Postgres, Spring Boot e React.

## Estrutura do projeto
- `backend/`: API REST em Java 17 com Spring Boot 3, preparada para PostgreSQL e princípios de ACID, SOLID e arquitetura em camadas.
- `frontend/`: SPA React com Vite e TypeScript, pronta para consumir a API e funcionar em qualquer navegador moderno.

## Pré-requisitos
- Java 17+
- Maven 3.9+
- Node.js 20+
- npm 10+
- Banco PostgreSQL acessível (opcional para subir a API em modo conectado)

## Como executar
### Backend
```bash
cd backend
mvn spring-boot:run
```
A aplicação inicia pela classe principal `com.bioerp.BioErpApplication` e expõe o endpoint de saúde em `GET /api/health`.
A profile padrão `local` usa um banco em memória H2 (modo PostgreSQL), dispensando um Postgres ativo. Para conectar a um
PostgreSQL real, defina `SPRING_PROFILES_ACTIVE=prod` e informe `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER` e `DB_PASSWORD`.

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Abra o navegador em `http://localhost:5173` para acessar a interface.

## Testes
- Backend: `mvn test`
- Frontend: `npm run build`

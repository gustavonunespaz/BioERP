# BioERP Painel - Frontend

## Estrutura
- Layout compartilhado em `app/(panel)/layout.tsx` com sidebar fixa, topbar contextual e área de conteúdo.
- Navegação principal: `/dashboard`, `/clientes`, `/licencas`, `/alertas`. Rotas de detalhe: `/clientes/[id]`, `/unidades/[id]`, `/licencas/[id]`.
- API mockada via rotas em `app/api` mantendo estado em memória para clientes, unidades, licenças e alertas.

## Funcionalidades implementadas
- **Dashboard** reutiliza componentes existentes com métricas, agenda e feeds.
- **Clientes**: listagem com estados de carregamento/erro/vazio, criação inline com validação mínima, detalhe com unidades e contatos.
- **Unidades**: detalhe com lista de licenças e aba de resíduos preparada para futuras entregas.
- **Licenças**: listagem com busca e filtro por status; detalhe com abas Resumo, Documentos (upload versionado), Condicionantes e Histórico.
- **Alertas**: central para listar e marcar como lido.

## Convenções
- Serviços de dados em `lib/services/api-client.ts` fazem chamadas REST para rotas locais.
- Tipagens compartilhadas em `lib/types.ts` e dados de exemplo em `lib/data/mock-api.ts`.
- UI construída com Next.js (App Router) e Tailwind, respeitando o design shadcn existente.

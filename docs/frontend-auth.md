# Autenticação do painel BioERP

## Fluxo principal
1. **Cadastro (/cadastro)**: coleta nome, e-mail e senha; envia POST `/auth/register` para o backend configurado em `NEXT_PUBLIC_API_BASE_URL` e, em caso de sucesso, salva o token em cookie `bioerp_token`.
2. **Login (/login)**: valida credenciais via POST `/auth/login`; em caso de sucesso, reutiliza o mesmo cookie e armazena o perfil no `localStorage` para exibição no topo do painel.
3. **Proteção de rotas**: o middleware (`frontend/middleware.ts`) bloqueia acessos não autenticados a `/`, `/dashboard`, `/clientes`, `/licencas`, `/alertas`, `/unidades` e ao grupo `(panel)`.

## Variáveis e segurança
- Defina `NEXT_PUBLIC_API_BASE_URL` apontando para o backend (ex.: `http://localhost:8080/api`).
- O cookie `bioerp_token` tem duração de 4 horas, escopo global (`path=/`) e `SameSite=Lax`.
- Use senhas fortes; o formulário impede envio sem dados obrigatórios e confirmações de senha divergentes.

## Mensagens de erro
Caso a API esteja inacessível ou a variável de ambiente não esteja configurada, o frontend exibe mensagens claras para guiar a correção do ambiente.

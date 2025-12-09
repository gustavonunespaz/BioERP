# Prompt para o Codex — BioERP MVP (Licenças + Clientes/Unidades + Alertas)

Este documento consolida o pacote de instruções a ser aplicado pelo agente de código (Codex) para implementar o MVP descrito. As diretrizes seguem Java 17 + Spring Boot 3, PostgreSQL (prod) / H2 (local), Clean Architecture e frontend em Next.js + Tailwind + shadcn/ui.

## 0) Objetivo do MVP
Entregar um núcleo funcional que permita:

1. Cadastrar Clientes
2. Cadastrar Unidades/Empreendimentos
3. Cadastrar Licenças Ambientais vinculadas às unidades
4. Anexar Documentos à licença com versionamento real
5. Registrar Condicionantes (MVP light)
6. Calcular farol de risco baseado em validade
7. Disparar alertas automáticos diários (e-mail + notificação interna)
8. Exibir no frontend: listas e detalhes de clientes, unidades e licenças; aba de documentos com histórico de versões; central de alertas

## 1) Backend — Arquitetura e Pastas
- Respeitar Clean Architecture, organizando em core/domain, core/application, core/ports e infrastructure.
- Manter compatibilidade com classes já existentes (EnvironmentalLicense, LicenseCondition, LicenseLifecycleStatus, LicenseStatusEvaluator, RegisterEnvironmentalLicenseUseCase, LicenseRepository, InMemoryLicenseRepository).
- Evoluir para incluir Clientes, Unidades, Documentos/Versões e Alertas.

## 2) Backend — Modelo de Dados
- Adicionar migrations (Flyway ou Liquibase) caso não exista.
- Criar tabelas: clients, units, environmental_licenses, license_documents, license_document_versions, license_conditions, audit_logs, alerts.
- Regras de integridade de versionamento: apenas 1 versão ativa por documento; nova versão desativa a anterior e incrementa o número.

## 3) Backend — Regras de Negócio
- Relações: Cliente → Unidades → Licenças; Licenças → Documentos → Versões; Licenças → Condicionantes.
- Implementar AddLicenseDocumentVersionUseCase com criação do documento por tipo, cálculo de próxima versão, ativação da versão nova, desativação da anterior, registro de audit_logs e opcional de impedir duplicidade por hash.
- Usar LicenseStatusEvaluator para lifecycle/farol, expondo lifecycleStatus, farolColor/severity e daysToExpire.
- Configurar thresholds via settings ou arquivo (renewal_window_days_default, warning_window_days, critical_window_days).
- Tratar licenças sem data_validade, renovação com CRL, documento anexado errado (nova versão + log) e clientes com múltiplas unidades.

## 4) Backend — Casos de Uso
- Clientes/Unidades: CreateClientUseCase, ListClientsUseCase, GetClientDetailUseCase, CreateUnitUseCase, ListUnitsByClientUseCase, GetUnitDetailUseCase.
- Licenças: atualizar RegisterEnvironmentalLicenseUseCase para exigir unitId; ListLicensesUseCase com filtros; GetLicenseDetailUseCase.
- Documentos/Versões: AddLicenseDocumentVersionUseCase, ListLicenseDocumentsUseCase, ListDocumentVersionsUseCase.
- Condicionantes: AddConditionUseCase, UpdateConditionStatusUseCase.
- Alertas: GenerateAlertsUseCase, ListAlertsUseCase, MarkAlertAsReadUseCase.

## 5) Backend — Repositórios e Adapters
- Ports: ClientRepository, UnitRepository, EnvironmentalLicenseRepository, LicenseDocumentRepository, LicenseDocumentVersionRepository, ConditionRepository, AlertRepository, AuditLogRepository, NotificationSenderPort (sendEmail, sendInApp).
- Infra: criar adapters JPA para todos.

## 6) Backend — Controllers REST
- Rotas: clientes (POST/GET, detail), unidades (POST/GET por cliente, detail), licenças (POST, list com filtros, detail), documentos/versões (POST versão, listar documentos e versões), condicionantes (POST, PATCH), alertas (GET, PATCH read).

## 7) Backend — Scheduler de Alertas
- Job diário para licenças ativas comparando data_validade com hoje, gerando severidades por janelas (90/60/30/vencida) com dedup_key `LICENSE:{licenseId}:{window}:{yyyy-MM-dd}` e envio IN_APP/EMAIL. Lógica na aplicação/domínio, scheduler apenas dispara.

## 8) Frontend — Next.js + Tailwind + shadcn/ui
- Rotas: /dashboard, /clientes, /clientes/[id], /unidades/[id], /licencas, /licencas/[id], /alertas.
- Telas: lista/detalhe de clientes; detalhe de unidade com aba "Resíduos (em breve)"; lista/detalhe de licenças com abas (Resumo, Documentos com histórico, Condicionantes, Histórico); central de alertas com filtros e CTAs.
- Componentes reutilizáveis: KpiCard, RiskBadge, FarolIndicator, LicensesTable, ClientsTable, UploadDocumentDialog, AlertList.

## 9) Preparação para módulos futuros
- Resíduos: aba placeholder na unidade e tabelas base waste_types, unit_waste_config.
- Transporte: garantir tipos de license_documents/versions extensíveis.
- Relatórios técnicos: dados estruturados, menu/placeholder.
- Auditorias: audit_logs robusto desde já.

## 10) Critérios de Aceite
- Clientes/Unidades: CNPJ válido, impedir duplicidade, vincular e listar unidades.
- Licenças: cadastro por unidade, listagem com filtros, detalhe com status calculado.
- Documentos: v1 automática, nova versão ativa e versões antigas consultáveis, log gravado.
- Condicionantes: criação e atualização de status.
- Alertas: job diário com dedup_key, central de alertas e marcação como lido.
- UI: telas funcionais, branding BioERP, estados loading/empty/error tratados.

## 11) Entrega final esperada
- Executar localmente com H2: criar cliente/unidade, criar licença, anexar PDF com histórico de versões, ver farol de risco, rodar job de alertas (endpoint debug opcional) e visualizar alertas no frontend.

Observações: não refatorar além do necessário, priorizar legibilidade, coesão e testes unitários de casos de uso. Regras de negócio no domínio/aplicação; controllers e JPA como adaptadores externos.

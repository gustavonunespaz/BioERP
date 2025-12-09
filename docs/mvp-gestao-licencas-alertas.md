# MVP de Gestão de Licenças, Clientes e Alertas do BioERP

## 1. Visão geral
Módulo central para compliance ambiental que conecta clientes, unidades operacionais e o ciclo de vida completo das licenças. Ele garante versionamento de documentos, rastreabilidade para auditorias e alertas automáticos de prazos, já preparado para expansão futura (resíduos, transporte, relatórios e auditorias).

## 2. Arquitetura de dados (MVP)
### 2.1 Entidades mínimas
- **Cliente**: razão social, CNPJ, segmento, status e contatos.
- **Unidade/Empreendimento**: vinculado a cliente; nome, CNPJ (opcional), endereço, município/UF, atividade, observações.
- **Licença**: unidade, tipo (LP/LI/LO/RLO/Autorização etc.), órgão emissor, nº processo, nº licença, datas (emissão/validade), status (ativa/vencida/em renovação/suspensa), responsável interno, observações e tags.
- **Documento da Licença**: agrupa arquivos por tipo (Licença PDF, Parecer, Protocolo, CRL, Ofício, Relatório de condicionantes) com descrição.
- **Versão de Documento**: versionamento real com `versão` (v1, v2...), `arquivo_url`, hash, data de upload, usuário, notas e flag de ativo.
- **Condicionante (leve)**: descrição, prazo, status.
- **Histórico/Auditoria**: logs de alterações e uploads.

### 2.2 Regras estruturais
- Sempre deve haver uma versão ativa por documento; versões antigas permanecem preservadas e não são apagadas (apenas admins podem arquivar). 
- Impedir uploads duplicados por hash (opcional) e exigir motivo para novas versões. 
- Evitar duplicidade de CNPJ e validar máscara/dígitos. 
- Unidade sempre vinculada a um cliente, permitindo um cliente com múltiplos CNPJs/unidades.

## 3. Fluxos principais
### 3.1 Ciclo de upload e versionamento
1. Usuário abre uma licença e aciona **Anexar documento**.
2. Seleciona o tipo de documento e faz upload do PDF.
3. Sistema registra como **v1**, marca como ativa e guarda autor/data. 
4. Novos uploads criam **v2, v3...**, tornando a nova versão ativa e arquivando as anteriores. 
5. Logs registram alterações e substituições (motivo obrigatório em versões posteriores). 

### 3.2 Leitura automática de PDF (gancho opcional)
- Ao subir um PDF, tentar extrair nº da licença, validade, órgão e cliente/unidade.
- Se falhar, permitir confirmação manual; dados estruturados são salvos para evoluir a automação no v1.1.

### 3.3 Cadastro de clientes e unidades
1. Cadastrar cliente com CNPJ principal, contatos e status.
2. Criar unidades com CNPJ próprio (se houver), endereço, atividade e responsável local.
3. Licenças, documentos, alertas e faturamento se conectam sempre à unidade correspondente.

### 3.4 Alertas automatizados
1. Rotina diária busca licenças ativas, compara validade e classifica conforme regra de prazo configurável (por tipo de licença, cliente e criticidade). 
2. Janelas sugeridas: 90 dias (informativo), 60 dias (atenção), 30 dias (crítico) e pós-vencimento com repetição controlada.
3. Envio por e-mail e notificação interna no MVP; planejar WhatsApp/SMS/push para próximas versões.
4. Evitar spam: controle de repetição, priorização de críticos e resumo semanal consolidado.

## 4. Telas e UX
### 4.1 Lista de licenças
- Filtros: cliente, unidade, tipo, status, validade, órgão. 
- Colunas: nº licença, tipo, empreendimento, validade, status e farol.

### 4.2 Detalhe da licença
- Abas: **Resumo** (dados principais, status, datas, responsável), **Documentos** (versões com download e comparação de histórico), **Condicionantes** (descrição, prazo, status), **Histórico** (log). 

### 4.3 Cadastro de clientes/unidades
- Lista de clientes com nome, CNPJ, nº de unidades, farol geral e status do contrato. 
- Detalhe do cliente com abas: Visão geral (dados, contatos, SLA), Unidades (lista + “Nova unidade”), Licenças (agregadas) e Documentos (repositório). 
- Detalhe da unidade: dados, licenças, condicionantes e histórico.

### 4.4 Central de alertas
- Lista com tipo, cliente/unidade, prazo, status e ação sugerida. 
- Filtros: críticos, vencendo na semana, vencendo no mês. 
- CTA guiada: criar tarefa de renovação, anexar protocolo, registrar CRL.

## 5. Cálculo de farol
- Baseado em `data_validade`: Verde (OK), Amarelo (atenção) a partir de 60/90 dias, Vermelho (crítico) vencida ou <30 dias. 
- Thresholds devem ser configuráveis em **Configurações**. 
- Tratar edge cases: licenças sem validade, renovação com CRL, regras diferentes por estado, mesmo processo para múltiplas unidades e substituição de documento com log.

## 6. Preparação para módulos futuros
- **Resíduos:** reservar aba “Resíduos (em breve)” na unidade; criar tabelas base `tipo_residuo` e `unidade_residuo_config`.
- **Transporte:** usar modelo de documento genérico para MTR/CDF com metadados.
- **Relatórios técnicos:** priorizar dados estruturados para farol mensal, histórico de renovação e relatórios por cliente.
- **Auditorias:** basear-se em documentos robustos, histórico de ações e permissões para checklists e não conformidades futuras.

## 7. Por que esse modelo é crítico
- Licença é entidade viva (não apenas PDF solto). 
- Versionamento real garante rastreabilidade jurídica. 
- Relação cliente → unidade → licença mantém consistência operacional. 
- Alertas com ação guiada evitam falhas de conformidade e alimentam tarefas de renovação. 

## 8. Resumo executável do fluxo completo
1. Cadastro de cliente e unidade. 
2. Criação da licença. 
3. Upload do PDF (v1) com logs. 
4. Cadastro básico de condicionantes. 
5. Cálculo automático do farol. 
6. Agendamento de alertas. 
7. Alertas geram tarefas e novos uploads (protocolo/CRL como v2). 
8. Histórico preservado e dashboards exibem risco por cliente/unidade.

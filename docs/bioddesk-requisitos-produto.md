# BioDesk — Visão e Requisitos Iniciais

## 1. Identidade do Produto
- **Nome oficial:** BioDesk.
- **Posicionamento:** SaaS leve e modular para consultorias ambientais, com foco em organização, conformidade legal e agilidade operacional.

## 2. Proposta de Valor
- Centraliza licenças, condicionantes, prazos e documentos críticos.
- Automatiza alertas e relatórios para reduzir risco de multas e retrabalho.
- Arquitetura limpa, aderente a ACID e SOLID, visando escalabilidade e facilidade de manutenção.

## 3. Segmentação e Personas
- Consultorias ambientais (pequenas, médias e grandes).
- Empresas que geram resíduos e/ou transportam químicos.
- Indústrias que precisam de regularização ambiental.
- Personas baseadas em rotinas reais de fiscalização, licenciamento e atendimento a condicionantes.

## 4. Problemas Prioritários
- Desorganização de licenças e documentos.
- Perda de prazos e risco de multas por falta de alertas confiáveis.
- Processos manuais e retrabalho constante.

## 5. Solução Proposta (MVP)
- **Gestão de Licenças Ambientais:** cadastro, versionamento e armazenamento dos PDFs associados.
- **Cadastro de Empresas/Clientes:** estrutura para múltiplos CNPJs e unidades operacionais.
- **Alertas Automatizados:** detecção de prazos e notificações multicanal para vencimento e renovação.
- **Planejamento de Módulos Futuros:** resíduos, transporte, relatórios técnicos e auditorias.

## 6. Benchmark e Diferenciais
- Benchmark direto com Ambisis; pontos fortes mapeados, porém com limitações de peso e modularidade.
- BioDesk seguirá abordagem SaaS pura, mais leve, modular e com cadência de atualização contínua.

## 7. Fluxo de Condicionantes e Prazos
- Upload do PDF da licença para identificação automática de condicionantes, validade e periodicidade.
- Controle dos atributos mínimos por condicionante/documento:
  - Nome/tipo do documento.
  - Órgão ambiental emissor.
  - Datas de emissão e validade.
  - Prazo de renovação parametrizável (60/80/120 dias).
  - Situação: **Vigente**, **Em renovação**, **Renovação atrasada** (fora do prazo mínimo) ou **Vencida**.
- Paleta de status sugerida: verde (vigente), amarelo (em renovação), laranja (renovação atrasada), vermelho (vencida).
- Agenda de visitas e entregas: registrar rotina de visitas e relatórios mensais de auditoria por cliente.

## 8. Estratégia e Metodologia
- Lean Startup: MVP enxuto com iterações rápidas e validação com consultores.
- Clean Architecture: fronteiras claras entre domínio, aplicação e infraestrutura para manter coesão e testabilidade.
- Observância a princípios ACID, SOLID e Código Limpo na modelagem e implementação.

## 9. Requisitos Não Funcionais Iniciais
- SaaS multi-tenant, pronto para atualização contínua sem downtime perceptível.
- Segurança: autenticação, autorização por papel e segregação de dados entre clientes.
- Observabilidade: logs de auditoria para prazos, renovações e alterações de condicionantes.
- Usabilidade: interface web responsiva com alertas destacados e filtros por status/prazo.

## 10. Próximos Passos Recomendados
- Definir modelos de domínio iniciais (Licença, Condicionante, Documento, Cliente, Visita, Alerta).
- Mapear integrações de entrada (upload manual, e-mail, OCR) e saída (notificações e relatórios).
- Priorizar casos de uso do MVP e critérios de aceite para alertas automáticos.
- Estabelecer convenções de pastas e camadas para garantir arquitetura limpa desde o início.

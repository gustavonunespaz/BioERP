# BioDesk - Estrutura inicial do backend

Este documento descreve a primeira entrega de arquitetura limpa aplicada ao backend do BioDesk.

## Camadas
- **Domínio (`core/domain`)**: modelos imutáveis (`EnvironmentalLicense`, `LicenseCondition`) e regras de negócio (`LicenseLifecycleStatus`).
- **Serviços de domínio**: `LicenseStatusEvaluator` avalia o status de validade e renovação com base em datas de emissão/validade e janela mínima configurável.
- **Aplicação (`core/application`)**: caso de uso `RegisterEnvironmentalLicenseUseCase` cria e valida licenças ambientais.
- **Ports (`core/ports`)**: `LicenseRepository` define contrato para persistência.
- **Infraestrutura (`infrastructure`)**: `InMemoryLicenseRepository` oferece armazenamento em memória para prototipação rápida.

## Regras de status implementadas
- **Vigente**: fora da janela mínima de renovação.
- **Renovação atrasada**: dentro da janela mínima sem solicitação registrada.
- **Em renovação**: solicitação registrada antes do vencimento.
- **Vencida**: data de validade anterior à data de referência.

## Próximos passos sugeridos
- Expor controladores REST para cadastro e consulta de licenças.
- Integrar persistência relacional (PostgreSQL) mantendo contratos de porta e adaptadores.
- Incluir modelo de cliente/unidade e logs de auditoria para alterações de prazos.
- Automatizar notificações com base nos estados retornados pelo avaliador de status.

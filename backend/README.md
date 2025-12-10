# BioERP Backend - Data Model MVP

Diagrama textual dos relacionamentos iniciais:

```
users
  ├─< environmental_licenses (responsible_user_id)
  ├─< license_document_versions (uploaded_by)
  └─< audit_logs (actor_user_id)

clients
  └─< units
         └─< environmental_licenses
                ├─< license_documents
                │     └─< license_document_versions
                ├─< license_conditions
                │     └─< alerts (optionally via condition_id)
                └─< alerts
```

Tabelas adicionais:

* alerts podem referenciar client ou unit para facilitar notificações agregadas.
* audit_logs registram ações de usuários sobre qualquer entidade por `entity_type` e `entity_id`.


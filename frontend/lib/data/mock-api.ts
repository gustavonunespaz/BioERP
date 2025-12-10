import { Alert, Client, License, LicenseDocument, UnitDetail } from "../types";

let clients: Client[] = [
  {
    id: "c1",
    name: "EcoTrans",
    segment: "Logística sustentável",
    status: "ativo",
    city: "Curitiba",
    state: "PR",
    notes: "Cliente ativo com foco em expansão de terminais.",
    updatedAt: "há 2 dias",
    createdAt: "2022-04-10",
    contacts: [
      { name: "Laura Lima", role: "Gestora ambiental", email: "laura@ecotrans.com", phone: "+55 41 99888-0001" }
    ],
    units: [
      { id: "u1", name: "Terminal Logístico Verde", city: "Curitiba", state: "PR", licenseCount: 3 },
      { id: "u2", name: "Pátio Eco Norte", city: "Londrina", state: "PR", licenseCount: 2 }
    ]
  },
  {
    id: "c2",
    name: "Luz do Sul",
    segment: "Energia solar",
    status: "risco",
    city: "Belo Horizonte",
    state: "MG",
    notes: "Monitorar condicionantes de emissões.",
    updatedAt: "há 5 horas",
    createdAt: "2023-01-18",
    contacts: [
      { name: "César Mello", role: "Operações", email: "cesar@luzdosul.com", phone: "+55 31 97777-4545" }
    ],
    units: [{ id: "u3", name: "Parque Solar Horizonte", city: "Caeté", state: "MG", licenseCount: 4 }]
  }
];

const licenses: License[] = [
  {
    id: "l1",
    code: "LO-2024-118",
    title: "Renovação LO - Usina Delta",
    status: "vigente",
    category: "Operação",
    authority: "SEMAD",
    issuedAt: "2024-02-10",
    expiresAt: "2025-02-10",
    unitId: "u1",
    clientId: "c1",
    documents: [
      {
        id: "d1",
        name: "Renovacao-LO-Usina-Delta.pdf",
        version: 2,
        uploadedAt: "há 4 dias",
        uploadedBy: "Bruna Sato",
        size: "2.4 MB"
      }
    ],
    conditions: [
      { id: "cond1", title: "Entrega relatório trimestral", status: "pendente", dueDate: "2024-12-01" },
      { id: "cond2", title: "Monitoramento emissões", status: "concluida", dueDate: "2024-09-01" }
    ],
    history: [
      { id: "h1", description: "Atualização de condicionante enviada", date: "2024-09-01", author: "Laura Lima" },
      { id: "h2", description: "Reunião com órgão ambiental", date: "2024-08-20", author: "César Mello" }
    ]
  },
  {
    id: "l2",
    code: "LP-2023-441",
    title: "LP Ampliação Terminal Norte",
    status: "critica",
    category: "Implantação",
    authority: "IBAMA",
    issuedAt: "2023-11-01",
    expiresAt: "2025-05-01",
    unitId: "u1",
    clientId: "c1",
    documents: [
      {
        id: "d2",
        name: "Inventario-florestal.pdf",
        version: 1,
        uploadedAt: "há 20 dias",
        uploadedBy: "Equipe campo",
        size: "1.2 MB"
      }
    ],
    conditions: [
      { id: "cond3", title: "Evidências de supressão", status: "alerta", dueDate: "2024-10-05" },
      { id: "cond4", title: "Plano de compensação", status: "pendente", dueDate: "2024-11-12" }
    ],
    history: [
      { id: "h3", description: "Solicitação de vistoria extraordinária", date: "2024-09-18", author: "Bruna Sato" }
    ]
  },
  {
    id: "l3",
    code: "LO-2022-009",
    title: "Operação Pátio Eco Norte",
    status: "vigente",
    category: "Operação",
    authority: "IAP",
    issuedAt: "2023-06-10",
    expiresAt: "2025-06-10",
    unitId: "u2",
    clientId: "c1",
    documents: [
      {
        id: "d3",
        name: "Relatorio-auditoria.pdf",
        version: 3,
        uploadedAt: "há 12 dias",
        uploadedBy: "Laura Lima",
        size: "980 KB"
      }
    ],
    conditions: [
      { id: "cond5", title: "Plano de emergência", status: "concluida", dueDate: "2024-07-01" }
    ],
    history: [{ id: "h4", description: "Licença arquivada para revisão", date: "2024-07-01", author: "Sistema" }]
  },
  {
    id: "l4",
    code: "LI-2024-778",
    title: "Intervenção Solar Horizonte",
    status: "critica",
    category: "Implantação",
    authority: "FEAM",
    issuedAt: "2024-05-10",
    expiresAt: "2024-12-10",
    unitId: "u3",
    clientId: "c2",
    documents: [
      {
        id: "d4",
        name: "Licenca-Instalacao.pdf",
        version: 1,
        uploadedAt: "há 8 dias",
        uploadedBy: "César Mello",
        size: "1.1 MB"
      }
    ],
    conditions: [
      { id: "cond6", title: "Compensação vegetal", status: "alerta", dueDate: "2024-10-30" }
    ],
    history: [{ id: "h5", description: "Alerta de vencimento", date: "2024-09-25", author: "Sistema" }]
  }
];

const units: UnitDetail[] = [
  {
    id: "u1",
    name: "Terminal Logístico Verde",
    clientId: "c1",
    city: "Curitiba",
    state: "PR",
    manager: "Bruna Sato",
    licenses: licenses.filter((license) => license.unitId === "u1")
  },
  {
    id: "u2",
    name: "Pátio Eco Norte",
    clientId: "c1",
    city: "Londrina",
    state: "PR",
    manager: "Laura Lima",
    licenses: licenses.filter((license) => license.unitId === "u2")
  },
  {
    id: "u3",
    name: "Parque Solar Horizonte",
    clientId: "c2",
    city: "Caeté",
    state: "MG",
    manager: "César Mello",
    licenses: licenses.filter((license) => license.unitId === "u3")
  }
];

let alerts: Alert[] = [
  {
    id: "a1",
    title: "Vencimento de condicionante",
    category: "Condicionantes",
    severity: "critical",
    timeAgo: "há 1h",
    description: "Condicionante 12 da LO-2024-118 vence em 7 dias.",
    read: false
  },
  {
    id: "a2",
    title: "Documento aguardando versão",
    category: "Documentos",
    severity: "warning",
    timeAgo: "há 3h",
    description: "Upload do Inventario-florestal.pdf não recebeu nova versão.",
    read: false
  },
  {
    id: "a3",
    title: "Checklist de resíduos",
    category: "Resíduos",
    severity: "info",
    timeAgo: "há 1 dia",
    description: "Nova coleta de evidências disponível para agendamento.",
    read: true
  }
];

export function listClients(): Client[] {
  return clients;
}

export function createClient(payload: {
  name: string;
  segment: string;
  city: string;
  state: string;
}): Client {
  const newClient: Client = {
    id: `c${clients.length + 1}`,
    name: payload.name,
    segment: payload.segment,
    status: "ativo",
    city: payload.city,
    state: payload.state,
    notes: "Cliente criado via painel.",
    updatedAt: "agora",
    createdAt: new Date().toISOString(),
    contacts: [],
    units: []
  };

  clients = [newClient, ...clients];
  return newClient;
}

export function getClient(id: string): Client | undefined {
  return clients.find((client) => client.id === id);
}

export function getUnit(id: string): UnitDetail | undefined {
  return units.find((unit) => unit.id === id);
}

export function listLicenses(filter?: { status?: string; search?: string; unitId?: string }): License[] {
  const { status, search, unitId } = filter || {};
  return licenses.filter((license) => {
    const matchesStatus = status ? license.status === status : true;
    const matchesUnit = unitId ? license.unitId === unitId : true;
    const matchesSearch = search
      ? `${license.title} ${license.code} ${license.category}`.toLowerCase().includes(search.toLowerCase())
      : true;

    return matchesStatus && matchesUnit && matchesSearch;
  });
}

export function getLicense(id: string): License | undefined {
  return licenses.find((license) => license.id === id);
}

export function addDocumentVersion(licenseId: string, file: { name: string; size: string; uploadedBy: string }): LicenseDocument | undefined {
  const target = licenses.find((license) => license.id === licenseId);
  if (!target) return undefined;

  const latestVersion = Math.max(...target.documents.map((doc) => doc.version), 0);
  const newVersion: LicenseDocument = {
    id: `d${Date.now()}`,
    name: file.name,
    version: latestVersion + 1,
    uploadedAt: "agora",
    uploadedBy: file.uploadedBy,
    size: file.size
  };

  target.documents = [newVersion, ...target.documents];
  const unitIdx = units.findIndex((unit) => unit.id === target.unitId);
  if (unitIdx >= 0) {
    units[unitIdx].licenses = units[unitIdx].licenses.map((license) => (license.id === target.id ? target : license));
  }
  return newVersion;
}

export function listAlerts(): Alert[] {
  return alerts;
}

export function markAlertAsRead(id: string): Alert | undefined {
  const alertIndex = alerts.findIndex((alert) => alert.id === id);
  if (alertIndex === -1) return undefined;
  alerts[alertIndex] = { ...alerts[alertIndex], read: true };
  return alerts[alertIndex];
}

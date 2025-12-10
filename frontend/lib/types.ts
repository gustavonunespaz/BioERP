export type Contact = {
  name: string;
  role: string;
  email: string;
  phone: string;
};

export type UnitSummary = {
  id: string;
  name: string;
  city: string;
  state: string;
  licenseCount: number;
};

export type Client = {
  id: string;
  name: string;
  segment: string;
  status: "ativo" | "risco" | "inativo";
  city: string;
  state: string;
  contacts: Contact[];
  notes: string;
  updatedAt: string;
  createdAt: string;
  units: UnitSummary[];
};

export type LicenseDocument = {
  id: string;
  name: string;
  version: number;
  uploadedAt: string;
  uploadedBy: string;
  size: string;
};

export type Condition = {
  id: string;
  title: string;
  status: "concluida" | "pendente" | "alerta";
  dueDate: string;
};

export type LicenseHistory = {
  id: string;
  description: string;
  date: string;
  author: string;
};

export type License = {
  id: string;
  code: string;
  title: string;
  status: "vigente" | "critica" | "arquivada";
  category: string;
  authority: string;
  issuedAt: string;
  expiresAt: string;
  unitId: string;
  clientId: string;
  documents: LicenseDocument[];
  conditions: Condition[];
  history: LicenseHistory[];
};

export type UnitDetail = {
  id: string;
  name: string;
  clientId: string;
  city: string;
  state: string;
  manager: string;
  licenses: License[];
};

export type Alert = {
  id: string;
  title: string;
  category: string;
  severity: "info" | "warning" | "critical";
  timeAgo: string;
  description: string;
  read: boolean;
};

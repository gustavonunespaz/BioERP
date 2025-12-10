export type Contact = {
  name?: string;
  role?: string;
  email?: string;
  phone?: string;
};

export type UnitSummary = {
  id: string;
  name: string;
  city?: string;
  state?: string;
  licenseCount?: number;
};

export type Client = {
  id: string;
  name: string;
  cnpj?: string;
  segment?: string;
  status?: "ativo" | "risco" | "inativo";
  city?: string;
  state?: string;
  contacts?: Contact[];
  notes?: string;
  updatedAt?: string;
  createdAt?: string;
  units?: UnitSummary[];
};

export type LicenseCondition = {
  id: string;
  name: string;
  documentType?: string;
  periodicityInMonths?: number;
  status?: string;
};

export type License = {
  id: string;
  code?: string;
  title?: string;
  name?: string;
  status?: string;
  category?: string;
  authority?: string;
  issuingAuthority?: string;
  issuedAt?: string;
  issueDate?: string;
  expiresAt?: string;
  expirationDate?: string;
  unitId: string;
  clientId: string;
  documents?: [];
  conditions: LicenseCondition[];
  history?: [];
  renewalLeadTimeDays?: number;
  renewalRequested?: boolean;
  renewalRequestedAt?: string | null;
};

export type UnitDetail = {
  id: string;
  name: string;
  clientId: string;
  city?: string;
  state?: string;
  manager?: string;
  cnpj?: string;
  createdAt?: string;
  licenses?: License[];
};

export type Alert = {
  id: string;
  title: string;
  category?: string;
  severity: "info" | "warning" | "critical";
  timeAgo?: string;
  description: string;
  read: boolean;
  createdAt?: string;
};

import { Alert, Client, License, LicenseCondition, UnitDetail } from "../types";

const FALLBACK_API = "http://localhost:8080/api";

export function getApiBaseUrl() {
  const configured = process.env.NEXT_PUBLIC_API_BASE_URL ?? process.env.API_BASE_URL;
  if (!configured) {
    throw new Error(
      "NEXT_PUBLIC_API_BASE_URL não está configurada. Defina a variável para apontar para o backend (ex.: http://localhost:8080/api)."
    );
  }
  return configured || FALLBACK_API;
}

export async function apiFetch(path: string, options?: RequestInit) {
  const baseUrl = getApiBaseUrl();
  let response: Response;

  try {
    response = await fetch(`${baseUrl}${path}`, options);
  } catch (error) {
    throw new Error(
      "Não foi possível acessar a API. Confirme se o backend está rodando e se NEXT_PUBLIC_API_BASE_URL aponta para ele."
    );
  }

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.error ?? "Erro ao comunicar com o serviço");
  }
  return response.json();
}

export async function fetchClients(): Promise<Client[]> {
  const data = await apiFetch("/clients", { cache: "no-store" });
  return Array.isArray(data)
    ? data.map((item: any) => ({
        id: item.id,
        name: item.name,
        tradeName: item.tradeName,
        cnpj: item.cnpj,
        segment: item.segment,
        status: mapStatus(item.status),
        mainContactName: item.mainContactName,
        mainContactEmail: item.mainContactEmail,
        mainContactPhone: item.mainContactPhone,
        notes: item.notes,
        createdAt: item.createdAt,
        updatedAt: item.updatedAt
      }))
    : [];
}

export async function createClientRequest(payload: {
  name: string;
  tradeName?: string;
  cnpj: string;
  segment?: string;
  status?: string;
  mainContactName?: string;
  mainContactEmail?: string;
  mainContactPhone?: string;
  notes?: string;
}): Promise<Client> {
  const data = await apiFetch("/clients", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
  return {
    id: data.id,
    name: data.name,
    tradeName: data.tradeName,
    cnpj: data.cnpj,
    segment: data.segment,
    status: mapStatus(data.status),
    mainContactName: data.mainContactName,
    mainContactEmail: data.mainContactEmail,
    mainContactPhone: data.mainContactPhone,
    notes: data.notes,
    createdAt: data.createdAt,
    updatedAt: data.updatedAt
  };
}

export async function fetchClientById(id: string): Promise<Client> {
  const data = await apiFetch(`/clients/${id}`, { cache: "no-store" });
  return {
    id: data.id,
    name: data.name,
    tradeName: data.tradeName,
    cnpj: data.cnpj,
    segment: data.segment,
    status: mapStatus(data.status),
    mainContactName: data.mainContactName,
    mainContactEmail: data.mainContactEmail,
    mainContactPhone: data.mainContactPhone,
    notes: data.notes,
    createdAt: data.createdAt,
    updatedAt: data.updatedAt
  };
}

export async function fetchUnitById(id: string): Promise<UnitDetail> {
  const data = await apiFetch(`/units/${id}`, { cache: "no-store" });
  return {
    id: data.id,
    name: data.name,
    clientId: data.clientId,
    addressLine: data.addressLine,
    cnpj: data.cnpj,
    city: data.city,
    state: data.state,
    activity: data.activity,
    notes: data.notes,
    createdAt: data.createdAt,
    updatedAt: data.updatedAt
  };
}

export async function fetchLicenses(filters?: { clientId?: string; unitId?: string }): Promise<License[]> {
  const params = new URLSearchParams();
  if (filters?.clientId) params.append("clientId", filters.clientId);
  if (filters?.unitId) params.append("unitId", filters.unitId);

  const url = `/licenses${params.toString() ? `?${params.toString()}` : ""}`;
  const data = await apiFetch(url, { cache: "no-store" });
  return Array.isArray(data)
    ? data.map((item: any) => ({
        id: item.id,
        clientId: item.clientId,
        unitId: item.unitId,
        name: item.name,
        issuingAuthority: item.issuingAuthority,
        issueDate: item.issueDate,
        expirationDate: item.expirationDate,
        renewalLeadTimeDays: item.renewalLeadTimeDays,
        renewalRequested: item.renewalRequested,
        renewalRequestedAt: item.renewalRequestedAt,
        status: item.status,
        conditions: (item.conditions ?? []).map((condition: any): LicenseCondition => ({
          id: condition.id,
          name: condition.name,
          documentType: condition.documentType,
          periodicityInMonths: condition.periodicityInMonths,
          status: condition.status
        }))
      }))
    : [];
}

export async function fetchLicenseById(id: string): Promise<License> {
  const item = await apiFetch(`/licenses/${id}`, { cache: "no-store" });
  return {
    id: item.id,
    clientId: item.clientId,
    unitId: item.unitId,
    name: item.name,
    issuingAuthority: item.issuingAuthority,
    issueDate: item.issueDate,
    expirationDate: item.expirationDate,
    renewalLeadTimeDays: item.renewalLeadTimeDays,
    renewalRequested: item.renewalRequested,
    renewalRequestedAt: item.renewalRequestedAt,
    status: item.status,
    conditions: (item.conditions ?? []).map((condition: any): LicenseCondition => ({
      id: condition.id,
      name: condition.name,
      documentType: condition.documentType,
      periodicityInMonths: condition.periodicityInMonths,
      status: condition.status
    }))
  };
}

export async function fetchAlerts(): Promise<Alert[]> {
  const data = await apiFetch("/alerts", { cache: "no-store" });
  return Array.isArray(data)
    ? data.map((item: any) => ({
        id: item.id,
        title: item.title,
        description: item.message,
        read: item.read,
        severity: item.type === "LICENSE_EXPIRATION" ? "warning" : "info",
        createdAt: item.createdAt,
        category: item.type
      }))
    : [];
}

export async function markAlertRead(id: string): Promise<Alert> {
  const data = await apiFetch(`/alerts/${id}/read`, { method: "PATCH" });
  return {
    id: data.id,
    title: data.title,
    description: data.message,
    read: data.read,
    severity: data.type === "LICENSE_EXPIRATION" ? "warning" : "info",
    createdAt: data.createdAt,
    category: data.type
  };
}

function mapStatus(status?: string) {
  if (!status) return undefined;
  const normalized = status.toLowerCase();
  if (normalized === "active") return "ativo";
  if (normalized === "risk") return "risco";
  if (normalized === "inactive") return "inativo";
  return status as Client["status"];
}

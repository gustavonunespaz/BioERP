import { Alert, Client, License, LicenseDocument, UnitDetail } from "../types";

async function handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.error ?? "Erro ao comunicar com o serviço");
  }
  return response.json();
}

export async function fetchClients(): Promise<Client[]> {
  const response = await fetch("/api/clients", { cache: "no-store" });
  const data = await handleResponse<{ clients: Client[] }>(response);
  return data.clients;
}

export async function createClientRequest(payload: {
  name: string;
  segment: string;
  city: string;
  state: string;
}): Promise<Client> {
  const response = await fetch("/api/clients", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
  return handleResponse<Client>(response);
}

export async function fetchClientById(id: string): Promise<Client> {
  const response = await fetch(`/api/clients/${id}`, { cache: "no-store" });
  return handleResponse<Client>(response);
}

export async function fetchUnitById(id: string): Promise<UnitDetail> {
  const response = await fetch(`/api/units/${id}`, { cache: "no-store" });
  return handleResponse<UnitDetail>(response);
}

export async function fetchLicenses(filters?: { status?: string; search?: string; unitId?: string }): Promise<License[]> {
  const params = new URLSearchParams();
  if (filters?.status) params.append("status", filters.status);
  if (filters?.search) params.append("q", filters.search);
  if (filters?.unitId) params.append("unitId", filters.unitId);

  const url = `/api/licenses${params.toString() ? `?${params.toString()}` : ""}`;
  const response = await fetch(url, { cache: "no-store" });
  const data = await handleResponse<{ licenses: License[] }>(response);
  return data.licenses;
}

export async function fetchLicenseById(id: string): Promise<License> {
  const response = await fetch(`/api/licenses/${id}`, { cache: "no-store" });
  return handleResponse<License>(response);
}

export async function uploadLicenseDocument(
  licenseId: string,
  payload: { name: string; size: string; uploadedBy: string }
): Promise<LicenseDocument> {
  const response = await fetch(`/api/licenses/${licenseId}/documents`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });

  return handleResponse<LicenseDocument>(response);
}

export async function fetchAlerts(): Promise<Alert[]> {
  const response = await fetch("/api/alerts", { cache: "no-store" });
  const data = await handleResponse<{ alerts: Alert[] }>(response);
  return data.alerts;
}

export async function markAlertRead(id: string): Promise<Alert> {
  const response = await fetch("/api/alerts", {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ id })
  });

  return handleResponse<Alert>(response);
}

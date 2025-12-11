import { apiFetch } from "./api-client";

export type AuthPayload = {
  name?: string;
  email: string;
  password: string;
};

export type AuthResponse = {
  token: string;
  user: {
    name: string;
    email: string;
  };
};

export async function registerUser(payload: AuthPayload): Promise<AuthResponse> {
  return apiFetch("/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
}

export async function loginUser(payload: AuthPayload): Promise<AuthResponse> {
  return apiFetch("/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
}

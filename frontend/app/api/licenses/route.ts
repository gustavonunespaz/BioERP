import { NextResponse } from "next/server";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? process.env.API_BASE_URL ?? "http://localhost:8080/api";

export async function GET(request: Request) {
  const { searchParams } = new URL(request.url);
  const params = new URLSearchParams();
  const unitId = searchParams.get("unitId");
  const clientId = searchParams.get("clientId");
  if (unitId) params.append("unitId", unitId);
  if (clientId) params.append("clientId", clientId);

  const response = await fetch(`${API_BASE_URL}/licenses${params.toString() ? `?${params.toString()}` : ""}`);
  const data = await response.json();
  return NextResponse.json({ licenses: data ?? [] }, { status: response.status });
}

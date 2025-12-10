import { NextResponse } from "next/server";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? process.env.API_BASE_URL ?? "http://localhost:8080/api";

export async function GET() {
  const response = await fetch(`${API_BASE_URL}/alerts`, { cache: "no-store" });
  const data = await response.json();
  return NextResponse.json({ alerts: data ?? [] }, { status: response.status });
}

export async function PATCH(request: Request) {
  const body = await request.json();
  if (!body?.id) {
    return NextResponse.json({ error: "ID obrigatório" }, { status: 400 });
  }

  const response = await fetch(`${API_BASE_URL}/alerts/${body.id}/read`, { method: "PATCH" });
  if (response.status === 404) {
    return NextResponse.json({ error: "Alerta não encontrado" }, { status: 404 });
  }

  const data = await response.json();
  return NextResponse.json(data, { status: response.status });
}

import { NextResponse } from "next/server";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? process.env.API_BASE_URL ?? "http://localhost:8080/api";

export async function GET() {
  const response = await fetch(`${API_BASE_URL}/clients`, { cache: "no-store" });
  const data = await response.json();
  return NextResponse.json({ clients: data ?? [] }, { status: response.status });
}

export async function POST(request: Request) {
  const body = await request.json();
  if (!body?.name || !body?.cnpj) {
    return NextResponse.json({ error: "Dados obrigatórios ausentes" }, { status: 400 });
  }

  const response = await fetch(`${API_BASE_URL}/clients`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name: body.name, cnpj: body.cnpj })
  });
  const data = await response.json();
  return NextResponse.json(data, { status: response.status });
}

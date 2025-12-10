import { NextResponse } from "next/server";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? process.env.API_BASE_URL ?? "http://localhost:8080/api";

export async function GET(_request: Request, { params }: { params: { id: string } }) {
  const response = await fetch(`${API_BASE_URL}/licenses/${params.id}`, { cache: "no-store" });
  if (response.status === 404) {
    return NextResponse.json({ error: "Licença não encontrada" }, { status: 404 });
  }
  const data = await response.json();
  return NextResponse.json(data, { status: response.status });
}

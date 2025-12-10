import { NextResponse } from "next/server";

import { createClient, listClients } from "@/lib/data/mock-api";

export async function GET() {
  return NextResponse.json({ clients: listClients() });
}

export async function POST(request: Request) {
  const body = await request.json();
  if (!body?.name || !body?.segment || !body?.city || !body?.state) {
    return NextResponse.json({ error: "Dados obrigatórios ausentes" }, { status: 400 });
  }

  const client = createClient({ name: body.name, segment: body.segment, city: body.city, state: body.state });
  return NextResponse.json(client, { status: 201 });
}

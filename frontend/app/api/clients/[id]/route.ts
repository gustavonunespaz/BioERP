import { NextResponse } from "next/server";

import { getClient } from "@/lib/data/mock-api";

export async function GET(_request: Request, { params }: { params: { id: string } }) {
  const client = getClient(params.id);
  if (!client) {
    return NextResponse.json({ error: "Cliente não encontrado" }, { status: 404 });
  }

  return NextResponse.json(client);
}

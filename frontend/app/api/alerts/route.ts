import { NextResponse } from "next/server";

import { listAlerts, markAlertAsRead } from "@/lib/data/mock-api";

export async function GET() {
  return NextResponse.json({ alerts: listAlerts() });
}

export async function PATCH(request: Request) {
  const body = await request.json();
  if (!body?.id) {
    return NextResponse.json({ error: "ID obrigatório" }, { status: 400 });
  }

  const updated = markAlertAsRead(body.id);
  if (!updated) {
    return NextResponse.json({ error: "Alerta não encontrado" }, { status: 404 });
  }

  return NextResponse.json(updated);
}

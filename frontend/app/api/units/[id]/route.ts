import { NextResponse } from "next/server";

import { getUnit } from "@/lib/data/mock-api";

export async function GET(_request: Request, { params }: { params: { id: string } }) {
  const unit = getUnit(params.id);
  if (!unit) {
    return NextResponse.json({ error: "Unidade não encontrada" }, { status: 404 });
  }

  return NextResponse.json(unit);
}

import { NextResponse } from "next/server";

import { getLicense } from "@/lib/data/mock-api";

export async function GET(_request: Request, { params }: { params: { id: string } }) {
  const license = getLicense(params.id);
  if (!license) {
    return NextResponse.json({ error: "Licença não encontrada" }, { status: 404 });
  }

  return NextResponse.json(license);
}

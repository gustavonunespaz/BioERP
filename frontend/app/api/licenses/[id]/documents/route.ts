import { NextResponse } from "next/server";

import { addDocumentVersion } from "@/lib/data/mock-api";

export async function POST(request: Request, { params }: { params: { id: string } }) {
  const body = await request.json();
  if (!body?.name || !body?.size || !body?.uploadedBy) {
    return NextResponse.json({ error: "Arquivo inválido" }, { status: 400 });
  }

  const newDoc = addDocumentVersion(params.id, {
    name: body.name,
    size: body.size,
    uploadedBy: body.uploadedBy
  });

  if (!newDoc) {
    return NextResponse.json({ error: "Licença não encontrada" }, { status: 404 });
  }

  return NextResponse.json(newDoc, { status: 201 });
}

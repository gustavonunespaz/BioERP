import { NextResponse } from "next/server";

export async function POST() {
  return NextResponse.json(
    { error: "Upload direto deve ser feito via API /licenses/{id}/documents/{tipo}/versions" },
    { status: 501 }
  );
}

import { NextResponse } from "next/server";

import { listLicenses } from "@/lib/data/mock-api";

export async function GET(request: Request) {
  const { searchParams } = new URL(request.url);
  const status = searchParams.get("status") || undefined;
  const search = searchParams.get("q") || undefined;
  const unitId = searchParams.get("unitId") || undefined;

  const licenses = listLicenses({ status, search, unitId });
  return NextResponse.json({ licenses });
}

"use client";

import { useEffect, useMemo, useState } from "react";
import Link from "next/link";
import { AlertCircle, Loader2 } from "lucide-react";

import { Badge } from "@/components/ui/badge";
import { Card } from "@/components/ui/card";
import { fetchLicenses } from "@/lib/services/api-client";
import { License } from "@/lib/types";

const statusPill: Record<string, string> = {
  VIGENTE: "bg-emerald-50 text-emerald-700",
  EM_RENOVACAO: "bg-sky-50 text-sky-700",
  RENOVACAO_ATRASADA: "bg-amber-50 text-amber-700",
  VENCIDA: "bg-red-50 text-red-700"
};

export default function LicencasPage() {
  const [licenses, setLicenses] = useState<License[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const filtered = useMemo(() => licenses, [licenses]);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await fetchLicenses();
        setLicenses(response);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-3 lg:flex-row lg:items-center lg:justify-between">
        <div>
          <h1 className="text-2xl font-semibold text-slate-900">Licenças</h1>
          <p className="text-sm text-slate-500">Filtros rápidos e visão geral de vencimento.</p>
        </div>
        <div className="text-sm text-slate-600">Sincronizado com a API real, sem dados fictícios.</div>
      </div>

      <Card className="p-4 lg:p-6">
        {loading && (
          <div className="flex items-center gap-2 text-sm text-slate-600">
            <Loader2 className="h-4 w-4 animate-spin" /> carregando licenças...
          </div>
        )}
        {error && (
          <div className="mb-3 flex items-center gap-2 rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-700">
            <AlertCircle className="h-4 w-4" /> {error}
          </div>
        )}
        {!loading && !error && filtered.length === 0 ? (
          <p className="text-sm text-slate-500">Nenhuma licença encontrada.</p>
        ) : null}

        <div className="grid gap-3 md:grid-cols-2 xl:grid-cols-3">
          {filtered.map((license) => (
            <Link
              key={license.id}
              href={`/licencas/${license.id}`}
              className="rounded-xl border border-slate-100 bg-white p-4 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md"
            >
              <div className="flex items-start justify-between">
                <div className="space-y-1">
                  <p className="text-sm font-semibold text-slate-900">{license.name ?? license.title ?? "Licença"}</p>
                  <p className="text-xs text-slate-500">Autoridade: {license.issuingAuthority ?? license.authority ?? "não informado"}</p>
                </div>
                <span className={`rounded-full px-3 py-1 text-xs font-semibold ${statusPill[license.status ?? ""] ?? "bg-slate-100 text-slate-600"}`}>
                  {license.status ?? "Sem status"}
                </span>
              </div>
              <div className="mt-3 grid grid-cols-2 gap-3 text-xs text-slate-500">
                <div>
                  <p className="font-semibold text-slate-700">Cliente</p>
                  <p>{license.clientId}</p>
                </div>
                <div>
                  <p className="font-semibold text-slate-700">Vencimento</p>
                  <p>{license.expirationDate ?? license.expiresAt ?? "Sem data"}</p>
                </div>
              </div>
              <div className="mt-3 flex flex-wrap gap-2 text-xs text-slate-500">
                <Badge variant="secondary">Unidade {license.unitId}</Badge>
                <Badge variant="outline">Renovação: {license.renewalLeadTimeDays ?? 0} dias</Badge>
              </div>
            </Link>
          ))}
        </div>
      </Card>
    </div>
  );
}

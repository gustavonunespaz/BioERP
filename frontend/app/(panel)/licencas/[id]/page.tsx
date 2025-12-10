"use client";

import { useEffect, useState } from "react";
import { AlertCircle, Loader2 } from "lucide-react";
import { useParams } from "next/navigation";

import { Card } from "@/components/ui/card";
import { fetchLicenseById } from "@/lib/services/api-client";
import { License } from "@/lib/types";

const tabs = [
  { id: "resumo", label: "Resumo" },
  { id: "condicionantes", label: "Condicionantes" }
] as const;

type TabId = (typeof tabs)[number]["id"];

const statusTag: Record<string, string> = {
  VIGENTE: "bg-emerald-50 text-emerald-700",
  EM_RENOVACAO: "bg-sky-50 text-sky-700",
  RENOVACAO_ATRASADA: "bg-amber-50 text-amber-700",
  VENCIDA: "bg-red-50 text-red-700"
};

export default function LicenseDetailPage() {
  const params = useParams<{ id: string }>();
  const [license, setLicense] = useState<License | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [tab, setTab] = useState<TabId>("resumo");

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchLicenseById(params.id);
        setLicense(data);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [params.id]);

  if (loading) {
    return (
      <div className="flex items-center gap-2 text-sm text-slate-600">
        <Loader2 className="h-4 w-4 animate-spin" /> carregando licença...
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center gap-2 rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-700">
        <AlertCircle className="h-4 w-4" /> {error}
      </div>
    );
  }

  if (!license) {
    return <p className="text-sm text-slate-500">Licença não encontrada.</p>;
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-2">
        <h1 className="text-2xl font-semibold text-slate-900">{license.name ?? license.title ?? "Licença"}</h1>
        <div className="flex flex-wrap items-center gap-2 text-xs text-slate-600">
          <span className={`rounded-full px-3 py-1 font-semibold ${statusTag[license.status ?? ""] ?? "bg-slate-100 text-slate-700"}`}>
            {(license.status ?? "Sem status").toString()}
          </span>
          <span className="rounded-full bg-slate-100 px-3 py-1 font-semibold text-slate-700">Unidade {license.unitId}</span>
        </div>
      </div>

      <div className="flex gap-2">
        {tabs.map((item) => (
          <button
            key={item.id}
            onClick={() => setTab(item.id)}
            className={`rounded-full px-4 py-2 text-xs font-semibold ${
              tab === item.id ? "bg-emerald-100 text-emerald-800" : "bg-slate-100 text-slate-600"
            }`}
          >
            {item.label}
          </button>
        ))}
      </div>

      {tab === "resumo" && (
        <Card className="p-4 lg:p-6">
          <div className="grid gap-4 md:grid-cols-2">
            <div>
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">Autoridade</p>
              <p className="text-sm text-slate-900">{license.issuingAuthority ?? license.authority ?? "Não informado"}</p>
            </div>
            <div>
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">Validade</p>
              <p className="text-sm text-slate-900">{license.expirationDate ?? license.expiresAt ?? "Sem data"}</p>
            </div>
            <div>
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">Emissão</p>
              <p className="text-sm text-slate-900">{license.issueDate ?? license.issuedAt ?? "Sem data"}</p>
            </div>
            <div>
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">Documentos</p>
              <p className="text-sm text-slate-900">Disponível via API de documentos</p>
            </div>
          </div>
        </Card>
      )}

      {tab === "condicionantes" && (
        <Card className="space-y-3 p-4 lg:p-6">
          {license.conditions.map((condition) => (
            <div key={condition.id} className="rounded-xl border border-slate-100 bg-white p-4">
              <div className="flex items-center justify-between">
                <p className="text-sm font-semibold text-slate-900">{condition.name}</p>
                <span
                  className={`rounded-full px-3 py-1 text-xs font-semibold ${
                    condition.status === "COMPLETED"
                      ? "bg-emerald-50 text-emerald-700"
                      : condition.status === "LATE"
                        ? "bg-amber-50 text-amber-700"
                        : "bg-slate-100 text-slate-700"
                  }`}
                >
                  {condition.status ?? "Sem status"}
                </span>
              </div>
              <p className="mt-2 text-xs text-slate-500">Periodicidade: {condition.periodicityInMonths ?? 0} meses</p>
            </div>
          ))}
        </Card>
      )}
    </div>
  );
}

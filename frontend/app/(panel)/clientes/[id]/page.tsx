"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { AlertCircle, Building2, Loader2, Phone } from "lucide-react";
import { useParams } from "next/navigation";

import { Card } from "@/components/ui/card";
import { fetchClientById } from "@/lib/services/api-client";
import { Client } from "@/lib/types";

export default function ClientDetailPage() {
  const params = useParams<{ id: string }>();
  const [client, setClient] = useState<Client | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchClientById(params.id);
        setClient(data);
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
        <Loader2 className="h-4 w-4 animate-spin" /> carregando cliente...
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

  if (!client) {
    return <p className="text-sm text-slate-500">Cliente não encontrado.</p>;
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-2">
        <h1 className="text-2xl font-semibold text-slate-900">{client.name}</h1>
        <p className="text-sm text-slate-500">Nome fantasia: {client.tradeName || "não informado"}</p>
        <div className="flex flex-wrap items-center gap-2 text-xs text-slate-500">
          <span className="rounded-full bg-slate-100 px-3 py-1 font-semibold text-slate-700">CNPJ: {client.cnpj ?? "não informado"}</span>
          <span className="rounded-full bg-slate-100 px-3 py-1 font-semibold text-slate-700">Segmento: {client.segment || "não informado"}</span>
          <span
            className={`rounded-full px-3 py-1 font-semibold ${
              client.status === "ativo"
                ? "bg-emerald-50 text-emerald-700"
                : client.status === "risco"
                  ? "bg-amber-50 text-amber-700"
                  : "bg-slate-100 text-slate-700"
            }`}
          >
            Status: {client.status ?? "Sem status"}
          </span>
        </div>
      </div>

      <div className="grid gap-4 lg:grid-cols-[1fr_0.8fr]">
        <Card className="p-4 lg:p-6">
          <div className="flex items-center justify-between">
            <p className="text-sm font-semibold text-slate-900">Unidades</p>
            <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">
              {client.units?.length ?? 0} ativos
            </span>
          </div>
          <div className="mt-4 grid gap-3 md:grid-cols-2">
            {(client.units ?? []).map((unit) => (
              <Link
                key={unit.id}
                href={`/unidades/${unit.id}`}
                className="rounded-xl border border-slate-100 bg-white p-4 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md"
              >
                <div className="flex items-start justify-between">
                  <div className="space-y-1">
                    <p className="text-sm font-semibold text-slate-900">{unit.name}</p>
                    <p className="text-xs text-slate-500">{unit.city ?? "-"}/{unit.state ?? "-"}</p>
                  </div>
                  <span className="rounded-full bg-slate-100 px-3 py-1 text-xs font-semibold text-slate-700">
                    {unit.licenseCount ?? 0} licenças
                  </span>
                </div>
                <p className="mt-2 text-xs text-slate-500">Última atualização {client.updatedAt ?? "-"}</p>
              </Link>
            ))}
          </div>
        </Card>

        <Card className="p-4 lg:p-6">
          <div className="flex items-center gap-2 text-sm font-semibold text-slate-900">
            <Building2 className="h-4 w-4 text-emerald-600" /> Contato principal
          </div>
          <div className="mt-4 space-y-3">
            <div className="rounded-xl border border-slate-100 bg-white p-4">
              <p className="text-sm font-semibold text-slate-900">{client.mainContactName ?? "Não informado"}</p>
              <p className="text-xs text-slate-500">{client.mainContactEmail ?? "Sem e-mail"}</p>
              <div className="mt-2 flex items-center gap-2 text-xs text-slate-500">
                <Phone className="h-4 w-4" /> {client.mainContactPhone ?? "Sem telefone"}
              </div>
            </div>
            {(client.notes || "").trim().length > 0 ? (
              <div className="rounded-xl border border-emerald-100 bg-emerald-50/60 p-4 text-sm text-emerald-900">
                <p className="text-xs font-semibold uppercase tracking-wide text-emerald-800">Observações</p>
                <p className="mt-1 leading-relaxed">{client.notes}</p>
              </div>
            ) : (
              <p className="text-sm text-slate-500">Nenhuma observação registrada.</p>
            )}
          </div>
        </Card>
      </div>
    </div>
  );
}

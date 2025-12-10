"use client";

import { useEffect, useMemo, useState } from "react";
import Link from "next/link";
import { AlertCircle, Filter, Loader2, Search } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { fetchLicenses } from "@/lib/services/api-client";
import { License } from "@/lib/types";

const statusLabel: Record<License["status"], string> = {
  vigente: "Vigente",
  critica: "Crítica",
  arquivada: "Arquivada"
};

const statusPill: Record<License["status"], string> = {
  vigente: "bg-emerald-50 text-emerald-700",
  critica: "bg-amber-50 text-amber-700",
  arquivada: "bg-slate-100 text-slate-600"
};

export default function LicencasPage() {
  const [licenses, setLicenses] = useState<License[]>([]);
  const [statusFilter, setStatusFilter] = useState<string>("");
  const [search, setSearch] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const filtered = useMemo(() => licenses, [licenses]);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await fetchLicenses({ status: statusFilter || undefined, search: search || undefined });
        setLicenses(response);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [statusFilter, search]);

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-3 lg:flex-row lg:items-center lg:justify-between">
        <div>
          <h1 className="text-2xl font-semibold text-slate-900">Licenças</h1>
          <p className="text-sm text-slate-500">Filtros rápidos e visão geral de vencimento.</p>
        </div>
        <div className="flex items-center gap-2">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
            <input
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="Buscar por código ou título"
              className="w-64 rounded-xl border border-slate-200 bg-slate-50 px-10 py-2 text-sm focus:border-emerald-300 focus:outline-none focus:ring-2 focus:ring-emerald-100"
            />
          </div>
          <select
            className="rounded-xl border border-slate-200 bg-white px-3 py-2 text-sm focus:border-emerald-300 focus:outline-none focus:ring-2 focus:ring-emerald-100"
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
          >
            <option value="">Todos os status</option>
            <option value="vigente">Vigente</option>
            <option value="critica">Crítica</option>
            <option value="arquivada">Arquivada</option>
          </select>
          <Button variant="outline" className="gap-2">
            <Filter className="h-4 w-4" /> Filtros
          </Button>
        </div>
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
                  <p className="text-sm font-semibold text-slate-900">{license.title}</p>
                  <p className="text-xs text-slate-500">{license.code}</p>
                </div>
                <span className={`rounded-full px-3 py-1 text-xs font-semibold ${statusPill[license.status]}`}>
                  {statusLabel[license.status]}
                </span>
              </div>
              <div className="mt-3 grid grid-cols-2 gap-3 text-xs text-slate-500">
                <div>
                  <p className="font-semibold text-slate-700">Categoria</p>
                  <p>{license.category}</p>
                </div>
                <div>
                  <p className="font-semibold text-slate-700">Vencimento</p>
                  <p>{license.expiresAt}</p>
                </div>
              </div>
              <p className="mt-3 text-xs text-slate-500">{license.documents.length} documentos vinculados</p>
            </Link>
          ))}
        </div>
      </Card>
    </div>
  );
}

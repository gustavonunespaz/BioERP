"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { AlertCircle, Clock, FileText, Leaf, Loader2, MapPin } from "lucide-react";
import { useParams } from "next/navigation";

import { Card } from "@/components/ui/card";
import { fetchUnitById } from "@/lib/services/api-client";
import { License, UnitDetail } from "@/lib/types";

const statusTone: Record<License["status"], string> = {
  vigente: "text-emerald-700 bg-emerald-50",
  critica: "text-amber-700 bg-amber-50",
  arquivada: "text-slate-500 bg-slate-100"
};

export default function UnitDetailPage() {
  const params = useParams<{ id: string }>();
  const [unit, setUnit] = useState<UnitDetail | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [tab, setTab] = useState<"licencas" | "residuos">("licencas");

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchUnitById(params.id);
        setUnit(data);
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
        <Loader2 className="h-4 w-4 animate-spin" /> carregando unidade...
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

  if (!unit) {
    return <p className="text-sm text-slate-500">Unidade não encontrada.</p>;
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-2">
        <h1 className="text-2xl font-semibold text-slate-900">{unit.name}</h1>
        <div className="flex items-center gap-3 text-sm text-slate-500">
          <MapPin className="h-4 w-4" /> {unit.city}/{unit.state}
          <span className="flex items-center gap-2 rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">
            <Leaf className="h-4 w-4" /> Gestor: {unit.manager}
          </span>
        </div>
      </div>

      <div className="flex gap-2">
        <button
          className={`rounded-full px-4 py-2 text-xs font-semibold ${
            tab === "licencas" ? "bg-emerald-100 text-emerald-800" : "bg-slate-100 text-slate-600"
          }`}
          onClick={() => setTab("licencas")}
        >
          Licenças ({unit.licenses.length})
        </button>
        <button
          className={`rounded-full px-4 py-2 text-xs font-semibold ${
            tab === "residuos" ? "bg-emerald-100 text-emerald-800" : "bg-slate-100 text-slate-600"
          }`}
          onClick={() => setTab("residuos")}
        >
          Resíduos (em breve)
        </button>
      </div>

      {tab === "licencas" ? (
        <Card className="p-4 lg:p-6">
          <div className="grid gap-3 md:grid-cols-2">
            {unit.licenses.map((license) => (
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
                  <span className={`rounded-full px-3 py-1 text-xs font-semibold ${statusTone[license.status]}`}>
                    {license.status.toUpperCase()}
                  </span>
                </div>
                <div className="mt-3 flex items-center gap-2 text-xs text-slate-500">
                  <Clock className="h-4 w-4" /> Vencimento {license.expiresAt}
                </div>
                <div className="mt-3 flex items-center gap-2 text-xs text-slate-500">
                  <FileText className="h-4 w-4" /> {license.documents.length} documentos
                </div>
              </Link>
            ))}
          </div>
        </Card>
      ) : (
        <Card className="p-6">
          <p className="text-sm font-semibold text-slate-900">Resíduos (em breve)</p>
          <p className="text-sm text-slate-500">Esta aba mostrará PGRS, MTR e cadeia de evidências em breve.</p>
        </Card>
      )}
    </div>
  );
}

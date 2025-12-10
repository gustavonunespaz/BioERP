"use client";

import { useEffect, useState } from "react";
import { AlertCircle, BellRing, CheckCircle, Loader2 } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { fetchAlerts, markAlertRead } from "@/lib/services/api-client";
import { Alert } from "@/lib/types";

const severityColor: Record<Alert["severity"], string> = {
  info: "bg-slate-100 text-slate-700",
  warning: "bg-amber-50 text-amber-700",
  critical: "bg-red-50 text-red-700"
};

export default function AlertasPage() {
  const [alerts, setAlerts] = useState<Alert[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [updatingId, setUpdatingId] = useState<string | null>(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const response = await fetchAlerts();
        setAlerts(response);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  async function handleMarkRead(id: string) {
    try {
      setUpdatingId(id);
      const updated = await markAlertRead(id);
      setAlerts((prev) => prev.map((alert) => (alert.id === id ? updated : alert)));
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setUpdatingId(null);
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-2">
        <h1 className="text-2xl font-semibold text-slate-900">Central de alertas</h1>
        <p className="text-sm text-slate-500">Monitore vencimentos, documentos e avisos críticos.</p>
      </div>

      <Card className="p-4 lg:p-6">
        {loading && (
          <div className="flex items-center gap-2 text-sm text-slate-600">
            <Loader2 className="h-4 w-4 animate-spin" /> carregando alertas...
          </div>
        )}
        {error && (
          <div className="mb-3 flex items-center gap-2 rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-700">
            <AlertCircle className="h-4 w-4" /> {error}
          </div>
        )}
        {!loading && !error && alerts.length === 0 ? (
          <p className="text-sm text-slate-500">Nenhum alerta pendente.</p>
        ) : null}

        <div className="space-y-3">
          {alerts.map((alert) => (
            <div key={alert.id} className="flex items-start justify-between rounded-xl border border-slate-100 bg-white p-4">
              <div className="space-y-1">
                <div className="flex items-center gap-2">
                  <span className={`rounded-full px-3 py-1 text-xs font-semibold ${severityColor[alert.severity]}`}>
                    {alert.category ?? "Alerta"}
                  </span>
                  {!alert.read && <span className="badge-dot" />}
                </div>
                <p className="text-sm font-semibold text-slate-900">{alert.title}</p>
                <p className="text-xs text-slate-500">{alert.description}</p>
                <p className="text-xs text-slate-400">
                  {alert.createdAt ? new Date(alert.createdAt).toLocaleString() : "Sem data"}
                </p>
              </div>
              <div className="flex items-center gap-2">
                {alert.read ? (
                  <span className="flex items-center gap-1 rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">
                    <CheckCircle className="h-4 w-4" /> Lido
                  </span>
                ) : (
                  <Button size="sm" variant="outline" onClick={() => handleMarkRead(alert.id)} disabled={updatingId === alert.id}>
                    {updatingId === alert.id ? <Loader2 className="h-4 w-4 animate-spin" /> : <BellRing className="h-4 w-4" />} Marcar como lido
                  </Button>
                )}
              </div>
            </div>
          ))}
        </div>
      </Card>
    </div>
  );
}

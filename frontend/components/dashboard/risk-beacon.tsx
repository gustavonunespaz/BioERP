import type { ReactNode } from "react";

import { Activity, AlertTriangle, CheckCircle2, Circle } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import type { RiskPanel } from "@/lib/data/dashboard";
import { cn } from "@/lib/utils";

interface RiskBeaconProps {
  panel: RiskPanel;
}

const toneStyles: Record<string, string> = {
  positive: "bg-emerald-50 text-emerald-700",
  neutral: "bg-amber-50 text-amber-700",
  alert: "bg-rose-50 text-rose-700"
};

const statusIcon: Record<RiskPanel["highlights"][number]["status"], ReactNode> = {
  ok: <CheckCircle2 className="h-4 w-4 text-emerald-600" />,
  attention: <Activity className="h-4 w-4 text-amber-600" />,
  critical: <AlertTriangle className="h-4 w-4 text-rose-600" />
};

export function RiskBeacon({ panel }: RiskBeaconProps) {
  return (
    <Card className="relative h-full overflow-hidden border border-emerald-100 bg-white/80 shadow-soft">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-4">
        <div>
          <p className="text-xs uppercase tracking-[0.3em] text-emerald-600">Farol de risco</p>
          <CardTitle className="text-xl text-slate-900">{panel.scope}</CardTitle>
        </div>
        <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">Assinatura BioERP</span>
      </CardHeader>
      <CardContent className="space-y-4">
        <div className="grid grid-cols-3 gap-3">
          {panel.buckets.map((bucket) => (
            <div key={bucket.label} className={cn("rounded-2xl border p-4 text-center shadow-sm", bucket.tone === "alert" ? "border-rose-100" : "border-emerald-100")}
            >
              <div className={cn("mx-auto flex h-10 w-10 items-center justify-center rounded-full", toneStyles[bucket.tone])}>
                <Circle className="h-5 w-5" />
              </div>
              <p className="mt-2 text-lg font-semibold text-slate-900">{bucket.count}</p>
              <p className="text-sm font-semibold text-slate-700">{bucket.label}</p>
              <p className="text-xs text-slate-500">{bucket.description}</p>
            </div>
          ))}
        </div>

        <div className="space-y-2 rounded-2xl border border-slate-100 bg-slate-50/60 p-4">
          <p className="text-xs font-semibold uppercase tracking-[0.25em] text-slate-500">Por cliente/empreendimento</p>
          <div className="space-y-3">
            {panel.highlights.map((item) => (
              <div key={item.name} className="flex items-center justify-between rounded-xl bg-white px-4 py-3 shadow-sm">
                <div className="space-y-1">
                  <p className="text-sm font-semibold text-slate-900">{item.name}</p>
                  <p className="text-xs text-slate-500">{item.nextStep}</p>
                </div>
                <div className="flex items-center gap-3">
                  <span className="text-xs font-semibold text-slate-500">{item.due}</span>
                  <span
                    className={cn(
                      "inline-flex items-center gap-2 rounded-full px-3 py-1 text-xs font-semibold",
                      item.status === "ok"
                        ? "bg-emerald-50 text-emerald-700"
                        : item.status === "attention"
                          ? "bg-amber-50 text-amber-700"
                          : "bg-rose-50 text-rose-700"
                    )}
                  >
                    {statusIcon[item.status]} {item.owner}
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}

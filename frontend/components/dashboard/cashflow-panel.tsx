import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { TrendingUp } from "lucide-react";

const bars = [62, 78, 90, 74, 68, 82, 96];

export function CashflowPanel() {
  return (
    <Card className="h-full">
      <CardHeader className="flex flex-row items-start justify-between space-y-0 pb-4">
        <div>
          <p className="text-xs uppercase tracking-[0.25em] text-emerald-600">Fluxo de caixa diário</p>
          <CardTitle className="text-lg text-slate-800">Curva projetada com consistência</CardTitle>
        </div>
        <Badge variant="default" className="gap-1">
          <TrendingUp className="h-4 w-4" /> Atualizado
        </Badge>
      </CardHeader>
      <CardContent className="space-y-3">
        <div className="flex items-end justify-between gap-2 rounded-2xl bg-gradient-to-t from-emerald-50 via-white to-white px-4 pb-4 pt-6">
          {bars.map((height, index) => (
            <div key={index} className="relative flex h-40 w-full items-end">
              <div className="w-full rounded-xl bg-emerald-500/70" style={{ height: `${height}%` }} />
            </div>
          ))}
        </div>
        <div className="flex items-center gap-2 text-sm text-slate-600">
          <span className="badge-dot" /> Saldo crescente com governança forte
        </div>
      </CardContent>
    </Card>
  );
}

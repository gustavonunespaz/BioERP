import { ArrowUpRight, AlertTriangle, TrendingUp } from "lucide-react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import type { MetricCard } from "@/lib/data/dashboard";
import { cn } from "@/lib/utils";

export function MetricCard({ title, value, detail, trend, tone = "neutral" }: MetricCard) {
  const toneClasses = {
    positive: "bg-emerald-50 text-emerald-700",
    neutral: "bg-slate-50 text-slate-600",
    alert: "bg-amber-50 text-amber-700"
  } satisfies Record<typeof tone, string>;

  return (
    <Card className="card-sheen relative overflow-hidden border-0">
      <div className="absolute inset-0 opacity-60" aria-hidden />
      <CardHeader className="relative space-y-3">
        <div className="inline-flex items-center gap-2 rounded-full bg-white/60 px-3 py-1 text-xs font-semibold text-slate-600">
          <TrendingUp className="h-4 w-4 text-emerald-500" /> {trend}
        </div>
        <CardTitle className="text-base text-slate-800">{title}</CardTitle>
        <CardDescription className="text-xs uppercase tracking-[0.3em] text-emerald-600">Camada financeira</CardDescription>
      </CardHeader>
      <CardContent className="relative flex items-end justify-between gap-2 pt-2">
        <div>
          <p className="text-3xl font-semibold text-slate-900">{value}</p>
          <p className="text-sm text-slate-500">{detail}</p>
        </div>
        <span className={cn("flex items-center gap-1 rounded-full px-3 py-1 text-xs font-semibold", toneClasses[tone])}>
          {tone === "alert" ? <AlertTriangle className="h-4 w-4" /> : <ArrowUpRight className="h-4 w-4" />} {tone === "alert" ? "Risco" : "Saudável"}
        </span>
      </CardContent>
    </Card>
  );
}

import { ArrowDownRight, ArrowUpRight, AlertTriangle, Dot } from "lucide-react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import type { MetricCard } from "@/lib/data/dashboard";
import { cn } from "@/lib/utils";

export function MetricCard({ title, value, detail, change, changeDirection, category, tone = "neutral" }: MetricCard) {
  const toneClasses = {
    positive: "bg-emerald-50 text-emerald-700",
    neutral: "bg-slate-50 text-slate-600",
    alert: "bg-amber-50 text-amber-700"
  } satisfies Record<typeof tone, string>;

  return (
    <Card className="relative overflow-hidden border border-white/40 bg-white/80 shadow-soft">
      <div className="absolute inset-0 card-sheen opacity-70" aria-hidden />
      <CardHeader className="relative flex flex-row items-start justify-between space-y-0 pb-2">
        <div className="space-y-2">
          <span className="inline-flex items-center gap-2 rounded-full bg-slate-50/80 px-3 py-1 text-[11px] font-semibold text-slate-600">
            <Dot className="h-5 w-5 text-emerald-500" /> {category}
          </span>
          <CardTitle className="text-lg font-semibold text-slate-900">{title}</CardTitle>
          <CardDescription className="text-sm text-slate-500">{detail}</CardDescription>
        </div>
        <span className={cn("flex items-center gap-1 rounded-full px-3 py-1 text-xs font-semibold shadow-sm", toneClasses[tone])}>
          {tone === "alert" ? <AlertTriangle className="h-4 w-4" /> : <ArrowUpRight className="h-4 w-4" />} {tone === "alert" ? "Risco" : "Saudável"}
        </span>
      </CardHeader>
      <CardContent className="relative flex items-end justify-between gap-2">
        <div className="space-y-1">
          <p className="text-3xl font-semibold text-slate-900">{value}</p>
          <p className="text-sm text-slate-500">{detail}</p>
        </div>
        <div
          className={cn(
            "flex items-center gap-2 rounded-full px-3 py-1 text-xs font-semibold",
            changeDirection === "up" ? "bg-emerald-50 text-emerald-700" : "bg-amber-50 text-amber-700"
          )}
        >
          {changeDirection === "up" ? <ArrowUpRight className="h-4 w-4" /> : <ArrowDownRight className="h-4 w-4" />} {change}
        </div>
      </CardContent>
    </Card>
  );
}

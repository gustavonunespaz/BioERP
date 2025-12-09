import { ArrowRightLeft, BarChart3, Wallet } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import type { FinanceBar, ServiceBreakdown } from "@/lib/data/dashboard";

interface FinanceSummaryProps {
  bars: FinanceBar[];
  breakdown: ServiceBreakdown[];
}

export function FinanceSummary({ bars, breakdown }: FinanceSummaryProps) {
  return (
    <Card className="h-full border border-white/50 bg-white/80 shadow-soft">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-4">
        <div>
          <p className="text-xs uppercase tracking-[0.3em] text-emerald-600">Financeiro resumido</p>
          <CardTitle className="text-xl text-slate-900">Receber x pagar e serviços</CardTitle>
        </div>
        <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">Emitir cobrança</span>
      </CardHeader>
      <CardContent className="space-y-4">
        <div className="rounded-2xl border border-slate-100 bg-gradient-to-r from-emerald-50 via-white to-white p-4 shadow-sm">
          <div className="flex items-center justify-between text-sm font-semibold text-slate-700">
            <div className="flex items-center gap-2">
              <ArrowRightLeft className="h-4 w-4 text-emerald-600" /> Receber x pagar (30 dias)
            </div>
            <span className="text-xs text-slate-500">Valores em milhares</span>
          </div>
          <div className="mt-4 grid grid-cols-4 gap-3">
            {bars.map((bar) => (
              <div key={bar.label} className="space-y-2 rounded-xl bg-white/70 p-3 shadow-sm">
                <p className="text-xs font-semibold text-slate-500">{bar.label}</p>
                <div className="space-y-1">
                  <div className="flex items-center gap-2 text-emerald-700">
                    <span className="h-2 w-2 rounded-full bg-emerald-500" />
                    <p className="text-sm font-semibold">{bar.receivable}k</p>
                  </div>
                  <div className="flex items-center gap-2 text-slate-600">
                    <span className="h-2 w-2 rounded-full bg-slate-400" />
                    <p className="text-sm font-semibold">{bar.payable}k</p>
                  </div>
                  <div className="mt-2 h-2 rounded-full bg-slate-100">
                    <div className="h-2 rounded-full bg-gradient-to-r from-emerald-500 to-emerald-400" style={{ width: `${bar.receivable}%` }} />
                  </div>
                  <div className="h-2 rounded-full bg-slate-100">
                    <div className="h-2 rounded-full bg-slate-300" style={{ width: `${bar.payable}%` }} />
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="rounded-2xl border border-slate-100 bg-slate-50/60 p-4 shadow-sm">
          <div className="flex items-center justify-between text-sm font-semibold text-slate-700">
            <div className="flex items-center gap-2">
              <BarChart3 className="h-4 w-4 text-emerald-600" /> Faturamento por serviço
            </div>
            <span className="inline-flex items-center gap-2 rounded-full bg-white px-3 py-1 text-xs font-semibold text-slate-600">
              <Wallet className="h-4 w-4 text-emerald-600" /> Serviços premium
            </span>
          </div>
          <div className="mt-4 grid gap-3 md:grid-cols-2">
            {breakdown.map((item) => (
              <div key={item.service} className="flex items-center justify-between rounded-xl bg-white px-4 py-3 shadow-sm">
                <div>
                  <p className="text-sm font-semibold text-slate-900">{item.service}</p>
                  <p className="text-xs text-slate-500">{item.value}</p>
                </div>
                <div className="flex items-center gap-3">
                  <div className="h-2 w-20 rounded-full bg-slate-100">
                    <div className="h-2 rounded-full bg-gradient-to-r from-emerald-500 to-emerald-400" style={{ width: `${item.percent}%` }} />
                  </div>
                  <span className="text-sm font-semibold text-slate-700">{item.percent}%</span>
                </div>
              </div>
            ))}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}

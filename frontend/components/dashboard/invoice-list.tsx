import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import type { InvoiceCard } from "@/lib/data/dashboard";
import { cn } from "@/lib/utils";
import { ArrowRight, CheckCircle2, Clock3 } from "lucide-react";

interface InvoiceListProps {
  title: string;
  items: InvoiceCard[];
}

const statusMap: Record<InvoiceCard["status"], { label: string; classes: string; icon: React.ReactNode }> = {
  due: { label: "Vencido", classes: "text-amber-700 bg-amber-50", icon: <Clock3 className="h-4 w-4" /> },
  scheduled: { label: "Agendado", classes: "text-slate-700 bg-slate-100", icon: <Clock3 className="h-4 w-4" /> },
  paid: { label: "Pago", classes: "text-emerald-700 bg-emerald-50", icon: <CheckCircle2 className="h-4 w-4" /> }
};

export function InvoiceList({ title, items }: InvoiceListProps) {
  return (
    <Card className="h-full">
      <CardHeader className="flex flex-row items-start justify-between space-y-0 pb-4">
        <div>
          <p className="text-xs uppercase tracking-[0.25em] text-emerald-600">Prioridade inteligente</p>
          <CardTitle className="text-lg text-slate-800">{title}</CardTitle>
        </div>
        <Badge variant="secondary">Ações diretas</Badge>
      </CardHeader>
      <CardContent className="space-y-3">
        {items.map((invoice) => (
          <div key={invoice.title} className="flex items-center justify-between rounded-xl border border-slate-100 bg-slate-50/60 p-4 transition hover:border-emerald-100 hover:bg-white">
            <div>
              <p className="text-sm font-semibold text-slate-800">{invoice.title}</p>
              <p className="text-xs text-slate-500">{invoice.dueLabel}</p>
            </div>
            <div className="flex items-center gap-3 text-right">
              <div>
                <p className="text-lg font-semibold text-slate-900">{invoice.amount}</p>
                <p className={cn("inline-flex items-center gap-2 rounded-full px-3 py-1 text-[11px] font-semibold", statusMap[invoice.status].classes)}>
                  {statusMap[invoice.status].icon} {statusMap[invoice.status].label}
                </p>
              </div>
              <span className="flex h-9 w-9 items-center justify-center rounded-xl border border-slate-200 text-slate-400">
                <ArrowRight className="h-4 w-4" />
              </span>
            </div>
          </div>
        ))}
      </CardContent>
    </Card>
  );
}

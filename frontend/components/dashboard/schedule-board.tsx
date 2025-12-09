import { CalendarClock, ChevronRight } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import type { AgendaBoard } from "@/lib/data/dashboard";

interface ScheduleBoardProps {
  boards: AgendaBoard[];
}

export function ScheduleBoard({ boards }: ScheduleBoardProps) {
  return (
    <Card className="h-full border border-white/50 bg-white/80 shadow-soft">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-4">
        <div>
          <p className="text-xs uppercase tracking-[0.3em] text-emerald-600">Agenda e prazos</p>
          <CardTitle className="text-xl text-slate-900">Vencimentos e entregas</CardTitle>
        </div>
        <span className="inline-flex items-center gap-2 rounded-full bg-slate-50 px-3 py-1 text-xs font-semibold text-slate-600">
          <CalendarClock className="h-4 w-4 text-emerald-600" /> Hoje / 7 / 30 dias
        </span>
      </CardHeader>
      <CardContent className="space-y-3">
        {boards.map((board) => (
          <div key={board.timeframe} className="rounded-2xl border border-slate-100 bg-slate-50/60 p-4">
            <div className="flex items-center justify-between">
              <p className="text-sm font-semibold text-slate-800">{board.label}</p>
              <span className="text-xs font-semibold text-emerald-700">Prioridade dinâmica</span>
            </div>
            <div className="mt-3 space-y-2">
              {board.items.map((item) => (
                <div
                  key={item.title}
                  className="flex items-center justify-between rounded-xl bg-white px-3 py-3 text-sm shadow-sm transition hover:-translate-y-[1px] hover:shadow-md"
                >
                  <div className="space-y-0.5">
                    <p className="font-semibold text-slate-900">{item.title}</p>
                    <p className="text-xs text-slate-500">{item.category} • Responsável {item.owner}</p>
                  </div>
                  <div className="flex items-center gap-3 text-xs font-semibold text-slate-600">
                    <span className="rounded-full bg-emerald-50 px-3 py-1 text-emerald-700">{item.dueLabel}</span>
                    <ChevronRight className="h-4 w-4 text-slate-400" />
                  </div>
                </div>
              ))}
            </div>
          </div>
        ))}
      </CardContent>
    </Card>
  );
}

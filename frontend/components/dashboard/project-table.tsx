import type { ReactNode } from "react";

import { ArrowRight, CheckCircle2, CircleDot, Clock3 } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import type { ProjectRow } from "@/lib/data/dashboard";
import { cn } from "@/lib/utils";

interface ProjectTableProps {
  projects: ProjectRow[];
}

const statusStyles: Record<ProjectRow["status"], { label: string; classes: string; icon: ReactNode }> = {
  "em andamento": { label: "Em andamento", classes: "bg-emerald-50 text-emerald-700", icon: <CheckCircle2 className="h-4 w-4" /> },
  "em risco": { label: "Crítico", classes: "bg-rose-50 text-rose-700", icon: <CircleDot className="h-4 w-4" /> },
  aguardando: { label: "Aguardando", classes: "bg-slate-100 text-slate-700", icon: <Clock3 className="h-4 w-4" /> }
};

export function ProjectTable({ projects }: ProjectTableProps) {
  return (
    <Card className="h-full border border-white/50 bg-white/80 shadow-soft">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-4">
        <div>
          <p className="text-xs uppercase tracking-[0.3em] text-emerald-600">Projetos em andamento</p>
          <CardTitle className="text-xl text-slate-900">Timeline e responsáveis</CardTitle>
        </div>
        <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">Ações rápidas</span>
      </CardHeader>
      <CardContent className="overflow-hidden rounded-2xl border border-slate-100 bg-slate-50/60">
        <div className="grid grid-cols-5 bg-white px-4 py-3 text-xs font-semibold uppercase tracking-[0.2em] text-slate-500">
          <span>Projeto</span>
          <span>Cliente</span>
          <span>Status</span>
          <span>Próximo marco</span>
          <span className="text-right">Responsável</span>
        </div>
        <div className="divide-y divide-slate-100">
          {projects.map((project) => (
            <div key={project.name} className="grid grid-cols-5 items-center bg-white/80 px-4 py-3 text-sm">
              <p className="font-semibold text-slate-900">{project.name}</p>
              <p className="text-slate-600">{project.client}</p>
              <div className="flex items-center gap-2">
                <span className={cn("inline-flex items-center gap-2 rounded-full px-3 py-1 text-xs font-semibold", statusStyles[project.status].classes)}>
                  {statusStyles[project.status].icon} {statusStyles[project.status].label}
                </span>
              </div>
              <p className="text-slate-700">{project.nextMilestone}</p>
              <div className="flex items-center justify-end gap-2 text-emerald-700">
                <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold">{project.owner}</span>
                <ArrowRight className="h-4 w-4" />
              </div>
            </div>
          ))}
        </div>
      </CardContent>
    </Card>
  );
}

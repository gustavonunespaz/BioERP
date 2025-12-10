import { ActivityFeed } from "@/components/dashboard/activity-feed";
import { DocumentFeed } from "@/components/dashboard/document-feed";
import { FinanceSummary } from "@/components/dashboard/finance-summary";
import { MetricCard } from "@/components/dashboard/metric-card";
import { ProjectTable } from "@/components/dashboard/project-table";
import { RiskBeacon } from "@/components/dashboard/risk-beacon";
import { ScheduleBoard } from "@/components/dashboard/schedule-board";
import {
  activities,
  agendaBoard,
  documents,
  financeBars,
  metrics,
  projectRows,
  riskPanel,
  serviceBreakdown
} from "@/lib/data/dashboard";

export default function DashboardPage() {
  return (
    <div className="space-y-6">
      <section className="grid grid-cols-1 gap-4 xl:grid-cols-12">
        {metrics.length === 0 ? (
          <div className="xl:col-span-12">
            <div className="rounded-xl border border-slate-100 bg-white p-6 text-center text-sm text-slate-600">
              Nenhum KPI configurado ainda. Cadastre dados reais para visualizar os painéis.
            </div>
          </div>
        ) : (
          metrics.map((metric) => (
            <div key={metric.title} className="xl:col-span-3">
              <MetricCard {...metric} />
            </div>
          ))
        )}
      </section>

      <section className="grid grid-cols-1 gap-4 xl:grid-cols-12">
        <div className="xl:col-span-5">
          {riskPanel.buckets.length === 0 ? (
            <div className="rounded-xl border border-slate-100 bg-white p-6 text-sm text-slate-600">Painel de risco vazio.</div>
          ) : (
            <RiskBeacon panel={riskPanel} />
          )}
        </div>
        <div className="xl:col-span-7">
          {agendaBoard.length === 0 ? (
            <div className="rounded-xl border border-slate-100 bg-white p-6 text-sm text-slate-600">Agenda sem itens.</div>
          ) : (
            <ScheduleBoard boards={agendaBoard} />
          )}
        </div>
      </section>

      <section className="grid grid-cols-1 gap-4 xl:grid-cols-12">
        <div className="xl:col-span-7">
          {projectRows.length === 0 ? (
            <div className="rounded-xl border border-slate-100 bg-white p-6 text-sm text-slate-600">Nenhum projeto disponível.</div>
          ) : (
            <ProjectTable projects={projectRows} />
          )}
        </div>
        <div className="xl:col-span-5">
          {financeBars.length === 0 ? (
            <div className="rounded-xl border border-slate-100 bg-white p-6 text-sm text-slate-600">Fluxo financeiro aguardando dados.</div>
          ) : (
            <FinanceSummary bars={financeBars} breakdown={serviceBreakdown} />
          )}
        </div>
      </section>

      <section className="grid grid-cols-1 gap-4 xl:grid-cols-12">
        <div className="xl:col-span-7">
          {documents.length === 0 ? (
            <div className="rounded-xl border border-slate-100 bg-white p-6 text-sm text-slate-600">Sem documentos recentes.</div>
          ) : (
            <DocumentFeed documents={documents} />
          )}
        </div>
        <div className="xl:col-span-5">
          {activities.length === 0 ? (
            <div className="rounded-xl border border-slate-100 bg-white p-4 text-sm text-slate-600">Nenhuma atividade registrada.</div>
          ) : (
            <ActivityFeed activities={activities} />
          )}
        </div>
      </section>
    </div>
  );
}

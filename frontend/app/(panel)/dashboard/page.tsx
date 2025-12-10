import { ActivityFeed } from "@/components/dashboard/activity-feed";
import { CashflowPanel } from "@/components/dashboard/cashflow-panel";
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
        {metrics.map((metric) => (
          <div key={metric.title} className="xl:col-span-3">
            <MetricCard {...metric} />
          </div>
        ))}
      </section>

      <section className="grid grid-cols-1 gap-4 xl:grid-cols-12">
        <div className="xl:col-span-5">
          <RiskBeacon panel={riskPanel} />
        </div>
        <div className="xl:col-span-7">
          <ScheduleBoard boards={agendaBoard} />
        </div>
      </section>

      <section className="grid grid-cols-1 gap-4 xl:grid-cols-12">
        <div className="xl:col-span-7">
          <ProjectTable projects={projectRows} />
        </div>
        <div className="xl:col-span-5">
          <FinanceSummary bars={financeBars} breakdown={serviceBreakdown} />
        </div>
      </section>

      <section className="grid grid-cols-1 gap-4 xl:grid-cols-12">
        <div className="xl:col-span-7">
          <DocumentFeed documents={documents} />
        </div>
        <div className="xl:col-span-5 space-y-4">
          <CashflowPanel />
          <ActivityFeed activities={activities} />
        </div>
      </section>
    </div>
  );
}

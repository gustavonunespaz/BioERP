import { ActivityFeed } from "@/components/dashboard/activity-feed";
import { CashflowPanel } from "@/components/dashboard/cashflow-panel";
import { DocumentFeed } from "@/components/dashboard/document-feed";
import { FinanceSummary } from "@/components/dashboard/finance-summary";
import { MetricCard } from "@/components/dashboard/metric-card";
import { ProjectTable } from "@/components/dashboard/project-table";
import { RiskBeacon } from "@/components/dashboard/risk-beacon";
import { ScheduleBoard } from "@/components/dashboard/schedule-board";
import { Sidebar } from "@/components/layout/sidebar";
import { TopBar } from "@/components/layout/top-bar";
import {
  metrics,
  riskPanel,
  agendaBoard,
  projectRows,
  financeBars,
  serviceBreakdown,
  documents,
  activities
} from "@/lib/data/dashboard";

export default function Home() {
  return (
    <div className="mx-auto max-w-[1440px] space-y-6 px-4 py-8 lg:px-8">
      <div className="flex flex-col gap-6 lg:flex-row">
        <Sidebar />
        <div className="flex-1 space-y-6">
          <TopBar />
          <section className="grid grid-cols-1 gap-4 xl:grid-cols-12">
            {metrics.map((metric) => (
              <div key={metric.title} className="xl:col-span-2">
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
      </div>
    </div>
  );
}

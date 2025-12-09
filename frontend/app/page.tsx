import { ActivityFeed } from "@/components/dashboard/activity-feed";
import { CashflowPanel } from "@/components/dashboard/cashflow-panel";
import { Hero } from "@/components/dashboard/hero";
import { InvoiceList } from "@/components/dashboard/invoice-list";
import { MetricCard } from "@/components/dashboard/metric-card";
import { Sidebar } from "@/components/layout/sidebar";
import { TopBar } from "@/components/layout/top-bar";
import { metrics, payables, receivables, activities } from "@/lib/data/dashboard";

export default function Home() {
  return (
    <div className="mx-auto max-w-[1400px] space-y-6 px-4 py-8 lg:px-8">
      <div className="flex flex-col gap-6 lg:flex-row">
        <Sidebar />
        <div className="flex-1 space-y-6">
          <TopBar />
          <Hero />
          <section className="metric-grid">
            {metrics.map((metric) => (
              <MetricCard key={metric.title} {...metric} />
            ))}
          </section>
          <section className="grid gap-4 lg:grid-cols-2">
            <InvoiceList title="A receber" items={receivables} />
            <InvoiceList title="A pagar" items={payables} />
          </section>
          <section className="grid gap-4 lg:grid-cols-2">
            <CashflowPanel />
            <ActivityFeed activities={activities} />
          </section>
        </div>
      </div>
    </div>
  );
}

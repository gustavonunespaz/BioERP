import './styles.css';
import { Navigation } from './components/Navigation';
import { MetricCardTile } from './components/MetricCard';
import { activities, metrics, navigationLinks, payables, receivables } from './data/dashboardContent';

type Tone = 'positive' | 'neutral' | 'alert';

function StatusPill({ label, tone = 'neutral' }: { label: string; tone?: Tone }) {
  return <span className={`pill ${tone}`}>{label}</span>;
}

function InvoiceList({ title, items }: { title: string; items: typeof receivables }) {
  return (
    <section className="card list-card">
      <header className="card-header">
        <div>
          <p className="eyebrow">{title}</p>
          <h3>Prioridade inteligente</h3>
        </div>
        <StatusPill label="Ações diretas" tone="positive" />
      </header>
      <ul className="invoice-list">
        {items.map((invoice) => (
          <li key={invoice.title} className={`invoice ${invoice.status}`}>
            <div>
              <p className="invoice-title">{invoice.title}</p>
              <span className="invoice-due">{invoice.dueLabel}</span>
            </div>
            <div className="invoice-meta">
              <strong>{invoice.amount}</strong>
              <span className="status">{invoice.status === 'paid' ? 'Pago' : invoice.status === 'due' ? 'Vencido' : 'Agendado'}</span>
            </div>
          </li>
        ))}
      </ul>
    </section>
  );
}

function ActivityFeed() {
  return (
    <section className="card list-card">
      <header className="card-header">
        <div>
          <p className="eyebrow">Fluxo de trabalho</p>
          <h3>Trilhas ACID & SOLID</h3>
        </div>
        <StatusPill label="Auditável" tone="neutral" />
      </header>
      <ul className="activity-list">
        {activities.map((activity) => (
          <li key={activity.title} className={activity.tone ?? 'neutral'}>
            <div>
              <p className="activity-title">{activity.title}</p>
              <span className="activity-subtitle">{activity.subtitle}</span>
            </div>
            <StatusPill label={activity.badge} tone={activity.tone} />
          </li>
        ))}
      </ul>
    </section>
  );
}

function CashFlowPanel() {
  return (
    <section className="card cashflow-card">
      <header className="card-header">
        <div>
          <p className="eyebrow">Fluxo de caixa diário</p>
          <h3>Curva projetada com consistência</h3>
        </div>
        <StatusPill label="Atualizado" tone="positive" />
      </header>
      <div className="chart">
        <div className="bar" style={{ height: '62%' }} />
        <div className="bar" style={{ height: '78%' }} />
        <div className="bar" style={{ height: '90%' }} />
        <div className="bar" style={{ height: '74%' }} />
        <div className="bar" style={{ height: '68%' }} />
        <div className="bar" style={{ height: '82%' }} />
        <div className="bar" style={{ height: '96%' }} />
      </div>
      <div className="chart-legend">
        <span className="legend-dot" />
        <p>Saldo crescente com governança forte</p>
      </div>
    </section>
  );
}

function App() {
  return (
    <div className="app-shell">
      <Navigation items={navigationLinks} />

      <main className="content">
        <header className="topbar">
          <div>
            <p className="eyebrow">BioERP • Consultoria ambiental</p>
            <h1>Controle financeiro, projetos e compliance em um só painel</h1>
          </div>
          <div className="topbar-actions">
            <button className="ghost">Compartilhar</button>
            <button className="primary">Nova cobrança</button>
          </div>
        </header>

        <section className="hero">
          <div>
            <p className="pill positive">Clean Architecture</p>
            <h2>Estrutura modular com ACID, SOLID e código limpo</h2>
            <p>
              Frameworks integrados, rastreabilidade de dados e UX inspirada em Conta Azul com paleta de verdes e
              transições suaves para orientar decisões do cliente.
            </p>
            <div className="cta">
              <a className="primary" href="#receber">Receber</a>
              <a className="secondary" href="#projetos">Projetos</a>
            </div>
          </div>
          <div className="hero-highlight">
            <p>Disponibilidade</p>
            <strong>99,95%</strong>
            <span>Observabilidade e testes automatizados</span>
          </div>
        </section>

        <section className="grid metrics">
          {metrics.map((metric) => (
            <MetricCardTile key={metric.title} {...metric} />
          ))}
        </section>

        <section className="split" id="receber">
          <InvoiceList title="A receber" items={receivables} />
          <InvoiceList title="A pagar" items={payables} />
        </section>

        <section className="split" id="projetos">
          <CashFlowPanel />
          <ActivityFeed />
        </section>
      </main>
    </div>
  );
}

export default App;

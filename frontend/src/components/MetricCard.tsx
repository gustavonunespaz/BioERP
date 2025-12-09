import { MetricCard } from '../data/dashboardContent';

type MetricCardProps = MetricCard;

export function MetricCardTile({ title, value, detail, trend, tone = 'neutral' }: MetricCardProps) {
  return (
    <article className={`card metric ${tone}`}>
      <header>
        <p className="eyebrow">{title}</p>
        <span className="material-symbols-rounded" aria-hidden>
          trending_up
        </span>
      </header>
      <div className="metric-body">
        <strong>{value}</strong>
        <p>{detail}</p>
      </div>
      <footer>
        <span className="trend">{trend}</span>
      </footer>
    </article>
  );
}

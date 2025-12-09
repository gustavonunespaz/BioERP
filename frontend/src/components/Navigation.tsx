import { NavItem } from '../data/dashboardContent';

type NavigationProps = {
  items: NavItem[];
};

export function Navigation({ items }: NavigationProps) {
  return (
    <aside className="sidebar">
      <div className="brand">
        <div className="brand-icon">B</div>
        <div>
          <p className="brand-label">BioERP</p>
          <span className="brand-sub">Arquitetura limpa • ACID</span>
        </div>
      </div>
      <nav>
        <ul>
          {items.map((item) => (
            <li key={item.label} className={item.active ? 'active' : ''}>
              <span className="material-symbols-rounded" aria-hidden>{item.icon}</span>
              <span>{item.label}</span>
            </li>
          ))}
        </ul>
      </nav>
      <div className="sidebar-foot">
        <p>Licenciamento</p>
        <strong>Ambiental Platinum</strong>
      </div>
    </aside>
  );
}

export type NavItem = {
  label: string;
  icon: string;
  active?: boolean;
};

export type MetricCard = {
  title: string;
  value: string;
  detail: string;
  trend: string;
  tone?: "positive" | "neutral" | "alert";
};

export type InvoiceCard = {
  title: string;
  amount: string;
  dueLabel: string;
  status: "due" | "scheduled" | "paid";
};

export type Activity = {
  title: string;
  subtitle: string;
  badge: string;
  tone?: "positive" | "neutral" | "alert";
};

export const navigationLinks: NavItem[] = [
  { label: "Visão geral", icon: "layout-dashboard", active: true },
  { label: "Faturamento", icon: "receipt" },
  { label: "Projetos", icon: "layers" },
  { label: "Financeiro", icon: "wallet" },
  { label: "Clientes", icon: "users" },
  { label: "Ambiental", icon: "leaf" },
  { label: "Relatórios", icon: "bar-chart-3" },
  { label: "Configurações", icon: "settings" }
];

export const metrics: MetricCard[] = [
  {
    title: "Receitas previstas",
    value: "R$ 128.400,00",
    detail: "18 contratos recorrentes ativos",
    trend: "+12,4% vs mês anterior",
    tone: "positive"
  },
  {
    title: "Despesas controladas",
    value: "R$ 82.150,00",
    detail: "Pagamentos provisionados e aprovados",
    trend: "-4,1% em relação a agosto",
    tone: "positive"
  },
  {
    title: "Caixa projetado",
    value: "R$ 46.250,00",
    detail: "Fluxo líquido em 30 dias",
    trend: "ACID • consistência garantida",
    tone: "neutral"
  },
  {
    title: "Compliance ambiental",
    value: "93% em conformidade",
    detail: "MTR, CDF e licenças monitoradas",
    trend: "3 pendências críticas",
    tone: "alert"
  }
];

export const receivables: InvoiceCard[] = [
  { title: "Consultoria Bio Leste", amount: "R$ 17.896,09", dueLabel: "Vencido há 8 dias", status: "due" },
  { title: "Projeto Serra Verde", amount: "R$ 8.000,00", dueLabel: "Vence hoje", status: "due" },
  { title: "Contrato Recorrente Juno", amount: "R$ 12.560,00", dueLabel: "Vence em 5 dias", status: "scheduled" }
];

export const payables: InvoiceCard[] = [
  { title: "Equipamentos campo", amount: "R$ 9.850,00", dueLabel: "Vence em 3 dias", status: "scheduled" },
  { title: "Licenças e taxas", amount: "R$ 3.720,00", dueLabel: "Paga hoje", status: "paid" },
  { title: "Equipe terceirizada", amount: "R$ 6.480,00", dueLabel: "Previsto para 12 dias", status: "scheduled" }
];

export const activities: Activity[] = [
  {
    title: "Checklist de campo concluído",
    subtitle: "Rastreabilidade ACID no envio dos dados",
    badge: "Operação",
    tone: "positive"
  },
  {
    title: "MTR e CDF emitidos",
    subtitle: "Licenciamento e resíduos em conformidade",
    badge: "Ambiental",
    tone: "neutral"
  },
  {
    title: "Auditoria trimestral agendada",
    subtitle: "Checklist SOLID com responsáveis definidos",
    badge: "Governança",
    tone: "alert"
  }
];

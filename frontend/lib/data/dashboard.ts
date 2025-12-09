export type NavItem = {
  label: string;
  icon: string;
  active?: boolean;
};

export type MetricCard = {
  title: string;
  value: string;
  detail: string;
  change: string;
  changeDirection: "up" | "down";
  category: string;
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

export type RiskBucket = {
  label: string;
  description: string;
  tone: "positive" | "neutral" | "alert";
  count: number;
};

export type RiskHighlight = {
  name: string;
  status: "ok" | "attention" | "critical";
  nextStep: string;
  owner: string;
  due: string;
};

export type RiskPanel = {
  scope: string;
  buckets: RiskBucket[];
  highlights: RiskHighlight[];
};

export type AgendaItem = {
  title: string;
  category: string;
  dueLabel: string;
  owner: string;
  timeframe: "today" | "7days" | "30days";
};

export type AgendaBoard = {
  timeframe: "today" | "7days" | "30days";
  label: string;
  items: AgendaItem[];
};

export type ProjectRow = {
  name: string;
  client: string;
  status: "em andamento" | "em risco" | "aguardando";
  nextMilestone: string;
  owner: string;
};

export type FinanceBar = {
  label: string;
  receivable: number;
  payable: number;
};

export type ServiceBreakdown = {
  service: string;
  value: string;
  percent: number;
};

export type DocumentEntry = {
  name: string;
  client: string;
  category: string;
  timeAgo: string;
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
    title: "Licenças a vencer",
    value: "14",
    detail: "30/60/90 dias monitorados",
    change: "+3 nesta semana",
    changeDirection: "up",
    category: "Licenciamento",
    tone: "alert"
  },
  {
    title: "Condicionantes críticas",
    value: "6",
    detail: "Checklist com responsáveis",
    change: "-2 vs mês anterior",
    changeDirection: "down",
    category: "Ambiental",
    tone: "alert"
  },
  {
    title: "Projetos ativos",
    value: "21",
    detail: "Kanban sincronizado",
    change: "+1 hoje",
    changeDirection: "up",
    category: "Operações",
    tone: "positive"
  },
  {
    title: "Faturamento do mês",
    value: "R$ 412.800",
    detail: "Serviços recorrentes",
    change: "+8,4%",
    changeDirection: "up",
    category: "Financeiro",
    tone: "positive"
  },
  {
    title: "Caixa previsto 30 dias",
    value: "R$ 198.200",
    detail: "Receber x pagar",
    change: "+R$ 32.000",
    changeDirection: "up",
    category: "Tesouraria",
    tone: "positive"
  },
  {
    title: "Pendências de documentos",
    value: "9",
    detail: "Uploads aguardando",
    change: "-3 desde ontem",
    changeDirection: "down",
    category: "Compliance",
    tone: "neutral"
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

export const riskPanel: RiskPanel = {
  scope: "Clientes e empreendimentos",
  buckets: [
    { label: "OK", description: "Operação saudável", tone: "positive", count: 18 },
    { label: "Atenção", description: "Prazos próximos", tone: "neutral", count: 7 },
    { label: "Crítico", description: "Risco imediato", tone: "alert", count: 3 }
  ],
  highlights: [
    {
      name: "Parque Solar Horizonte",
      status: "ok",
      nextStep: "Relatório mensal validado",
      owner: "Laura Lima",
      due: "em 4 dias"
    },
    {
      name: "Terminal Logístico Verde",
      status: "attention",
      nextStep: "Vistoria agendada",
      owner: "César Mello",
      due: "em 7 dias"
    },
    {
      name: "Usina Delta Renovação LO",
      status: "critical",
      nextStep: "Condicionante pendente",
      owner: "Bruna Sato",
      due: "hoje"
    }
  ]
};

export const agendaBoard: AgendaBoard[] = [
  {
    timeframe: "today",
    label: "Hoje",
    items: [
      { title: "Renovação LO - Usina Delta", category: "Licença", dueLabel: "14:00", owner: "Bruna", timeframe: "today" },
      { title: "Entrega relatório GRI", category: "Relatório", dueLabel: "17:00", owner: "César", timeframe: "today" }
    ]
  },
  {
    timeframe: "7days",
    label: "7 dias",
    items: [
      {
        title: "Vistoria campo - Linha Norte",
        category: "Vistoria",
        dueLabel: "em 2 dias",
        owner: "Laura",
        timeframe: "7days"
      },
      {
        title: "Coleta trimestral - Efluentes",
        category: "Coleta",
        dueLabel: "em 5 dias",
        owner: "Equipe campo",
        timeframe: "7days"
      }
    ]
  },
  {
    timeframe: "30days",
    label: "30 dias",
    items: [
      {
        title: "Renovação licença Lago Azul",
        category: "Licença",
        dueLabel: "em 18 dias",
        owner: "João",
        timeframe: "30days"
      },
      {
        title: "Checklist ISO 14001",
        category: "Compliance",
        dueLabel: "em 24 dias",
        owner: "Camila",
        timeframe: "30days"
      }
    ]
  }
];

export const projectRows: ProjectRow[] = [
  {
    name: "Usina Delta - Renovação LO",
    client: "Grupo Energia Viva",
    status: "em risco",
    nextMilestone: "Condicionante 12",
    owner: "Bruna Sato"
  },
  {
    name: "Parque Solar Horizonte",
    client: "Luz do Sul",
    status: "em andamento",
    nextMilestone: "Relatório mensal",
    owner: "Laura Lima"
  },
  {
    name: "Terminal Logístico Verde",
    client: "EcoTrans",
    status: "aguardando",
    nextMilestone: "Vistoria Ibama",
    owner: "César Mello"
  },
  {
    name: "Plano de resíduos 2025",
    client: "Cidade Limpa",
    status: "em andamento",
    nextMilestone: "Coleta de evidências",
    owner: "Equipe campo"
  }
];

export const financeBars: FinanceBar[] = [
  { label: "Semana 1", receivable: 42, payable: 28 },
  { label: "Semana 2", receivable: 36, payable: 24 },
  { label: "Semana 3", receivable: 51, payable: 32 },
  { label: "Semana 4", receivable: 44, payable: 30 }
];

export const serviceBreakdown: ServiceBreakdown[] = [
  { service: "Licenciamento", value: "R$ 186k", percent: 42 },
  { service: "Monitoramento", value: "R$ 124k", percent: 28 },
  { service: "Resíduos", value: "R$ 64k", percent: 18 },
  { service: "Outros", value: "R$ 38k", percent: 12 }
];

export const documents: DocumentEntry[] = [
  { name: "MTR - Operação Norte.pdf", client: "EcoTrans", category: "Resíduos", timeAgo: "há 12 min" },
  { name: "Relatório vistorias campo.docx", client: "Luz do Sul", category: "Projetos", timeAgo: "há 35 min" },
  { name: "Renovação LO - Lago Azul.pdf", client: "Grupo Energia Viva", category: "Licença", timeAgo: "há 1h" },
  { name: "Planilha faturamento setembro.xlsx", client: "Interno", category: "Financeiro", timeAgo: "há 2h" }
];

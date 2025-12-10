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

export const navigationLinks: NavItem[] = [];

export const metrics: MetricCard[] = [];

export const receivables: InvoiceCard[] = [];

export const payables: InvoiceCard[] = [];

export const activities: Activity[] = [];

export const riskPanel: RiskPanel = { scope: "", buckets: [], highlights: [] };

export const agendaBoard: AgendaBoard[] = [];

export const projectRows: ProjectRow[] = [];

export const financeBars: FinanceBar[] = [];

export const serviceBreakdown: ServiceBreakdown[] = [];

export const documents: DocumentEntry[] = [];

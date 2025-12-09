import { navigationLinks } from "@/lib/data/dashboard";
import { BadgeCheck, Leaf, Sparkles } from "lucide-react";
import Link from "next/link";

import { cn } from "@/lib/utils";

const iconMap: Record<string, React.ReactNode> = {
  "layout-dashboard": <BadgeCheck className="h-4 w-4" />,
  receipt: <Sparkles className="h-4 w-4" />,
  layers: <Leaf className="h-4 w-4" />,
  wallet: <Sparkles className="h-4 w-4" />,
  users: <Sparkles className="h-4 w-4" />,
  leaf: <Leaf className="h-4 w-4" />,
  "bar-chart-3": <Sparkles className="h-4 w-4" />,
  settings: <Sparkles className="h-4 w-4" />
};

export function Sidebar() {
  return (
    <aside className="hidden w-72 shrink-0 flex-col gap-6 rounded-[22px] border border-white/40 bg-white/80 p-6 shadow-soft backdrop-blur-xl lg:flex">
      <div className="flex items-center gap-3">
        <div className="flex h-11 w-11 items-center justify-center rounded-2xl bg-gradient-to-br from-emerald-500 to-teal-500 text-white shadow-soft">
          <Leaf className="h-5 w-5" />
        </div>
        <div>
          <p className="text-sm font-medium text-emerald-700">BioERP</p>
          <p className="text-xs text-slate-500">Consultoria ambiental premium</p>
        </div>
      </div>

      <div className="grid gap-1">
        {navigationLinks.map((item) => (
          <Link
            key={item.label}
            href="#"
            className={cn(
              "flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm font-medium transition-all hover:bg-emerald-50",
              item.active ? "bg-emerald-50 text-emerald-700 shadow-[0_10px_50px_-35px_rgba(16,185,129,0.8)]" : "text-slate-600"
            )}
          >
            <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-slate-100 text-emerald-600">
              {iconMap[item.icon]}
            </span>
            <span>{item.label}</span>
          </Link>
        ))}
      </div>

      <div className="rounded-2xl border border-emerald-100 bg-gradient-to-br from-emerald-50 via-white to-white p-4 shadow-soft">
        <p className="text-xs font-medium uppercase tracking-widest text-emerald-600">Governança</p>
        <p className="mt-1 text-sm text-slate-600">Camadas SOLID e consistência ACID documentadas.</p>
        <div className="mt-3 flex items-center gap-2 text-xs font-semibold text-emerald-700">
          <span className="badge-dot" /> SLA 99,95%
        </div>
      </div>
    </aside>
  );
}

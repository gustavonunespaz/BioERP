"use client";

import { useMemo } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { Bell, Leaf, Plus, Search } from "lucide-react";

import { Button } from "@/components/ui/button";
import { navigationItems } from "@/lib/navigation";

function getPageTitle(pathname: string) {
  if (pathname === "/") return "Dashboard";
  if (pathname.includes("/unidades")) return "Unidades";
  const match = navigationItems.find((item) => pathname.startsWith(item.href));
  return match?.label ?? "";
}

export function TopBar() {
  const pathname = usePathname();
  const title = useMemo(() => getPageTitle(pathname), [pathname]);

  return (
    <header className="glass-panel sticky top-0 z-30 flex flex-col gap-4 rounded-[22px] px-4 py-4 shadow-soft lg:px-6">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div className="flex items-center gap-3">
          <span className="flex h-10 w-10 items-center justify-center rounded-xl bg-gradient-to-br from-emerald-500 to-teal-500 text-white shadow-soft">
            <Leaf className="h-5 w-5" />
          </span>
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.2em] text-emerald-700">BioERP</p>
            <p className="text-lg font-semibold text-slate-900">{title}</p>
          </div>
        </div>
        <div className="flex items-center gap-2 text-xs text-slate-500">
          <Link href="/clientes" className="rounded-full bg-emerald-50 px-3 py-1 font-semibold text-emerald-700">
            Clientes ativos
          </Link>
          <Link href="/licencas" className="rounded-full bg-slate-100 px-3 py-1 font-semibold text-slate-700">
            Licenciamento
          </Link>
          <Link href="/alertas" className="rounded-full bg-slate-100 px-3 py-1 font-semibold text-slate-700">
            Alertas
          </Link>
        </div>
      </div>

      <div className="flex flex-wrap items-center gap-3">
        <div className="relative min-w-[280px] flex-1">
          <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
          <input
            type="search"
            placeholder="Busca global (clientes, licenças, docs)"
            className="w-full rounded-xl border border-slate-200 bg-slate-50 px-10 py-2.5 text-sm text-slate-700 shadow-inner focus:border-emerald-300 focus:outline-none focus:ring-2 focus:ring-emerald-100"
          />
        </div>
        <Button variant="outline" className="gap-2">
          <Plus className="h-4 w-4" />
          Nova ação
        </Button>
        <span className="flex h-11 w-11 items-center justify-center rounded-xl border border-slate-200 bg-white text-slate-500">
          <Bell className="h-5 w-5" />
        </span>
      </div>
    </header>
  );
}

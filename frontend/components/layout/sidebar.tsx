"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { Bell, Leaf, Layers, LayoutDashboard, Users } from "lucide-react";

import { navigationItems } from "@/lib/navigation";
import { cn } from "@/lib/utils";

const iconMap = {
  "layout-dashboard": LayoutDashboard,
  users: Users,
  layers: Layers,
  bell: Bell
};

export function Sidebar() {
  const pathname = usePathname();

  return (
    <aside className="sticky top-6 hidden h-[calc(100vh-3rem)] w-72 shrink-0 flex-col gap-6 rounded-[22px] border border-white/40 bg-white/80 p-6 shadow-soft backdrop-blur-xl lg:flex">
      <div className="flex items-center gap-3">
        <div className="flex h-11 w-11 items-center justify-center rounded-2xl bg-gradient-to-br from-emerald-500 to-teal-500 text-white shadow-soft">
          <Leaf className="h-5 w-5" />
        </div>
        <div>
          <p className="text-sm font-medium text-emerald-700">BioERP</p>
          <p className="text-xs text-slate-500">Painel inteligente</p>
        </div>
      </div>

      <div className="grid gap-1">
        {navigationItems.map((item) => {
          const Icon = iconMap[item.icon as keyof typeof iconMap];
          const active = pathname.startsWith(item.href);

          return (
            <Link
              key={item.href}
              href={item.href as any}
              className={cn(
                "flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm font-medium transition-all hover:bg-emerald-50",
                active ? "bg-emerald-50 text-emerald-700 shadow-[0_10px_50px_-35px_rgba(16,185,129,0.8)]" : "text-slate-600"
              )}
            >
              <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-slate-100 text-emerald-600">
                {Icon ? <Icon className="h-4 w-4" /> : null}
              </span>
              <span>{item.label}</span>
            </Link>
          );
        })}
      </div>
    </aside>
  );
}

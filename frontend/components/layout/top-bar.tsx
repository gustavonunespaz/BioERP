import { Bell, Plus, Search, Sparkles, UserRound } from "lucide-react";
import { Button } from "@/components/ui/button";

export function TopBar() {
  return (
    <header className="flex flex-col gap-4 rounded-[22px] border border-white/40 bg-white/80 p-6 shadow-soft backdrop-blur-xl">
      <div className="flex flex-wrap items-center justify-between gap-4">
        <div className="space-y-2">
          <div className="flex items-center gap-2 text-sm text-slate-500">
            <span className="font-semibold text-emerald-700">BioERP</span>
            <span>/</span>
            <span className="font-semibold text-slate-800">Visão geral</span>
          </div>
          <div className="flex items-center gap-2 text-xs font-semibold text-emerald-700">
            <span className="badge-dot" /> Cliente ativo: EcoTrans • Empreendimento: Terminal Logístico Verde
          </div>
        </div>
        <div className="flex flex-wrap items-center gap-2">
          <div className="relative w-64">
            <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
            <input
              type="search"
              placeholder="Busca global (clientes, licenças, docs)"
              className="w-full rounded-xl border border-slate-200 bg-slate-50 px-10 py-2 text-sm text-slate-700 shadow-inner focus:border-emerald-300 focus:outline-none focus:ring-2 focus:ring-emerald-100"
            />
          </div>
          <Button variant="outline" className="gap-2">
            <Sparkles className="h-4 w-4" /> Nova licença
          </Button>
          <Button className="gap-2">
            <Plus className="h-4 w-4" /> Ações rápidas
          </Button>
          <span className="flex h-11 w-11 items-center justify-center rounded-xl border border-slate-200 bg-white text-slate-500">
            <Bell className="h-5 w-5" />
          </span>
          <span className="flex h-11 w-11 items-center justify-center rounded-xl bg-gradient-to-br from-emerald-500 to-teal-500 text-white shadow-soft">
            <UserRound className="h-5 w-5" />
          </span>
        </div>
      </div>
    </header>
  );
}

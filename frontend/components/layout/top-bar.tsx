import { Bell, Share2, Sparkles } from "lucide-react";
import { Button } from "@/components/ui/button";

export function TopBar() {
  return (
    <header className="flex flex-col gap-4 rounded-[22px] border border-white/40 bg-white/80 p-6 shadow-soft backdrop-blur-xl">
      <div className="flex flex-wrap items-center justify-between gap-4">
        <div>
          <p className="text-sm font-semibold text-emerald-600">BioERP • Consultoria ambiental</p>
          <h1 className="text-3xl font-semibold tracking-tight text-slate-900 md:text-4xl">Financeiro, projetos e compliance em um só painel</h1>
        </div>
        <div className="flex items-center gap-2">
          <Button variant="outline" className="gap-2">
            <Share2 className="h-4 w-4" /> Compartilhar
          </Button>
          <Button className="gap-2">
            <Sparkles className="h-4 w-4" /> Nova cobrança
          </Button>
          <span className="flex h-11 w-11 items-center justify-center rounded-xl border border-slate-200 bg-white text-slate-500">
            <Bell className="h-5 w-5" />
          </span>
        </div>
      </div>
    </header>
  );
}

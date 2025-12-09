import { ArrowRight, Sparkles } from "lucide-react";
import { Button } from "@/components/ui/button";

export function Hero() {
  return (
    <section className="relative overflow-hidden rounded-[26px] border border-emerald-100 bg-white/90 p-8 shadow-soft">
      <div className="absolute inset-0 gridlines" aria-hidden />
      <div className="absolute inset-0 bg-hero-glow opacity-70" aria-hidden />
      <div className="relative grid gap-8 lg:grid-cols-[1.2fr_0.8fr]">
        <div className="space-y-6">
          <div className="inline-flex items-center gap-3 rounded-full border border-emerald-100 bg-white px-4 py-2 text-sm font-semibold text-emerald-700 shadow-soft">
            <span className="badge-dot" /> Clean Architecture pronta para escalar
          </div>
          <h2 className="text-4xl font-semibold leading-tight tracking-tight text-slate-900">
            Experiência premium com Tailwind + shadcn/ui para clientes de alto padrão
          </h2>
          <p className="max-w-2xl text-lg text-slate-600">
            Estruturamos ACID e SOLID em camadas claras, garantindo governança, rastreabilidade e fluidez visual inspirada em plataformas de ponta.
          </p>
          <div className="flex flex-wrap gap-3">
            <Button size="lg" className="gap-2">
              <Sparkles className="h-5 w-5" /> Explorar cockpit financeiro
            </Button>
            <Button size="lg" variant="outline" className="gap-2">
              Ver projetos <ArrowRight className="h-4 w-4" />
            </Button>
          </div>
        </div>
        <div className="relative grid gap-4 rounded-3xl bg-white/70 p-5 shadow-soft">
          <div className="glass-panel gradient-border p-4">
            <p className="text-xs font-semibold uppercase tracking-[0.2em] text-emerald-700">Disponibilidade</p>
            <div className="mt-2 flex items-end gap-3">
              <span className="text-5xl font-semibold text-slate-900">99,95%</span>
              <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">Observabilidade ativa</span>
            </div>
            <p className="mt-2 text-sm text-slate-500">Testes automatizados, rastreabilidade e auditoria em tempo real.</p>
          </div>
          <div className="grid grid-cols-3 gap-3 text-center text-sm font-semibold text-slate-700">
            <div className="rounded-2xl bg-gradient-to-br from-emerald-50 to-white p-4 shadow-soft">
              <p className="text-xs text-slate-500">ACID</p>
              <p className="text-lg font-semibold text-slate-900">Consistência</p>
            </div>
            <div className="rounded-2xl bg-gradient-to-br from-emerald-50 to-white p-4 shadow-soft">
              <p className="text-xs text-slate-500">SOLID</p>
              <p className="text-lg font-semibold text-slate-900">Camadas limpas</p>
            </div>
            <div className="rounded-2xl bg-gradient-to-br from-emerald-50 to-white p-4 shadow-soft">
              <p className="text-xs text-slate-500">UX</p>
              <p className="text-lg font-semibold text-slate-900">Conta Azul vibes</p>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

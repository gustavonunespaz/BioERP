"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { Leaf } from "lucide-react";

import { Card } from "@/components/ui/card";

const links = [
  { href: "/login", label: "Login" },
  { href: "/cadastro", label: "Criar conta" }
];

export function AuthShell({ children, title, description }: { children: React.ReactNode; title: string; description: string }) {
  const pathname = usePathname();

  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_10%_10%,rgba(16,185,129,0.08),transparent_26%),radial-gradient(circle_at_90%_20%,rgba(59,130,246,0.08),transparent_24%),linear-gradient(180deg,#f7fafc,#f8fbff)]">
      <div className="mx-auto flex min-h-screen max-w-5xl flex-col justify-center px-4 py-10 lg:px-8">
        <div className="mb-8 flex items-center gap-3 text-slate-800">
          <span className="flex h-12 w-12 items-center justify-center rounded-2xl bg-gradient-to-br from-emerald-500 to-teal-500 text-white shadow-soft">
            <Leaf className="h-5 w-5" />
          </span>
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.2em] text-emerald-700">BioERP</p>
            <p className="text-xl font-semibold">Portal seguro</p>
          </div>
        </div>

        <Card className="grid gap-10 p-6 shadow-soft lg:grid-cols-[1fr,1.1fr] lg:p-10">
          <div className="space-y-6">
            <div className="space-y-2">
              <h1 className="text-2xl font-bold text-slate-900 lg:text-3xl">{title}</h1>
              <p className="text-sm text-slate-500 lg:text-base">{description}</p>
            </div>
            <div className="flex items-center gap-3 text-sm font-semibold text-slate-600">
              {links.map((link) => (
                <Link
                  key={link.href}
                  href={link.href}
                  className={`rounded-full px-4 py-2 ${pathname === link.href ? "bg-emerald-100 text-emerald-700" : "bg-slate-100 text-slate-600"}`}
                >
                  {link.label}
                </Link>
              ))}
            </div>
            <div className="rounded-2xl bg-slate-50 p-5 text-sm leading-relaxed text-slate-600">
              <p className="font-semibold text-emerald-700">Boas práticas</p>
              <ul className="mt-3 space-y-2">
                <li>• Use uma senha forte e única.</li>
                <li>• Guarde o endereço seguro do backend na variável NEXT_PUBLIC_API_BASE_URL.</li>
                <li>• Sempre encerre a sessão em dispositivos compartilhados.</li>
              </ul>
            </div>
          </div>
          <div className="w-full max-w-lg justify-self-end">{children}</div>
        </Card>
      </div>
    </div>
  );
}

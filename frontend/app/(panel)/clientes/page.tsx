"use client";

import { useEffect, useMemo, useState } from "react";
import Link from "next/link";
import { AlertCircle, FileText, Loader2, Plus, Users } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { createClientRequest, fetchClients } from "@/lib/services/api-client";
import { Client } from "@/lib/types";

export default function ClientesPage() {
  const [clients, setClients] = useState<Client[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [form, setForm] = useState({ name: "", cnpj: "" });
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const response = await fetchClients();
        setClients(response);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  const totalLicenses = useMemo(
    () => clients.reduce((acc, client) => acc + (client.units?.reduce((sum, unit) => sum + (unit.licenseCount ?? 0), 0) ?? 0), 0),
    [clients]
  );

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setError(null);
    setSubmitting(true);

    try {
      const created = await createClientRequest(form);
      setClients((prev) => [created, ...prev]);
      setForm({ name: "", cnpj: "" });
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-3 lg:flex-row lg:items-center lg:justify-between">
        <div>
          <h1 className="text-2xl font-semibold text-slate-900">Clientes</h1>
          <p className="text-sm text-slate-500">Lista de clientes com unidades e status operacional.</p>
        </div>
        <div className="flex items-center gap-2 text-sm text-slate-600">
          <span className="flex items-center gap-2 rounded-full bg-emerald-50 px-4 py-2 font-semibold text-emerald-700">
            <Users className="h-4 w-4" /> {clients.length} clientes
          </span>
          <span className="rounded-full bg-slate-100 px-4 py-2 font-semibold text-slate-700">{totalLicenses} licenças</span>
        </div>
      </div>

      <div className="grid gap-4 lg:grid-cols-[1.1fr_0.9fr]">
        <Card className="p-4 lg:p-6">
          {loading && (
            <div className="flex items-center gap-2 text-sm text-slate-600">
              <Loader2 className="h-4 w-4 animate-spin" /> carregando clientes...
            </div>
          )}
          {error && (
            <div className="mb-3 flex items-center gap-2 rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-700">
              <AlertCircle className="h-4 w-4" /> {error}
            </div>
          )}
          {!loading && !error && clients.length === 0 ? (
            <p className="text-sm text-slate-500">Nenhum cliente cadastrado.</p>
          ) : null}

          <div className="grid gap-3 md:grid-cols-2">
            {clients.map((client) => (
              <Link key={client.id} href={`/clientes/${client.id}`} className="rounded-xl border border-slate-100 bg-white p-4 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
                <div className="flex items-start justify-between">
                  <div className="space-y-1">
                    <p className="text-sm font-semibold text-slate-900">{client.name}</p>
                    <p className="text-xs text-slate-500">CNPJ: {client.cnpj ?? "não informado"}</p>
                  </div>
                  <span className="rounded-full px-3 py-1 text-xs font-semibold bg-slate-100 text-slate-700">Ativo</span>
                </div>
                <div className="mt-3 flex items-center justify-between text-xs text-slate-500">
                  <span>{client.units?.length ?? 0} unidades</span>
                  <span>{client.createdAt ? `Criado em ${new Date(client.createdAt).toLocaleDateString()}` : "Data não informada"}</span>
                </div>
              </Link>
            ))}
          </div>
        </Card>

        <Card className="p-4 lg:p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-semibold text-slate-900">Novo cliente</p>
              <p className="text-xs text-slate-500">Cadastro rápido com segmentação e UF.</p>
            </div>
            <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">ACID seguro</span>
          </div>
          <form className="mt-4 grid gap-3" onSubmit={handleSubmit}>
            <div className="grid gap-1">
              <label className="text-xs font-semibold text-slate-700" htmlFor="name">
                Razão social
              </label>
              <input
                id="name"
                value={form.name}
                onChange={(e) => setForm((prev) => ({ ...prev, name: e.target.value }))}
                required
                placeholder="Ex: EcoTrans Logística"
                className="rounded-xl border border-slate-200 bg-slate-50 px-3 py-2 text-sm focus:border-emerald-300 focus:outline-none focus:ring-2 focus:ring-emerald-100"
              />
            </div>
            <div className="grid gap-1">
              <label className="text-xs font-semibold text-slate-700" htmlFor="cnpj">
                CNPJ
              </label>
              <div className="flex items-center gap-2 rounded-xl border border-slate-200 bg-slate-50 px-3 py-2 text-sm focus-within:border-emerald-300 focus-within:ring-2 focus-within:ring-emerald-100">
                <FileText className="h-4 w-4 text-slate-400" />
                <input
                  id="cnpj"
                  value={form.cnpj}
                  onChange={(e) => setForm((prev) => ({ ...prev, cnpj: e.target.value }))}
                  required
                  placeholder="00.000.000/0000-00"
                  className="w-full bg-transparent outline-none"
                />
              </div>
            </div>
            <Button type="submit" className="gap-2" disabled={submitting}>
              {submitting ? <Loader2 className="h-4 w-4 animate-spin" /> : <Plus className="h-4 w-4" />}
              Criar cliente
            </Button>
          </form>
        </Card>
      </div>
    </div>
  );
}

"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Loader2, UserPlus } from "lucide-react";

import { AuthShell } from "@/components/auth/auth-shell";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { persistSession } from "@/lib/auth/session";
import { registerUser } from "@/lib/services/auth-client";

export default function CadastroPage() {
  const router = useRouter();
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(event: React.FormEvent) {
    event.preventDefault();
    setError(null);

    if (!name || !email || !password) {
      setError("Preencha nome, e-mail e senha.");
      return;
    }

    if (password !== confirmPassword) {
      setError("As senhas precisam coincidir.");
      return;
    }

    try {
      setLoading(true);
      const response = await registerUser({ name, email, password });
      persistSession(response.token, response.user);
      router.push("/dashboard");
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <AuthShell title="Crie seu acesso" description="Cadastre um usuário para liberar o painel e registrar atividades.">
      <form onSubmit={handleSubmit} className="space-y-5">
        <div className="space-y-1">
          <Label htmlFor="name">Nome completo</Label>
          <Input id="name" value={name} onChange={(event) => setName(event.target.value)} placeholder="Maria Oliveira" />
        </div>
        <div className="space-y-1">
          <Label htmlFor="email">E-mail corporativo</Label>
          <Input
            id="email"
            type="email"
            autoComplete="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            placeholder="nome@empresa.com"
          />
        </div>
        <div className="space-y-1">
          <Label htmlFor="password">Senha</Label>
          <Input
            id="password"
            type="password"
            autoComplete="new-password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            placeholder="••••••••"
          />
        </div>
        <div className="space-y-1">
          <Label htmlFor="confirm">Confirme a senha</Label>
          <Input
            id="confirm"
            type="password"
            autoComplete="new-password"
            value={confirmPassword}
            onChange={(event) => setConfirmPassword(event.target.value)}
            placeholder="••••••••"
          />
        </div>
        {error && <p className="rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-700">{error}</p>}
        <Button type="submit" className="w-full gap-2" disabled={loading}>
          {loading ? <Loader2 className="h-4 w-4 animate-spin" /> : <UserPlus className="h-4 w-4" />}
          Registrar acesso seguro
        </Button>
      </form>
    </AuthShell>
  );
}

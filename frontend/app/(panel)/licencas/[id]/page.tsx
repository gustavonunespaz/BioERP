"use client";

import { useEffect, useState } from "react";
import { AlertCircle, History, Loader2, RefreshCw, Shield } from "lucide-react";
import { useParams } from "next/navigation";

import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Modal } from "@/components/ui/modal";
import { fetchLicenseById, uploadLicenseDocument } from "@/lib/services/api-client";
import { License } from "@/lib/types";

const tabs = [
  { id: "resumo", label: "Resumo" },
  { id: "documentos", label: "Documentos" },
  { id: "condicionantes", label: "Condicionantes" },
  { id: "historico", label: "Histórico" }
] as const;

type TabId = (typeof tabs)[number]["id"];

const statusTag: Record<License["status"], string> = {
  vigente: "bg-emerald-50 text-emerald-700",
  critica: "bg-amber-50 text-amber-700",
  arquivada: "bg-slate-100 text-slate-600"
};

export default function LicenseDetailPage() {
  const params = useParams<{ id: string }>();
  const [license, setLicense] = useState<License | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [tab, setTab] = useState<TabId>("resumo");
  const [uploading, setUploading] = useState(false);
  const [showUpload, setShowUpload] = useState(false);
  const [file, setFile] = useState<File | null>(null);
  const [uploader, setUploader] = useState("Equipe BioERP");

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchLicenseById(params.id);
        setLicense(data);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [params.id]);

  const handleUpload = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!file || !license) return;
    setUploading(true);
    setError(null);

    try {
      const newDoc = await uploadLicenseDocument(license.id, {
        name: file.name,
        size: `${(file.size / 1024 / 1024).toFixed(2)} MB`,
        uploadedBy: uploader
      });

      setLicense({ ...license, documents: [newDoc, ...license.documents] });
      setShowUpload(false);
      setFile(null);
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setUploading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center gap-2 text-sm text-slate-600">
        <Loader2 className="h-4 w-4 animate-spin" /> carregando licença...
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center gap-2 rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-700">
        <AlertCircle className="h-4 w-4" /> {error}
      </div>
    );
  }

  if (!license) {
    return <p className="text-sm text-slate-500">Licença não encontrada.</p>;
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-2">
        <h1 className="text-2xl font-semibold text-slate-900">{license.title}</h1>
        <div className="flex flex-wrap items-center gap-2 text-xs text-slate-600">
          <span className={`rounded-full px-3 py-1 font-semibold ${statusTag[license.status]}`}>{license.status.toUpperCase()}</span>
          <span className="rounded-full bg-slate-100 px-3 py-1 font-semibold text-slate-700">{license.code}</span>
          <span className="rounded-full bg-slate-100 px-3 py-1 font-semibold text-slate-700">{license.category}</span>
        </div>
      </div>

      <div className="flex gap-2">
        {tabs.map((item) => (
          <button
            key={item.id}
            onClick={() => setTab(item.id)}
            className={`rounded-full px-4 py-2 text-xs font-semibold ${
              tab === item.id ? "bg-emerald-100 text-emerald-800" : "bg-slate-100 text-slate-600"
            }`}
          >
            {item.label}
          </button>
        ))}
      </div>

      {tab === "resumo" && (
        <Card className="p-4 lg:p-6">
          <div className="grid gap-4 md:grid-cols-2">
            <div>
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">Autoridade</p>
              <p className="text-sm text-slate-900">{license.authority}</p>
            </div>
            <div>
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">Validade</p>
              <p className="text-sm text-slate-900">{license.expiresAt}</p>
            </div>
            <div>
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">Emissão</p>
              <p className="text-sm text-slate-900">{license.issuedAt}</p>
            </div>
            <div>
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">Documentos</p>
              <p className="text-sm text-slate-900">{license.documents.length} versões ativas</p>
            </div>
          </div>
        </Card>
      )}

      {tab === "documentos" && (
        <Card className="space-y-4 p-4 lg:p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-semibold text-slate-900">Documentos versionados</p>
              <p className="text-xs text-slate-500">Uploads criam nova versão preservando histórico.</p>
            </div>
            <Button className="gap-2" onClick={() => setShowUpload(true)}>
              <RefreshCw className="h-4 w-4" /> Reupload / nova versão
            </Button>
          </div>
          <div className="space-y-3">
            {license.documents.map((doc) => (
              <div key={doc.id} className="flex items-center justify-between rounded-xl border border-slate-100 bg-white p-4">
                <div>
                  <p className="text-sm font-semibold text-slate-900">{doc.name}</p>
                  <p className="text-xs text-slate-500">{doc.size} • Enviado por {doc.uploadedBy}</p>
                </div>
                <span className="rounded-full bg-slate-100 px-3 py-1 text-xs font-semibold text-slate-700">v{doc.version}</span>
              </div>
            ))}
          </div>
        </Card>
      )}

      {tab === "condicionantes" && (
        <Card className="space-y-3 p-4 lg:p-6">
          {license.conditions.map((condition) => (
            <div key={condition.id} className="rounded-xl border border-slate-100 bg-white p-4">
              <div className="flex items-center justify-between">
                <p className="text-sm font-semibold text-slate-900">{condition.title}</p>
                <span
                  className={`rounded-full px-3 py-1 text-xs font-semibold ${
                    condition.status === "concluida"
                      ? "bg-emerald-50 text-emerald-700"
                      : condition.status === "alerta"
                        ? "bg-amber-50 text-amber-700"
                        : "bg-slate-100 text-slate-700"
                  }`}
                >
                  {condition.status}
                </span>
              </div>
              <p className="mt-2 text-xs text-slate-500">Prazo {condition.dueDate}</p>
            </div>
          ))}
        </Card>
      )}

      {tab === "historico" && (
        <Card className="space-y-3 p-4 lg:p-6">
          {license.history.map((entry) => (
            <div key={entry.id} className="flex items-center gap-3 rounded-xl border border-slate-100 bg-white p-4">
              <History className="h-4 w-4 text-emerald-600" />
              <div>
                <p className="text-sm font-semibold text-slate-900">{entry.description}</p>
                <p className="text-xs text-slate-500">{entry.date} • {entry.author}</p>
              </div>
            </div>
          ))}
        </Card>
      )}

      <Modal open={showUpload} onClose={() => setShowUpload(false)} title="Upload versionado">
        <form className="space-y-3" onSubmit={handleUpload}>
          <div className="grid gap-1">
            <label className="text-xs font-semibold text-slate-700" htmlFor="file">
              Selecione o arquivo
            </label>
            <input
              id="file"
              type="file"
              required
              onChange={(event) => setFile(event.target.files?.[0] ?? null)}
              className="rounded-xl border border-slate-200 px-3 py-2 text-sm"
            />
          </div>
          <div className="grid gap-1">
            <label className="text-xs font-semibold text-slate-700" htmlFor="uploader">
              Responsável pelo envio
            </label>
            <input
              id="uploader"
              value={uploader}
              onChange={(e) => setUploader(e.target.value)}
              className="rounded-xl border border-slate-200 px-3 py-2 text-sm"
            />
          </div>
          <div className="flex justify-end gap-2">
            <Button variant="outline" type="button" onClick={() => setShowUpload(false)}>
              Cancelar
            </Button>
            <Button type="submit" className="gap-2" disabled={uploading}>
              {uploading ? <Loader2 className="h-4 w-4 animate-spin" /> : <Shield className="h-4 w-4" />} Confirmar versão
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}

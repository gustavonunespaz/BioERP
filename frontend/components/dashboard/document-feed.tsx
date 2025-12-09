import type { ReactNode } from "react";

import { FileSpreadsheet, FileText, FileType2, History } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import type { DocumentEntry } from "@/lib/data/dashboard";

interface DocumentFeedProps {
  documents: DocumentEntry[];
}

const iconMap: Record<DocumentEntry["category"], ReactNode> = {
  Resíduos: <FileType2 className="h-4 w-4" />,
  Projetos: <FileText className="h-4 w-4" />,
  Licença: <FileText className="h-4 w-4" />,
  Financeiro: <FileSpreadsheet className="h-4 w-4" />,
  Compliance: <History className="h-4 w-4" />,
  Ambiental: <FileText className="h-4 w-4" />,
  Interno: <FileSpreadsheet className="h-4 w-4" />
};

export function DocumentFeed({ documents }: DocumentFeedProps) {
  return (
    <Card className="border border-white/50 bg-white/80 shadow-soft">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-4">
        <div>
          <p className="text-xs uppercase tracking-[0.3em] text-emerald-600">Últimos documentos</p>
          <CardTitle className="text-xl text-slate-900">Uploads recentes</CardTitle>
        </div>
        <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">Upload inteligente</span>
      </CardHeader>
      <CardContent className="space-y-2">
        {documents.map((doc) => (
          <div key={doc.name} className="flex items-center justify-between rounded-xl border border-slate-100 bg-slate-50/80 px-4 py-3 text-sm shadow-sm">
            <div className="flex items-center gap-3">
              <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-white text-emerald-700">
                {iconMap[doc.category]}
              </span>
              <div className="space-y-0.5">
                <p className="font-semibold text-slate-900">{doc.name}</p>
                <p className="text-xs text-slate-500">{doc.client} • {doc.category}</p>
              </div>
            </div>
            <span className="text-xs font-semibold text-slate-500">{doc.timeAgo}</span>
          </div>
        ))}
      </CardContent>
    </Card>
  );
}

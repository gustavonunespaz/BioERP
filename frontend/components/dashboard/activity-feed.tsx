import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import type { Activity } from "@/lib/data/dashboard";
import { cn } from "@/lib/utils";
import { ShieldCheck } from "lucide-react";

interface ActivityFeedProps {
  activities: Activity[];
}

export function ActivityFeed({ activities }: ActivityFeedProps) {
  const toneMap: Record<NonNullable<Activity["tone"]>, string> = {
    positive: "border-emerald-100 bg-emerald-50/70 text-emerald-800",
    neutral: "border-slate-100 bg-slate-50 text-slate-700",
    alert: "border-amber-100 bg-amber-50 text-amber-800"
  };

  return (
    <Card className="h-full">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-4">
        <div>
          <p className="text-xs uppercase tracking-[0.25em] text-emerald-600">Fluxo de trabalho</p>
          <CardTitle className="text-lg text-slate-800">Trilhas ACID & SOLID</CardTitle>
        </div>
        <Badge variant="secondary" className="gap-1">
          <ShieldCheck className="h-4 w-4" /> Auditável
        </Badge>
      </CardHeader>
      <CardContent className="space-y-3">
        {activities.map((activity) => (
          <div key={activity.title} className={cn("flex items-center justify-between rounded-xl border px-4 py-3", toneMap[activity.tone ?? "neutral"])}>
            <div>
              <p className="text-sm font-semibold text-slate-900">{activity.title}</p>
              <p className="text-xs text-slate-600">{activity.subtitle}</p>
            </div>
            <Badge variant="outline" className="border-transparent bg-white/70 text-xs text-emerald-700">
              {activity.badge}
            </Badge>
          </div>
        ))}
      </CardContent>
    </Card>
  );
}

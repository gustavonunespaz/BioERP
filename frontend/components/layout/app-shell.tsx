import { Sidebar } from "./sidebar";
import { TopBar } from "./top-bar";

export function AppShell({ children }: { children: React.ReactNode }) {
  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_10%_10%,rgba(16,185,129,0.08),transparent_26%),radial-gradient(circle_at_90%_20%,rgba(59,130,246,0.08),transparent_24%),linear-gradient(180deg,#f7fafc,#f8fbff)]">
      <div className="mx-auto flex max-w-[1400px] gap-6 px-4 py-6 lg:px-8">
        <Sidebar />
        <div className="flex min-h-screen flex-1 flex-col gap-6">
          <TopBar />
          <main className="flex-1 pb-12">{children}</main>
        </div>
      </div>
    </div>
  );
}

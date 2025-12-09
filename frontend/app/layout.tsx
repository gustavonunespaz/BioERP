import type { Metadata } from "next";
import { Inter, Manrope } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"], variable: "--font-inter" });
const manrope = Manrope({ subsets: ["latin"], variable: "--font-manrope" });

export const metadata: Metadata = {
  title: "BioERP | Painel Inteligente",
  description: "Plataforma financeira e de projetos com experiência premium em Next.js"
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="pt-BR" className={`${inter.variable} ${manrope.variable}`}>
      <body className="min-h-screen bg-[radial-gradient(circle_at_10%_10%,rgba(16,185,129,0.08),transparent_26%),radial-gradient(circle_at_90%_20%,rgba(59,130,246,0.08),transparent_24%),linear-gradient(180deg,#f7fafc,#f8fbff)] text-slate-900">
        {children}
      </body>
    </html>
  );
}

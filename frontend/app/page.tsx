import { cookies } from "next/headers";
import { redirect } from "next/navigation";

import { getServerToken } from "@/lib/auth/session";

export default function Home() {
  const token = getServerToken(cookies());
  if (!token) {
    redirect("/login");
  }
  redirect("/dashboard");
}

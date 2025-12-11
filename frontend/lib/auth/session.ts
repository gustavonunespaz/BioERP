const TOKEN_KEY = "bioerp_token";
const USER_KEY = "bioerp_user";
const COOKIE_MAX_AGE = 60 * 60 * 4;

type StoredUser = {
  name: string;
  email: string;
};

export function persistSession(token: string, user: StoredUser) {
  if (typeof document !== "undefined") {
    document.cookie = `${TOKEN_KEY}=${token}; path=/; max-age=${COOKIE_MAX_AGE}; samesite=lax`;
  }
  if (typeof localStorage !== "undefined") {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }
}

export function clearSession() {
  if (typeof document !== "undefined") {
    document.cookie = `${TOKEN_KEY}=; path=/; max-age=0; samesite=lax`;
  }
  if (typeof localStorage !== "undefined") {
    localStorage.removeItem(USER_KEY);
  }
}

export function loadUser(): StoredUser | null {
  if (typeof localStorage === "undefined") return null;
  const stored = localStorage.getItem(USER_KEY);
  if (!stored) return null;
  try {
    return JSON.parse(stored) as StoredUser;
  } catch (error) {
    return null;
  }
}

export function getServerToken(cookies: { get(name: string): { value: string } | undefined }) {
  return cookies.get(TOKEN_KEY)?.value;
}

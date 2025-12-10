export type NavigationItem = {
  label: string;
  href: string;
  icon: string;
};

export const navigationItems: NavigationItem[] = [
  { label: "Dashboard", href: "/dashboard", icon: "layout-dashboard" },
  { label: "Clientes", href: "/clientes", icon: "users" },
  { label: "Licenças", href: "/licencas", icon: "layers" },
  { label: "Alertas", href: "/alertas", icon: "bell" }
];

import './styles.css';

const features = [
  'Gestão de requisitos legais com atualizações automáticas',
  'Monitoramento completo de licenças, condicionantes e vencimentos',
  'Gestão de projetos ambientais com histórico de entregas',
  'Controle e rastreabilidade de resíduos com geração de MTR e CDF',
  'Centralização de documentos críticos com segurança',
  'Checklists móveis para inspeções, auditorias e vistorias',
  'Dashboards e relatórios configuráveis para decisões estratégicas',
  'Alertas automáticos para prazos sensíveis'
];

function App() {
  return (
    <div className="page">
      <header className="hero">
        <p className="pill">BioERP • Consultoria Ambiental</p>
        <h1>Organize, preveja e controle cada requisito ambiental</h1>
        <p className="lead">
          Plataforma web construída com arquitetura limpa, pronta para integrar PostgreSQL, Spring Boot e React.
          Ideal para consultorias que precisam de rastreabilidade, segurança e aderência às normas ambientais.
        </p>
        <div className="cta">
          <a className="primary" href="#features">Explorar funcionalidades</a>
          <a className="secondary" href="#stack">Ver arquitetura</a>
        </div>
      </header>

      <main>
        <section id="features" className="card">
          <h2>Principais funcionalidades</h2>
          <ul>
            {features.map((feature) => (
              <li key={feature}>{feature}</li>
            ))}
          </ul>
        </section>

        <section id="stack" className="grid">
          <div className="card">
            <h3>Backend</h3>
            <p>
              API em Spring Boot com princípios de arquitetura limpa, camadas bem definidas e prontidão para integrações
              com PostgreSQL, garantindo ACID e SOLID em cada serviço.
            </p>
          </div>
          <div className="card">
            <h3>Frontend</h3>
            <p>
              Interface React moderna, otimizada para navegadores desktop e mobile, preparada para consumir a API do BioERP
              e oferecer uma experiência rápida e previsível.
            </p>
          </div>
          <div className="card">
            <h3>Observabilidade</h3>
            <p>
              Endpoints de saúde e métricas configuráveis para facilitar monitoramento, auditoria e previsibilidade operacional.
            </p>
          </div>
        </section>
      </main>
    </div>
  );
}

export default App;

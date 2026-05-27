import { useNavigate } from "react-router-dom";

export default function AdminDashboard() {
    const navigate = useNavigate();
    const nombre = localStorage.getItem("nombre");

    const handleLogout = () => {
        localStorage.clear();
        navigate("/login");
    };

    return (
        <div>
            <nav style={{ padding: "1rem", background: "#333", color: "white", display: "flex", justifyContent: "space-between" }}>
                <span><strong>BolsaEmpleo</strong></span>
                <div style={{ display: "flex", gap: "1rem", alignItems: "center" }}>
                    <button onClick={() => navigate("/admin/empresas-pendientes")}>Empresas pendientes</button>
                    <button onClick={() => navigate("/admin/oferentes-pendientes")}>Oferentes pendientes</button>
                    <button onClick={() => navigate("/admin/caracteristicas")}>Características</button>
                    <span>{nombre}</span>
                    <button onClick={handleLogout}>Salir</button>
                </div>
            </nav>

            <div style={{ padding: "2rem" }}>
                <h1>Administrador</h1>
                <p>Aprobaciones y catálogo de características.</p>
                <div style={{ display: "flex", gap: "1rem", marginTop: "1rem" }}>
                    <button onClick={() => navigate("/admin/empresas-pendientes")}>Empresas pendientes</button>
                    <button onClick={() => navigate("/admin/oferentes-pendientes")}>Oferentes pendientes</button>
                    <button onClick={() => navigate("/admin/caracteristicas")}>Características</button>
                </div>
            </div>
        </div>
    );
}
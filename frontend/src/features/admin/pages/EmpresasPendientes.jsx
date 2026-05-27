import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiFetch } from "../../../shared/api/apiClient";

export default function EmpresasPendientes() {
    const [empresas, setEmpresas] = useState([]);
    const [mensaje, setMensaje] = useState("");
    const navigate = useNavigate();

    const cargar = async () => {
        const data = await apiFetch("/api/admin/empresas/pendientes");
        setEmpresas(data || []);
    };

    useEffect(() => {
        cargar();
    }, []);

    const aprobar = async (id) => {
        await apiFetch(`/api/admin/empresas/${id}/aprobar`, { method: "POST" });
        setMensaje("Empresa aprobada correctamente.");
        cargar();
    };

    return (
        <div style={{ padding: "2rem" }}>
            <button onClick={() => navigate("/admin/dashboard")}>← Volver</button>
            <h1>Empresas pendientes</h1>
            {mensaje && <p style={{ color: "green" }}>{mensaje}</p>}

            {empresas.length === 0 ? (
                <p>No hay empresas pendientes de aprobación.</p>
            ) : (
                <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse" }}>
                    <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Localización</th>
                        <th>Teléfono</th>
                        <th>Descripción</th>
                        <th>Acción</th>
                    </tr>
                    </thead>
                    <tbody>
                    {empresas.map((e) => (
                        <tr key={e.id}>
                            <td>{e.nombre}</td>
                            <td>{e.correo}</td>
                            <td>{e.localizacion}</td>
                            <td>{e.telefono}</td>
                            <td>{e.descripcion}</td>
                            <td>
                                <button onClick={() => aprobar(e.id)}>Aprobar</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
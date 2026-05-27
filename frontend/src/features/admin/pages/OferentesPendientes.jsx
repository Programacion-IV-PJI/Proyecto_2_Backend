import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiFetch } from "../../../shared/api/apiClient";

export default function OferentesPendientes() {
    const [oferentes, setOferentes] = useState([]);
    const [mensaje, setMensaje] = useState("");
    const navigate = useNavigate();

    const cargar = async () => {
        const data = await apiFetch("/api/admin/oferentes/pendientes");
        setOferentes(data || []);
    };

    useEffect(() => {
        cargar();
    }, []);

    const aprobar = async (id) => {
        await apiFetch(`/api/admin/oferentes/${id}/aprobar`, { method: "POST" });
        setMensaje("Oferente aprobado correctamente.");
        cargar();
    };

    return (
        <div style={{ padding: "2rem" }}>
            <button onClick={() => navigate("/admin/dashboard")}>← Volver</button>
            <h1>Oferentes pendientes</h1>
            {mensaje && <p style={{ color: "green" }}>{mensaje}</p>}

            {oferentes.length === 0 ? (
                <p>No hay oferentes pendientes de aprobación.</p>
            ) : (
                <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse" }}>
                    <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Identificación</th>
                        <th>Correo</th>
                        <th>Nacionalidad</th>
                        <th>Teléfono</th>
                        <th>Residencia</th>
                        <th>Acción</th>
                    </tr>
                    </thead>
                    <tbody>
                    {oferentes.map((o) => (
                        <tr key={o.id}>
                            <td>{o.nombre}</td>
                            <td>{o.primerApellido}</td>
                            <td>{o.identificacion}</td>
                            <td>{o.correo}</td>
                            <td>{o.nacionalidad}</td>
                            <td>{o.telefono}</td>
                            <td>{o.residencia}</td>
                            <td>
                                <button onClick={() => aprobar(o.id)}>Aprobar</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
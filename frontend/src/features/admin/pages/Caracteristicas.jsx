import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiFetch } from "../../../shared/api/apiClient";

export default function Caracteristicas() {
    const [todas, setTodas] = useState([]);
    const [subcategorias, setSubcategorias] = useState([]);
    const [ruta, setRuta] = useState([]);
    const [actualId, setActualId] = useState(null);
    const [nuevoNombre, setNuevoNombre] = useState("");
    const [padreSeleccionado, setPadreSeleccionado] = useState("");
    const [mensaje, setMensaje] = useState("");
    const navigate = useNavigate();

    const cargarTodas = async () => {
        const data = await apiFetch("/api/admin/caracteristicas");
        setTodas(data || []);
    };

    const cargarRaices = async () => {
        const data = await apiFetch("/api/admin/caracteristicas/raices");
        setSubcategorias(data || []);
    };

    useEffect(() => {
        cargarTodas();
        cargarRaices();
    }, []);

    const entrar = async (id, nombre) => {
        const data = await apiFetch(`/api/admin/caracteristicas/${id}/hijos`);
        setSubcategorias(data || []);
        setActualId(id);
        setRuta((prev) => [...prev, { id, nombre }]);
    };

    const retroceder = async (index) => {
        const nuevaRuta = ruta.slice(0, index);
        setRuta(nuevaRuta);
        if (nuevaRuta.length === 0) {
            setActualId(null);
            cargarRaices();
        } else {
            const ultimo = nuevaRuta[nuevaRuta.length - 1];
            setActualId(ultimo.id);
            const data = await apiFetch(`/api/admin/caracteristicas/${ultimo.id}/hijos`);
            setSubcategorias(data || []);
        }
    };

    const crear = async (e) => {
        e.preventDefault();
        if (!nuevoNombre.trim()) return;

        const body = {
            nombre: nuevoNombre.trim(),
            padreId: padreSeleccionado ? Number(padreSeleccionado) : (actualId || null),
        };

        await apiFetch("/api/admin/caracteristicas", {
            method: "POST",
            body: JSON.stringify(body),
        });

        setNuevoNombre("");
        setPadreSeleccionado("");
        setMensaje("Característica creada.");
        cargarTodas();
        if (actualId) {
            const data = await apiFetch(`/api/admin/caracteristicas/${actualId}/hijos`);
            setSubcategorias(data || []);
        } else {
            cargarRaices();
        }
    };

    const eliminar = async (id) => {
        try {
            await apiFetch(`/api/admin/caracteristicas/${id}`, { method: "DELETE" });
            setMensaje("Característica eliminada.");
            cargarTodas();
            if (actualId) {
                const data = await apiFetch(`/api/admin/caracteristicas/${actualId}/hijos`);
                setSubcategorias(data || []);
            } else {
                cargarRaices();
            }
        } catch (err) {
            setMensaje("No se puede eliminar — tiene subcategorías.");
        }
    };

    return (
        <div style={{ padding: "2rem" }}>
            <button onClick={() => navigate("/admin/dashboard")}>← Dashboard</button>
            <h1>Características</h1>
            {mensaje && <p style={{ color: "green" }}>{mensaje}</p>}

            <div style={{ display: "flex", gap: "2rem", marginTop: "1rem" }}>

                {/* Panel izquierdo — árbol */}
                <div style={{ flex: 1 }}>
                    {/* Breadcrumb */}
                    <div style={{ marginBottom: "1rem" }}>
            <span style={{ cursor: "pointer", color: "blue" }} onClick={() => retroceder(0)}>
              Raíces
            </span>
                        {ruta.map((item, i) => (
                            <span key={item.id}>
                {" / "}
                                <span style={{ cursor: "pointer", color: "blue" }} onClick={() => retroceder(i + 1)}>
                  {item.nombre}
                </span>
              </span>
                        ))}
                    </div>

                    <p><strong>Subcategorías de: {ruta.length > 0 ? ruta[ruta.length - 1].nombre : "raíces"}</strong></p>

                    {subcategorias.length === 0 ? (
                        <p>Sin subcategorías.</p>
                    ) : (
                        <ul style={{ listStyle: "none", padding: 0 }}>
                            {subcategorias.map((c) => (
                                <li key={c.id} style={{ marginBottom: "0.5rem", display: "flex", gap: "0.5rem", alignItems: "center" }}>
                                    <span>{c.nombre}</span>
                                    <button onClick={() => entrar(c.id, c.nombre)}>Entrar</button>
                                    <button onClick={() => eliminar(c.id)} style={{ color: "red" }}>✕</button>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>

                {/* Panel derecho — agregar */}
                <div style={{ width: "300px", border: "1px solid #ccc", padding: "1rem", borderRadius: "8px" }}>
                    <h3>Agregar Característica</h3>
                    <form onSubmit={crear}>
                        <div style={{ marginBottom: "1rem" }}>
                            <label>Nombre</label><br />
                            <input
                                type="text"
                                value={nuevoNombre}
                                onChange={(e) => setNuevoNombre(e.target.value)}
                                placeholder="Ej: Kotlin"
                                required
                                style={{ width: "100%" }}
                            />
                        </div>
                        <div style={{ marginBottom: "1rem" }}>
                            <label>Padre</label><br />
                            <select
                                value={padreSeleccionado}
                                onChange={(e) => setPadreSeleccionado(e.target.value)}
                                style={{ width: "100%" }}
                            >
                                <option value="">{actualId ? ruta[ruta.length - 1]?.nombre : "(sin padre)"}</option>
                                {todas.map((c) => (
                                    <option key={c.id} value={c.id}>
                                        {c.padreNombre ? `${c.padreNombre} / ${c.nombre}` : c.nombre}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <button type="submit">Crear</button>
                    </form>
                </div>
            </div>
        </div>
    );
}
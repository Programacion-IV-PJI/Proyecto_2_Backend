import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {
    const [identificacion, setIdentificacion] = useState("");
    const [clave, setClave] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const res = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ identificacion, clave }),
            });

            if (!res.ok) {
                setError("Credenciales incorrectas o cuenta no aprobada.");
                return;
            }

            const data = await res.json();
            localStorage.setItem("token", data.token);
            localStorage.setItem("rol", data.rol);
            localStorage.setItem("nombre", data.nombre);

            if (data.rol === "ADMIN") navigate("/admin/dashboard");
            else if (data.rol === "EMPRESA") navigate("/empresa/dashboard");
            else if (data.rol === "OFERENTE") navigate("/oferente/dashboard");

        } catch (err) {
            setError("Error conectando con el servidor.");
        }
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
            <div style={{ border: "1px solid #ccc", padding: "2rem", borderRadius: "8px", width: "300px" }}>
                <h2 style={{ textAlign: "center" }}>Login</h2>
                {error && <p style={{ color: "red" }}>{error}</p>}
                <form onSubmit={handleLogin}>
                    <div style={{ marginBottom: "1rem" }}>
                        <label>Usuario</label><br />
                        <input
                            type="text"
                            value={identificacion}
                            onChange={(e) => setIdentificacion(e.target.value)}
                            style={{ width: "100%" }}
                            required
                        />
                    </div>
                    <div style={{ marginBottom: "1rem" }}>
                        <label>Clave</label><br />
                        <input
                            type="password"
                            value={clave}
                            onChange={(e) => setClave(e.target.value)}
                            style={{ width: "100%" }}
                            required
                        />
                    </div>
                    <button type="submit" style={{ width: "100%" }}>Ingresar</button>
                </form>
            </div>
        </div>
    );
}
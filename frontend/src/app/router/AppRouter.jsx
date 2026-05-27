import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import AdminDashboard from "../../features/admin/pages/AdminDashboard";
import EmpresasPendientes from "../../features/admin/pages/EmpresasPendientes";
import OferentesPendientes from "../../features/admin/pages/OferentesPendientes";
import Caracteristicas from "../../features/admin/pages/Caracteristicas";
import Login from "../../features/admin/pages/Login";

export default function AppRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/admin/dashboard" element={<AdminDashboard />} />
                <Route path="/admin/empresas-pendientes" element={<EmpresasPendientes />} />
                <Route path="/admin/oferentes-pendientes" element={<OferentesPendientes />} />
                <Route path="/admin/caracteristicas" element={<Caracteristicas />} />
                <Route path="/" element={<Navigate to="/admin/dashboard" />} />
                <Route path="/login" element={<Login />} />
                <Route path="/" element={<Navigate to="/login" />} />
            </Routes>
        </BrowserRouter>
    );
}
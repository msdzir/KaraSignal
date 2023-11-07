import React from "react";
import ReactDOM from "react-dom/client";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter } from "react-router-dom";
import { PrimeReactProvider } from "primereact/api";
import "primeflex/primeflex.css";
import { Router, Route, Navigate, Routes, RouteProps } from "react-router-dom";
import LoginPage from "./components/LoginPage";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import SignupPage from "./components/SignupPage";
import ForgotPasswordPage from "./components/ForgotPasswordPage";
import VerificationPage from "./components/VerificationPage";
import ResetPasswordPage from "./components/ResetPasswordPage";
import DashboardPage from "./components/DashboardPage";
import Cookies from "js-cookie";

const isUserAuthenticated = () => {
  const access_token = Cookies.get("access_token");
  const refresh_token = Cookies.get("refresh_token");

  return access_token && refresh_token;
};

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignupPage />} />
      <Route path="/confirmation" element={<VerificationPage />} />
      <Route path="/forgot-password" element={<ForgotPasswordPage />} />
      <Route path="/reset-password" element={<ResetPasswordPage />} />
      <Route path="/dashboard" element={<DashboardPage/>} />
      <Route path="/" element={<Navigate to="/login" />} />
      {/* <Route
        path="/dashboard/*"
        element={
          isUserAuthenticated() ? <DashboardPage /> : <Navigate to="/login" />
        }
      /> */}
    </Routes>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

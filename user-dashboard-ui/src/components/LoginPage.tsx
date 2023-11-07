import React, { useState } from "react";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { Form, Link, Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import Cookies from "js-cookie";

import "primereact/resources/themes/bootstrap4-light-blue/theme.css";
import "react-toastify/dist/ReactToastify.css";
import { AuthResponse } from "../models/AuthResponse";

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const navigateSignup = () => {
    navigate("/signup");
  };

  const navigateDashboard = () => {
    navigate("/dashboard");
  };

  const navigateForgotPassword = () => {
    navigate("/forgot-password");
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:9090/api/v1/auth/signin",
        { email, password }
      );

      if (response.status === 200) {
        const authResponse: AuthResponse = response.data;
        
        Cookies.set("access_token", authResponse.access);
        Cookies.set("refresh_token", authResponse.refresh);

        toast.success("Login was successful!");
        await delay(3000);
        navigateDashboard();
      }
    } catch (error: any) {
      toast.error(
        error.response.data.detail || "An error occurred while logging in."
      );
    }
  };

  return (
    <div
      className="flex align-items-center justify-content-center"
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
      }}
    >
      <div className="surface-card p-4 shadow-2 border-round w-full lg:w-6">
        <div className="text-center mb-5">
          <div className="text-900 text-3xl font-medium mb-3">
            Welcome Back!
          </div>
          <span className="text-600 font-medium line-height-3">
            Don't have an account?
          </span>
          <a
            onClick={navigateSignup}
            className="font-medium no-underline ml-2 text-blue-500 cursor-pointer"
          >
            Signup
          </a>
        </div>

        <form onSubmit={handleSubmit}>
          <label htmlFor="email" className="block text-900 font-medium mb-2">
            Email
          </label>
          <InputText
            id="email"
            type="email"
            placeholder="Email address"
            className="w-full mb-3"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <label htmlFor="password" className="block text-900 font-medium mb-2">
            Password
          </label>
          <InputText
            id="password"
            type="password"
            placeholder="Password"
            className="w-full mb-3"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <div className="flex align-items-center justify-content-between mb-6">
            <div className="flex align-items-center"></div>
            <a
              onClick={navigateForgotPassword}
              className="font-medium no-underline ml-2 text-blue-500 text-right cursor-pointer"
            >
              Forgot password?
            </a>
          </div>

          <Button label="Login" className="w-full" />
        </form>
        <ToastContainer />
      </div>
    </div>
  );
};

function delay(delay: number) {
  return new Promise((res) => setTimeout(res, delay));
}

export default LoginPage;

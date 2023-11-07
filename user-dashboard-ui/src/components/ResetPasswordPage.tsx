import React, { useState, useEffect } from "react";
import { Button } from "primereact/button";
import { Form, Link, Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import { InputText } from "primereact/inputtext";

import "primereact/resources/themes/bootstrap4-light-blue/theme.css";
import "react-toastify/dist/ReactToastify.css";

const ResetPasswordPage: React.FC = () => {
  const token = new URLSearchParams(window.location.search).get("token");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const navigate = useNavigate();

  const navigateLogin = () => {
    navigate("/login");
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:9090/api/v1/auth/reset-password",
        { token, password, confirmPassword }
      );

      if (response.status === 200) {
        toast.success("Your password has been changed.");
        await delay(5000);
        navigateLogin();
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
            Reset Password
          </div>
          <span className="text-600 font-medium line-height-3">
            Please enter a new password to reset the old one.
          </span>
        </div>

        <form onSubmit={handleSubmit}>
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
          <label
            htmlFor="confirm-password"
            className="block text-900 font-medium mb-2"
          >
            Confirm Password
          </label>
          <InputText
            id="confirm-password"
            type="password"
            placeholder="Re-enter password"
            className="w-full mb-3"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
          <div className="flex align-items-center justify-content-between mb-6">
            <div className="flex align-items-center"></div>
          </div>

          <Button label="Change" className="w-full" />
        </form>
        <ToastContainer />
      </div>
    </div>
  );
};

function delay(delay: number) {
  return new Promise((res) => setTimeout(res, delay));
}

export default ResetPasswordPage;

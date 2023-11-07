import React, { useState } from "react";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { Form, Link, Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";

import "primereact/resources/themes/bootstrap4-light-blue/theme.css";
import "react-toastify/dist/ReactToastify.css";

const ForgotPasswordPage: React.FC = () => {
  const [email, setEmail] = useState("");

  const navigate = useNavigate();

  const navigateLogin = () => {
    navigate("/login");
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        `http://localhost:9090/api/v1/auth/forgot-password?email=${email}`
      );

      if (response.status === 200) {
        toast.success("We have sent a reset link to your email!");
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
            Forgot Password
          </div>
          <span className="text-600 font-medium line-height-3">
            Please enter your email below to get a password reset link.
          </span>
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
          <div className="flex align-items-center justify-content-between mb-6">
            <div className="flex align-items-center"></div>
          </div>

          <Button label="Send" className="w-full" />
        </form>
        <ToastContainer />
      </div>
    </div>
  );
};

function delay(delay: number) {
  return new Promise((res) => setTimeout(res, delay));
}

export default ForgotPasswordPage;

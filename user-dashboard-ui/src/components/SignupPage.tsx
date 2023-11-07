import React, { useState } from "react";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { Form, Link, Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";

import "primereact/resources/themes/bootstrap4-light-blue/theme.css";
import "react-toastify/dist/ReactToastify.css";

const SignupPage: React.FC = () => {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
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
        "http://localhost:9090/api/v1/auth/signup",
        { fullName, email, phone, password, confirmPassword }
      );

      if (response.status === 201) {
        toast.success("You signed up successfully! Please confirm your email.");
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
            Create New Account!
          </div>
          <span className="text-600 font-medium line-height-3">
            Already have an account?
          </span>
          <a
            onClick={navigateLogin}
            className="font-medium no-underline ml-2 text-blue-500 cursor-pointer"
          >
            Login
          </a>
        </div>

        <form onSubmit={handleSubmit}>
          <label
            htmlFor="full-name"
            className="block text-900 font-medium mb-2"
          >
            Full Name
          </label>
          <InputText
            id="full-name"
            type="text"
            placeholder="Full name"
            className="w-full mb-3"
            value={fullName}
            onChange={(e) => setFullName(e.target.value)}
            required
          />
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
          <label htmlFor="phone" className="block text-900 font-medium mb-2">
            Phone
          </label>
          <InputText
            id="phone"
            type="tel"
            placeholder="Phone number"
            className="w-full mb-3"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
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

          <Button label="Sign Up" className="w-full" />
        </form>
        <ToastContainer />
      </div>
    </div>
  );
};

function delay(delay: number) {
  return new Promise((res) => setTimeout(res, delay));
}

export default SignupPage;

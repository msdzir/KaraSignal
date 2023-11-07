import React, { useState, useEffect } from "react";
import { Button } from "primereact/button";
import { Form, Link, Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";

import "primereact/resources/themes/bootstrap4-light-blue/theme.css";
import "react-toastify/dist/ReactToastify.css";

const VerificationPage: React.FC = () => {
  const [status, setStatus] = useState<string>("");
  const token = new URLSearchParams(window.location.search).get("token");

  const navigate = useNavigate();

  const navigateLogin = () => {
    navigate("/login");
  };

  useEffect(() => {
    const confirmEmail = async () => {
      try {
        const response = await axios.get(
          `http://localhost:9090/api/v1/auth/confirmation?token=${token}`
        );

        if(response.status === 200) {
            setStatus("success");
        }
      } catch (error: any) {
        setStatus(
          error.response.data.detail || "An error occurred while confirming."
        );
        // toast.error(
        //   error.response.data.detail || "An error occurred while logging in."
        // );
      }
    };

    if (token) {
      confirmEmail();
    } else {
      setStatus("Token not found!");
    }
  }, [token]);

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
            {status === "success" ? (
              <p>Email Verification Done! 🎉</p>
            ) : (
              <p>{status}</p>
            )}
          </div>
          <span className="text-600 font-medium line-height-3">
            {status === "success" ? (
              <p>
                You have confirmed your email successfully! Now you can login to
                your account with the credential.
              </p>
            ) : (
              <p>You can try again later.</p>
            )}
          </span>
        </div>
        <Button onClick={navigateLogin} label="Login" className="w-full" />
        <ToastContainer />
      </div>
    </div>
  );
};

function delay(delay: number) {
  return new Promise((res) => setTimeout(res, delay));
}

export default VerificationPage;

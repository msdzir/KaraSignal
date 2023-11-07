import React from "react";
//import { useRouter } from 'next/router';
import { Toolbar } from "primereact/toolbar";
import { Button } from "primereact/button";
import { MenuItem } from "primereact/menuitem";
import { SplitButton } from "primereact/splitbutton";
import "primeicons/primeicons.css";

import axios from "axios";
import Cookies from "js-cookie";
import { Form, Link, Navigate, useNavigate } from "react-router-dom";

interface DashboardToolBarProps {
  UserName: string | undefined;
}

export function DashboardToolBar(props: DashboardToolBarProps) {
  const navigate = useNavigate();

  const navigateLogin = () => {
    navigate("/login");
  };

  const handleLogout = () => {
    Cookies.remove("access_token");
    Cookies.remove("refresh_token");

    navigateLogin();
  };

  //const router = useRouter();
  const items: MenuItem[] = [
    {
      label: "Update",
      icon: "pi pi-refresh",
    },
    {
      label: "Delete",
      icon: "pi pi-times",
    },
    {
      label: "React Website",
      icon: "pi pi-external-link",
      command: () => {
        window.location.href = "https://reactjs.org/";
      },
    },
    {
      label: "Upload",
      icon: "pi pi-upload",
      command: () => {
        //router.push('/fileupload');
      },
    },
  ];

  const endContent = (
    <React.Fragment>
      <div className="header-btn-holder">
        {/* <Button icon="pi pi-user" className="mr-2" /> */}
        <Button
          onClick={handleLogout}
          icon="pi pi-sign-out"
          className="p-button-danger"
        />
      </div>
    </React.Fragment>
  );

  return (
    <div className="card">
      <Toolbar start={<h3>{props.UserName}</h3>} end={endContent} />
    </div>
  );
}

export default DashboardToolBar;

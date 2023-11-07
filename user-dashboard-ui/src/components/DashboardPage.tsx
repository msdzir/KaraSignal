import { Header } from "./Header";
import { DashboardTabs } from "./DashboardTabs";
import Cookies from "js-cookie";
import axios from "axios";
import UserResponse from "../models/UserResponse";
import React , { useEffect, useState } from 'react';

export function DashboardPage() {
  const token = Cookies.get("access_token");
  const [userResponse, setUserResponse] = useState<UserResponse>();

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  useEffect(() => {
    try {
      axios
        .get("http://localhost:9090/api/v1/users/me", config)
        .then((response) => {
          setUserResponse(response.data);
        });
    } catch (error: any) {}
  }, []);

  return (
    <>
      <div className="main-holder grid-item">
        <Header UserName={userResponse?.email} />
        <DashboardTabs userID={userResponse?.id.toString()} />
      </div>
    </>
  );
}

export default DashboardPage;

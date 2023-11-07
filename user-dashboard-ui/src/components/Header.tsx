import React from "react";
import { Card } from "primereact/card";
import { DashboardToolBar } from "./ToolBar";

interface headerProps {
  UserName: string | undefined;
}

export function Header(props: headerProps) {
  
  return (
    <div className="card">
      <Card>
        <DashboardToolBar UserName={props.UserName} />
      </Card>
    </div>
  );
}

export default Header;

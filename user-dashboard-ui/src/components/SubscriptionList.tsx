import "../styles/DashboardStyle.css";
import React, { useState, useEffect, Component, useLayoutEffect } from "react";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { DashboardSevice } from "../services/DashboardServices";
import { EditDialogButton } from "../components/EditDialogButton";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { Splitter } from "primereact/splitter";

interface Asset {
  assetID: string;
  userID: string;
  assetName: string;
  price: number;
  market: string;
  userSavedName: string;
  targetPrice: number;
}

interface SubscriptionListProps {
  Header: string;
  Target: boolean;
  UserID: string | undefined;

  tableRows: Asset[];
}

export function SubscriptionList(props: SubscriptionListProps) {
  let [lockedCustomers, setLockedCustomers] = useState([]);

  const numberTemplateForTargetPrice = (asset: Asset) => {
    return (
      <>
        {isNaN(asset.targetPrice)
          ? ""
          : Number(asset.targetPrice).toLocaleString("en-US", {
              style: "currency",
              currency: "USD",
            })}
      </>
    );
  };

  const numberTemplateForLivePrice = (asset: Asset) => {
    return (
      <>
        {isNaN(asset.price)
          ? ""
          : Number(asset.price).toLocaleString("en-US", {
              style: "currency",
              currency: "USD",
            })}
      </>
    );
  };

  return (
    <div>
      <div className="subscription-list-header">
        <div>
          <h2>{props.Header}</h2>
        </div>
      </div>
      <div className="card">
        <DataTable
          value={props.tableRows}
          frozenValue={lockedCustomers}
          scrollable
          scrollHeight="400px"
          sortField="price"
          sortOrder={-1}
          tableStyle={{ minWidth: "50rem" }}
        >
          <Column
            field="userSavedName"
            header="Lable"
            sortable
            style={{ width: "20%" }}
          ></Column>
          <Column
            field="assetName"
            header="Symbol Name"
            sortable
            style={{ width: "20%" }}
          ></Column>
          <Column
            field="price"
            header="Live-Price"
            body={numberTemplateForLivePrice}
            sortable
            style={{ width: "20%" }}
          ></Column>
          <Column
            field="marketName"
            header="Market"
            sortable
            style={{ width: "20%" }}
          ></Column>
          {props.Target === true && (
            <Column
              field="targetPrice"
              header="Target-Price"
              body={numberTemplateForTargetPrice}
              sortable
              style={{ width: "20%" }}
            ></Column>
          )}
        </DataTable>
      </div>
    </div>
  );
}

export default SubscriptionList;

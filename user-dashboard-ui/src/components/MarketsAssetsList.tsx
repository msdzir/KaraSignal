import React, { useState, useEffect, useRef } from "react";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { DashboardSevice } from "../services/DashboardServices";
import { Button } from "primereact/button";
import "../../Style/DashboardStyle.css";
import { Toast } from "primereact/toast";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

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
  marketName: string;
  userID: string;
}

export function MarketsAssetsList(props: SubscriptionListProps) {
  const [Assets, setAssets] = useState<Asset[]>([]);
  const [lockedCustomers, setLockedCustomers] = useState([]);
  const toast = useRef<Toast>(null);

  const addBTNClickFunction = (asset: Asset) => {
    DashboardSevice.addNewSubscription(
      props.userID,
      asset.assetID,
      (document.getElementById(asset.assetID + "USN") as HTMLInputElement)
        .value,
      (document.getElementById(asset.assetID + "TP") as HTMLInputElement).value
    ).then((text) => {
      toast.current?.show({ severity: "info", summary: "Info", detail: text });
    });
  };

  const actionBodyTemplate = (rowData: Asset) => {
    return (
      <React.Fragment>
        <div className="card flex justify-content-center">
          <Toast ref={toast} />
          <Button
            icon="pi pi-plus"
            rounded
            outlined
            className="mr-2"
            onClick={() => {
              addBTNClickFunction(rowData);
            }}
          />
        </div>
      </React.Fragment>
    );
  };

  const inputLabelTemplate = (rowData: Asset) => {
    return (
      <React.Fragment>
        <input
          id={rowData.assetID + "USN"}
          type="text"
          defaultValue={rowData.assetName}
        />
      </React.Fragment>
    );
  };

  const inputTargetPriceTemplate = (rowData: Asset) => {
    return (
      <React.Fragment>
        <input id={rowData.assetID + "TP"} type="text" defaultValue={-1} />
      </React.Fragment>
    );
  };

  useEffect(() => {
    let allAssetsData: any = [];
    DashboardSevice.getAllAssetsList().then((data) => {
      for (let index in data) allAssetsData.push(data[index]);

      setAssets(allAssetsData);
    });

    const socket = new SockJS("http://localhost:8092/stomp");
    const stompClient = Stomp.over(socket);
    stompClient.connect({ withCredentials: true }, (frame: any) => {
      stompClient.subscribe("/topic/livePrice", (payload: any) => {
        console.log("ALL Assets Live Price", payload.body);
        let assetsLivePriceJSON = JSON.parse(payload.body);
        for (var row in allAssetsData) {
          let assetNameTemp = allAssetsData[row].assetName;
          allAssetsData[row].price = Number(
            assetsLivePriceJSON[assetNameTemp]
          ).toLocaleString("en-US", { style: "currency", currency: "USD" });
        }
        setAssets(allAssetsData);
      });
    });

    setInterval(() => {
      stompClient.send(
        "/app/getLivePrice",
        {},
        JSON.stringify({
          message: "ALL",
        })
      );
    }, 1000);
  }, []);

  return (
    <div>
      <div className="card">
        <DataTable
          value={Assets}
          frozenValue={lockedCustomers}
          scrollable
          scrollHeight="400px"
          sortField="price"
          sortOrder={-1}
          tableStyle={{ minWidth: "50rem" }}
        >
          <Column
            field="assetName"
            header="Name"
            sortable
            style={{ width: "20%" }}
          ></Column>
          <Column
            field="userSavedName"
            header="Lable"
            body={inputLabelTemplate}
            sortable
            style={{ width: "20%" }}
          ></Column>
          <Column
            field="targetPrice"
            header="Target Price"
            body={inputTargetPriceTemplate}
            sortable
            style={{ width: "20%" }}
          ></Column>
          <Column
            header="Subscription"
            body={actionBodyTemplate}
            exportable={false}
            style={{ minWidth: "12rem" }}
          ></Column>
        </DataTable>
      </div>
    </div>
  );
}

export default MarketsAssetsList;

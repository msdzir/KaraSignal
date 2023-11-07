import "../styles/DashboardStyle.css";
import React, { useState, useEffect, useRef, createElement } from "react";
import { DataTable, DataTableRowEditCompleteEvent } from "primereact/datatable";
import { Column } from "primereact/column";
import { DashboardSevice } from "../services/DashboardServices";
import { Button } from "primereact/button";

import { ConfirmDialog, confirmDialog } from "primereact/confirmdialog";
import { Toast } from "primereact/toast";
import AddNewAssetForm from "./AddNewAssetForm";
import { InputNumber, InputNumberChangeEvent } from "primereact/inputnumber";
import { InputText } from "primereact/inputtext";

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
  header: string;
  userID: string | undefined;
  marketName: string;
}

export function EditSubscribedList(props: SubscriptionListProps) {
  const [Assets, setAssets] = useState<Asset[] | null | any>([]);
  const [visible, setVisible] = useState<boolean>(false);

  const [statuses] = useState<string[]>(["INSTOCK", "LOWSTOCK", "OUTOFSTOCK"]);

  const toast = useRef<Toast>(null);

  useEffect(() => {
    DashboardSevice.getSubscriptionListInTheMarket(
      props.userID,
      props.marketName
    ).then((data) => {
      if (data.length > 0) setAssets(data);
    });
  }, []);

  const getSubscriptionListFunction = () => {
    DashboardSevice.getSubscriptionListInTheMarket(
      props.userID,
      props.marketName
    ).then((data) => {
      if (data != undefined && data.length > 0) setAssets(data);
      else getSubscriptionListFunction();
    });
  };

  const DeleteBTNClickFunction = (asset: Asset) => {
    confirmDialog({
      message: "Do you want to delete " + asset.assetName + " asset?",
      header: "Delete Confirmation",
      icon: "pi pi-info-circle",
      acceptClassName: "p-button-danger",
      accept() {
        DashboardSevice.deleteSubscription(asset.userID, asset.assetID);
        getSubscriptionListFunction();
        toast.current?.show({
          severity: "warn",
          summary: "Confirmed",
          detail: "You have deleted asset!",
          life: 3000,
        });
      },
      reject() {
        toast.current?.show({
          severity: "info",
          summary: "Rejected",
          detail: "You have rejected",
          life: 3000,
        });
        getSubscriptionListFunction();
      },
    });
  };

  const actionBodyTemplate = (rowData: Asset) => {
    let DeleteBTN = createElement(
      Button,
      {
        onClick: () => {
          DeleteBTNClickFunction(rowData);
        },
        rounded: true,
        outlined: true,
        icon: "pi pi-trash",
        className: "mr-2",
        severity: "danger",
      },
      null
    );

    return (
      <React.Fragment>
        <Toast ref={toast} />
        <ConfirmDialog />
        <div className="card inner-list-btn-holder">{DeleteBTN}</div>
      </React.Fragment>
    );
  };

  const numberTemplateForTargetPrice = (asset: Asset) => {
    return (
      <>
        {isNaN(asset.targetPrice) || Number(asset.targetPrice) == -1
          ? ""
          : Number(asset.targetPrice).toLocaleString("en-US", {
              style: "currency",
              currency: "USD",
            })}
      </>
    );
  };

  const onRowEditComplete = (e: DataTableRowEditCompleteEvent) => {
    if (e.newData.targetPrice == null) e.newData.targetPrice = -1;

    DashboardSevice.editSubscribedAsset(
      e.newData.userID,
      e.newData.assetID,
      e.newData.userSavedName,
      e.newData.targetPrice
    ).then((text) => {
      toast.current?.show({
        severity: "info",
        summary: "Edit " + e.newData.assetName,
        detail: text,
        life: 3000,
      });
    });

    let _products = [...Assets];
    let { newData, index } = e;

    _products[index] = newData;

    setAssets(_products);
    getSubscriptionListFunction();
  };

  const textEditor = (options: any) => {
    return (
      <InputText
        type="text"
        defaultValue={options.value}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
          options.editorCallback(e.target.value)
        }
      />
    );
  };

  const priceEditor = (options: any) => {
    if (options.value == -1)
      return (
        <InputNumber
          inputId="currency-us"
          onChange={(e: InputNumberChangeEvent) =>
            options.editorCallback(e.value)
          }
          mode="currency"
          currency="USD"
          locale="en-US"
        />
      );

    return (
      <InputNumber
        inputId="currency-us"
        onChange={(e: InputNumberChangeEvent) =>
          options.editorCallback(e.value)
        }
        mode="currency"
        currency="USD"
        locale="en-US"
        value={options.value}
      />
    );
  };

  const priceBodyTemplate = (rowData: any) => {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(rowData.price);
  };

  return (
    <>
      {visible ? (
        <>
          <AddNewAssetForm
            marketName={props.marketName}
            userID={props.userID}
            setVisible={setVisible}
            reloadListFinction={getSubscriptionListFunction}
          />
        </>
      ) : (
        <>
          <div className="card">
            <DataTable
              value={Assets}
              editMode="row" 
              dataKey="id"
              onRowEditComplete={onRowEditComplete}
              scrollable
              scrollHeight="400px"
              sortField="price"
              sortOrder={-1}
              tableStyle={{ minWidth: "50rem" }}
            >
              <Column
                field="userSavedName"
                header="Lable"
                editor={(options) => textEditor(options)}
                style={{ width: "30%" }}
                sortable
              ></Column>
              <Column
                field="assetName"
                header="Name"
                style={{ width: "20%" }}
                sortable
              ></Column>
              <Column
                field="targetPrice"
                body={numberTemplateForTargetPrice}
                editor={(options) => priceEditor(options)}
                header="Target-Price"
                style={{ width: "25%" }}
                sortable
              ></Column>
              <Column
                rowEditor
                headerStyle={{ width: "5%", minWidth: "8rem" }}
                bodyStyle={{ textAlign: "center" }}
              ></Column>
              <Column body={actionBodyTemplate} exportable={false}></Column>
            </DataTable>
          </div>
          <div className="subscription-btn-hodler">
            <Button
              label="New Subscription"
              visible={!visible}
              size="small"
              icon="pi pi-plus"
              onClick={() => setVisible(true)}
            />
          </div>
        </>
      )}
    </>
  );
}

export default EditSubscribedList;

import React, { useEffect, useRef, useState } from "react";
import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";
import {
  InputNumber,
  InputNumberValueChangeEvent,
} from "primereact/inputnumber";
import { Dropdown, DropdownChangeEvent } from "primereact/dropdown";
import "../styles/DashboardStyle.css";
import { Button } from "primereact/button";
import { DashboardSevice } from "../services/DashboardServices";
import { Toast } from "primereact/toast";
import { toast } from "react-toastify";

interface Asset {
  assetDescription: string;
  assetID: string;
  assetName: string;
  marketDescription: number;
  marketID: string;
  marketName: string;
}

interface AddNewAssetsFormProps {
  marketName: string;
  userID: string | undefined;

  setVisible: (visible: boolean) => any;
  reloadListFinction: () => any;
}

export function AddNewAssetsForm(props: AddNewAssetsFormProps) {
  const [targetPrice, setTargetPrice] = useState<number | null>();
  const [valueLabel, setvalueLabel] = useState<string>();
  const [selectedAsset, setSelectedAsset] = useState<Asset | null>(null);
  const [dropBoxAssets, setDropBoxAsset] = useState<Asset[]>();

  const toast = useRef<Toast>(null);

  useEffect(() => {
    DashboardSevice.getAllAssetsList().then((data) => {
      setDropBoxAsset(data);
    });
  }, []);

  function addBTNClickFunction() {
    if (selectedAsset?.assetID != undefined && targetPrice != null) {
      DashboardSevice.addNewSubscription(
        props.userID,
        selectedAsset?.assetID,
        String(valueLabel),
        String(targetPrice)
      ).then((text) => {
        toast.current?.show({
          severity: "info",
          summary: "Info",
          detail: text,
        });
      });
    } else if (selectedAsset?.assetID != undefined && targetPrice == null) {
      DashboardSevice.addNewSubscription(
        props.userID,
        selectedAsset?.assetID,
        String(valueLabel),
        "-1"
      ).then((text) => {
        toast.current?.show({
          severity: "info",
          summary: "Info",
          detail: text,
        });
      });
    } else {
      toast.current?.show({
        severity: "warn",
        summary: "Info",
        detail: "Select a symbole",
      });
    }

    props.reloadListFinction();
  }

  return (
    <>
      <div className="add-new-asset-form-holder">
        <div className="card bombo-box-holder">
          <label htmlFor="">Symbols : </label>
          <Dropdown
            value={selectedAsset}
            onChange={(e: DropdownChangeEvent) => setSelectedAsset(e.value)}
            options={dropBoxAssets}
            optionLabel="assetName"
            placeholder="Select an asset"
            className="w-full md:w-20rem"
          />
        </div>
        <div className="inputs-holder">
          <div className="label-input-holder">
            <label htmlFor="username">Asset-Label</label>
            <InputText
              id="username"
              aria-describedby="username-help"
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                setvalueLabel(e.target.value)
              }
            />
            <small id="username-help">Enter a label for your asset.</small>
          </div>
          <div className="target-price-input-holder">
            <label htmlFor="currency-us" className="font-bold block mb-2">
              Target-Price
            </label>
            <InputNumber
              inputId="currency-us"
              value={targetPrice}
              onValueChange={(e: InputNumberValueChangeEvent) =>
                setTargetPrice(e.target.value)
              }
              mode="currency"
              currency="USD"
              locale="en-US"
            />
          </div>
        </div>
      </div>
      <div className="subscription-btn-hodler">
        <Button
          label="Back"
          className="p-button-danger"
          icon="pi pi-times-circle"
          size="small"
          onClick={() => props.setVisible(false)}
        />
        <React.Fragment>
          <div className="card flex justify-content-center">
            <Toast ref={toast} />
            <Button
              label="Add"
              className="p-button-info"
              icon="pi pi-plus"
              size="small"
              onClick={addBTNClickFunction}
            />
          </div>
        </React.Fragment>
      </div>
    </>
  );
}

export default AddNewAssetsForm;

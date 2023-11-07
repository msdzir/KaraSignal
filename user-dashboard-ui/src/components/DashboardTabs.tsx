import { SubscriptionList } from "./SubscriptionList";
import { Card } from "primereact/card";
import { useEffect, useState } from "react";
import EditDialogButton from "../components/EditDialogButton";
import { DashboardSevice } from "../services/DashboardServices";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import Cookies from "js-cookie";
import axios from "axios";
import UserResponse from "../models/UserResponse";

interface Asset {
  assetID: string;
  userID: string;
  assetName: string;
  price: number;
  market: string;
  userSavedName: string;
  targetPrice: number;
}

interface headerProps {
  userID: string | undefined;
}

export function DashboardTabs(props: headerProps) {
  const [targetTableRows, setTargetTableRows] = useState<any[]>([]);
  const [nonTargetTableRows, setNonTargetTableRows] = useState<Asset[]>([]);
  let symbolsLivePrice: string[] = [];

  useEffect(() => {
    // let userId = props.userResponse.id.toString;

    let ttr: any = [];
    let nttr: any = [];
    let initialData: any = [];
    DashboardSevice.getSubscriptionList(props?.userID).then((data) => {
      for (let index in data) {
        initialData.push(data[index]);
        if (data[index].targetPrice != -1) ttr.push(data[index]);
        else if (data[index].targetPrice == -1) nttr.push(data[index]);
        if (!symbolsLivePrice.includes(data[index].assetName))
          symbolsLivePrice.push(data[index].assetName);
      }
      console.log("initail datas is : ", initialData);
      console.log("ttr row is : ", ttr);
      if (ttr.length > 0) setTargetTableRows(ttr);
      console.log("begin target table row is : ", targetTableRows);
      if (nttr.length > 0) setNonTargetTableRows(nttr);
    });

    const socket = new SockJS("http://localhost:8092/stomp");
    const stompClient = Stomp.over(socket);
    stompClient.connect({ withCredentials: true }, (frame: any) => {
      stompClient.subscribe("/topic/livePrice", (payload: any) => {
        let assetsLivePriceJSON = JSON.parse(payload.body);
        for (var row in ttr) {
          let assetNameTemp = ttr[row].assetName;
          ttr[row].price = assetsLivePriceJSON[assetNameTemp];
        }
        if (ttr.length > 0) setTargetTableRows(ttr);
        for (var row in nttr) {
          let assetNameTemp = nttr[row].assetName;
          nttr[row].price = assetsLivePriceJSON[assetNameTemp];
        }
        if (nttr.length > 0) setNonTargetTableRows(nttr);
      });
    });

    // setInterval(() => {
    //   stompClient.send(
    //     "/app/getLivePrice",
    //     {},
    //     JSON.stringify({
    //       message: symbolsLivePrice,
    //     })
    //   );
    // }, 1000);
  }, []);

  return (
    <div className="card">
      <Card>
        <div>
          <EditDialogButton userID={props.userID} />
        </div>
        <SubscriptionList
          Header="Target Subscripted Signals"
          Target={true}
          UserID={props.userID}
          tableRows={targetTableRows}
        />
        <SubscriptionList
          Header="Subscripted Signals"
          Target={false}
          UserID={props.userID}
          tableRows={nonTargetTableRows}
        />
      </Card>
    </div>
  );
}

export default DashboardTabs;

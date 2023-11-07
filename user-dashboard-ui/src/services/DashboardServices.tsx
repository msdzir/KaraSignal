import axios from "axios";

export class DashboardSevice {

    static getSubscriptionList(UserID: string | undefined) {
        return fetch('http://localhost:9090/dashBoard/showUsersListOfActiveSignal/')
            .then(res => res.json())
            .then(d => d.root);
    }

    static getSubscriptionListInTheMarket(userID: string | undefined, marketName: string) {
        return fetch('http://localhost:9090/dashBoard/showUsersListOfActiveSignal/' + marketName)
            .then(res => res.json())
            .then(d => d.root);
    }

    static getAllAssetsList() {
        return fetch('http://localhost:9090/dashBoard/getAllSiganls')
            .then(res => res.json())
            .then(d => d.root);
    }

    static addNewSubscription(userID: string | undefined, assetID: string, userSavedName: string, targetPrice: string | null | undefined) {
        console.log('userSavedName : ', userSavedName);
        if (userSavedName == null || userSavedName == '' || typeof userSavedName === 'undefined') {
            console.log('here');
            userSavedName = "New_Signal";
        }

        if (targetPrice === '-1') {
            return fetch('http://localhost:9090/dashBoard/addNewAssetsSubscription/' + assetID + '/' + userSavedName)
                .then(res => res.text());
        }
        else {
            return fetch('http://localhost:9090/dashBoard/addNewAssetsSubscription/' + assetID + '/' + userSavedName + '/' + targetPrice)
                .then(res => res.text());
        }
    }

    static deleteSubscription(userID: string | undefined, assetID: string) {
        return fetch('http://localhost:9090/dashboard/deleteAssetSubscription/' + assetID)
            .then(res => res.text());
    }

    static GetAssetsName() {
        return fetch('data/SubscriptionListTestData.json')
            .then(res => res.json())
            .then(d => d.root);
    }

    static editSubscribedAsset(
        userID: string | undefined,
        assetID: string,
        userSavedName: string | null | undefined,
        targetPrice: string | null | undefined
    ) {
        console.log(userID);
        let data = {
            userID: userID,
            assetID: assetID,
            userSavedName: userSavedName,
            targetPrice: targetPrice
        };
        console.log(JSON.stringify(data));
        return fetch("http://localhost:9090/dashBoard/editAssetsSubscription",
            {
                method: "POST", // *GET, POST, PUT, DELETE, etc.
                mode: "cors", // no-cors, *cors, same-origin
                cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
                credentials: "same-origin", // include, *same-origin, omit
                headers: {
                    "Content-Type": "application/json",
                    // 'Content-Type': 'application/x-www-form-urlencoded',
                },
                redirect: "follow", // manual, *follow, error
                referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
                body: JSON.stringify(data), // body data type must match "Content-Type" header
            }
        )
            .then(res => res.json())
            .then(d => d.root)
            .catch(function (res) { console.log(res) });
    }

}
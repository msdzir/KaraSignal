
import React, { useState } from "react";
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { TabView, TabPanel } from 'primereact/tabview';
import { EditSubscribedList } from './EditSubscribedAssetsList';

interface EditDialogButtonProps {
    userID: string | undefined;
}

export function EditDialogButton(props: EditDialogButtonProps) {
    const [visible, setVisible] = useState(false);

    return (
        <div className="card flex">
            <Button label="Subscriptions" icon="pi pi-external-link" onClick={() => setVisible(true)} />
            <Dialog header="Edit Subscription List" visible={visible} 
            onHide={() => {
                setVisible(false);
                window.location.reload();
            }}
                style={{ minWidth: '50vw' }} breakpoints={{ '960px': '75vw', '641px': '100vw' }}>
                <TabView>
                    <TabPanel header="Binance" className="edit-tab-panel">
                        <EditSubscribedList
                            header='Subscription List'
                            userID={props.userID}
                            marketName='Binance'
                        />
                    </TabPanel>
                </TabView>
            </Dialog>
        </div>
    )
}

export default EditDialogButton;

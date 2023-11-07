
import React, { useState, useRef } from 'react';
import { ConfirmDialog, confirmDialog } from 'primereact/confirmdialog';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';

interface ConfirmProps{
    AssetID : String,
    UserID : string
}

export function DeleteConfirmButton(props : ConfirmProps) {
    const [visible, setVisible] = useState<boolean>(false);
    const toast = useRef<Toast>(null);

    const accept = () => {
        const r = "";
        fetch("http://localhost:8092/dashboard/deleteAssetSubscription/"+props.UserID+"/"+props.AssetID).then(res => res.text()).then(d => r);
        toast.current?.show({ severity: 'warn', summary: props.AssetID, detail: r, life: 3000 });
    }

    const reject = () => {
        toast.current?.show({ severity: 'info', summary: 'cancle', detail: 'You have rejected', life: 3000 });
    }
    
    return (
        <>
            <Toast ref={toast} />
            <ConfirmDialog visible={visible} onHide={() => setVisible(false)} message="Are you sure you want to proceed?" 
                header="Confirmation" icon="pi pi-exclamation-triangle" accept={accept} reject={reject} />
            <div className="card flex justify-content-center">
                <Button onClick={() => setVisible(true)} icon="pi pi-times" label="Delete" severity="danger" className='delete-button' text raised />
            </div>
        </>
    )
}

export default DeleteConfirmButton;
        
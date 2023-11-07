import React from 'react'; 
import { Children } from "react";
import { Fieldset } from 'primereact/fieldset';

export function PluginHolder(children: any) {
    const legendTemplate = (
        <div className="flex align-items-center text-primary">
            <span className="pi pi-user mr-2"></span>
            <span className="font-bold text-lg">Dashboard</span>
        </div>
    );

    return (
        <div className="card">
            <Fieldset legend={legendTemplate}>
                {children}
            </Fieldset>
        </div>
    )
}

export default PluginHolder;
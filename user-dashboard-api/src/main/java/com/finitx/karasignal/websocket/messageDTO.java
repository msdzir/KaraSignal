package com.finitx.karasignal.websocket;

public class messageDTO {   

    private String[] message;
    
    public messageDTO(){
    }
    
    public messageDTO(String[] message){
        this.message = message;
    }
    
    public String[] getMessage() {
    
        return message;
    
    }
    
    public void setMessage(String[] message) {
    
        this.message = message;
    
    }

}
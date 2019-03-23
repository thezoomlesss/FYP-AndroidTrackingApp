package com.example.thezo.fyp;

public class SingleMessage {
    private String sender;
    private String reciever;
    private String message;
    private String conversation;

    public SingleMessage(String numberPlate, String reciever,String message) {
        this.sender = numberPlate;
        this.reciever = reciever;
        this.message = message;
        this.conversation = "server " + numberPlate;
    }

    public String getConversation() {
        return conversation;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String numberPlate) {
        this.sender = numberPlate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

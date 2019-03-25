package com.example.thezo.fyp;

public class SingleMessage {
    private String sender;
    private String reciever;
    private String message;
    private String conversation;
    private String timestamp;

    public SingleMessage(String sender, String reciever, String message, String timestamp, String conversation) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.conversation = conversation;
        this.timestamp = timestamp;
    }

    public String printOut(){
        return "sender: "+ sender + "reciever: " + reciever + "msg: " + message + " conv:" + conversation + "time: " + timestamp;
    }
    public String getConversation() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

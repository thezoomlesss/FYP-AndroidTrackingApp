package com.example.thezo.fyp;

public class SingleMessage {
    private String numberPlate;
    private String message;

    public SingleMessage(String numberPlate, String message) {
        this.numberPlate = numberPlate;
        this.message = message;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.example.realtimemessagingchat.Model;

public class Massege {
    String sender;
    String reciver;
    String message;

    public Massege() {
    }

    public Massege(String sender, String reciver, String message) {
        this.sender = sender;
        this.reciver = reciver;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.thanhha.myapplication.models.dto;

import java.util.Date;

public class ChatMessage {
    private String senderId;
    private String receiverId;
    private String dateTime;
    private String message;

    private Date dateObject;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateObject() {
        return dateObject;
    }

    public void setDateObject(Date dateObject) {
        this.dateObject = dateObject;
    }

    public ChatMessage(String senderId, String receiverId, String dateTime, String message, Date dateObject) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.dateTime = dateTime;
        this.message = message;
        this.dateObject = dateObject;
    }

    public ChatMessage() {
    }
}

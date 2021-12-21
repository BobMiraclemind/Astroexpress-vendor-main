package com.ashishandroid.ashishkumar.ModelClass;

public class Message {
    public String messageId, message, senderId, imageUrl, sender, state;
    public long timestamp;


    public Message() {
    }

    public Message(String message, String senderId, long timestamp, String sender) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSender() {
        return sender;
    }

    public String getState() {
        return state;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getSenderId() {
        return senderId;
    }

}

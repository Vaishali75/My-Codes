package com.example.hp.quesec.Beans;

public class ChatBean
{
    private String senderId,receiverId,chatmsg,chatId;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

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

    public String getChatmsg() {
        return chatmsg;
    }

    public void setChatmsg(String chatmsg) {
        this.chatmsg = chatmsg;
    }
}

package com.openmarket.streamsworkshop.sms;

public class SmsMessage {

    private String phoneNumber;
    private String messageContent;

    public SmsMessage(String phoneNumber, String messageContent) {
        this.phoneNumber = phoneNumber;
        this.messageContent = messageContent;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String messageContent() {
        return messageContent;
    }
}

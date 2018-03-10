package com.openmarket.streamsworkshop.sms;

import java.util.ArrayList;
import java.util.List;

public class SomeMessages {

    public static List<SmsMessage> getAFew() {
        List<SmsMessage> messages = new ArrayList<>();

        messages.add(new SmsMessage("447700000001", "yes"));
        messages.add(new SmsMessage("447700000002", "yes"));
        messages.add(new SmsMessage("447700000003", "Yes"));
        messages.add(new SmsMessage("447700000004", "Yes plz"));
        messages.add(new SmsMessage("447700000005", "no"));
        messages.add(new SmsMessage("447700000006", "No"));
        messages.add(new SmsMessage("447700000007", "no i don't know this"));

        messages.add(new SmsMessage("497850000001", "yes please"));
        messages.add(new SmsMessage("33585000026", "no merci"));
        return messages;
    }

    public static List<SmsMessage> getLots() {
        int messageListSize = 1000000;
        List<SmsMessage> lotsOfMessages = new ArrayList<>(messageListSize);
        for (int i = 0; i < messageListSize/2; i++) {
            lotsOfMessages.add(new SmsMessage("44" + i, "yes you are number " + i));
        }
        for (int i = 0; i < messageListSize/2; i++) {
            lotsOfMessages.add(new SmsMessage("49" + i, "no you are number " + i));
        }
        return lotsOfMessages;
    }
}

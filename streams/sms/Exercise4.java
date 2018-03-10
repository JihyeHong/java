package com.openmarket.streamsworkshop.sms;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Exercise4 extends BaseExercise {

    public static void main (String[] args) {

        Map<String, List<SmsMessage>> map = getUkMessagesPerKeyword(SomeMessages.getAFew());

        printMap(map);

    }

    public static Map<String, List<SmsMessage>> getUkMessagesPerKeyword(List<SmsMessage> messages) {
        Map<String, List<SmsMessage>> out = new HashMap<>();
        for (SmsMessage message : messages) {
            if (message.phoneNumber().startsWith("44")) {
                String keyword = message.messageContent().split(" ")[0].toUpperCase();
                if (out.containsKey(keyword)) {
                    // append to existing list
                    List<SmsMessage> current = out.get(keyword);
                    current.add(message);
                    out.put(keyword, current);
                }
                else {
                    // create new list
                    List<SmsMessage> newList = new LinkedList<>();
                    newList.add(message);
                    out.put(keyword, newList);
                }
            }
        }
        return out;
    }

    /*
    public static Map<String, List<SmsMessage>> getUkMessagesPerKeywordStream(List<SmsMessage> messages) {
        Map<String, List<SmsMessage>> out = messages.stream()
                .filter(m -> m.phoneNumber().startsWith("44"))
                .collect( m -> m.messageContent().split(" ")[0].toUpperCase());

        return out;
    }*/

    public static Map<String, List<String>> test(List<SmsMessage> messages) {
        Map<String, List<String>> out2 = messages.stream()
                .filter(m -> m.phoneNumber().startsWith("44"))
                .map(m -> m.messageContent())
                .collect(Collectors.groupingBy(m -> m.split(" ")[0].toUpperCase()));

        return out2;
    }
    
}

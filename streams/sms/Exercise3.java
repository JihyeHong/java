package com.openmarket.streamsworkshop.sms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Exercise3 extends BaseExercise {

    public static void main (String[] args) {
        List<String> list = getAllMessageContentStream(SomeMessages.getAFew());
        System.out.println(list);
    }

    public static List<String> getAllMessageContent(List<SmsMessage> messages) {
        List content = new LinkedList<String>();
        for (SmsMessage message : messages) {
            content.add(message.messageContent());
        }
        return content;
    }

    public static List<String> getAllMessageContentStream(List<SmsMessage> messages) {

        List content = messages.stream()
                .map(m -> m.messageContent())
                .collect(Collectors.toList());


        return content;
    }
    
}

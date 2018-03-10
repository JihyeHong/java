package com.openmarket.streamsworkshop.sms;

import java.util.List;

public class Exercise1 extends BaseExercise {

    public static void main (String[] args) {
        printUkNumbersStream(SomeMessages.getAFew());
    }

    private static void printUkNumbers(List<SmsMessage> messages) {
        for (SmsMessage message : messages) {
            if (message.phoneNumber().startsWith("44")) {
                System.out.println(message.phoneNumber());
            }
        }
    }

    private static void printUkNumbersStream(List<SmsMessage> messages) {
        messages.stream()
                .filter(m -> m.phoneNumber().startsWith("44"))
                .forEach(m -> System.out.println(m.phoneNumber()));

        messages.stream()
                .filter(m -> true)
                .forEach(m-> System.out.println("test"));

    }


}

package com.openmarket.streamsworkshop.sms;

import java.util.List;

public class Exercise2 extends BaseExercise {

    public static void main (String[] args) {
        printLocalisedPhoneNumbersStream(SomeMessages.getAFew());
    }

    private static void printLocalisedPhoneNumbers(List<SmsMessage> messages) {
        for (SmsMessage message : messages) {
            System.out.println(localisePhoneNumber(message.phoneNumber()));
        }
    }

    private static void printLocalisedPhoneNumbersStream(List<SmsMessage> messages) {
        messages.stream()
                .map(m -> m.phoneNumber().substring(2))
                .forEach(number -> System.out.println(0+number));

        /*solution*/
        messages.stream()
                .map(m -> localisePhoneNumber(m.phoneNumber()))
                .forEach(number -> System.out.println(0+number));

    }

    private static String localisePhoneNumber(String phoneNumber) {
        String suffix = phoneNumber.substring(2);
        return 0 + suffix;
    }

}

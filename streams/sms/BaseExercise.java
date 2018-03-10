package com.openmarket.streamsworkshop.sms;

import java.util.List;
import java.util.Map;

public class BaseExercise {

    protected static void printMap(Map<String, List<SmsMessage>> map) {
        map.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue().size()));
    }
}

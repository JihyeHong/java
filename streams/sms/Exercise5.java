package com.openmarket.streamsworkshop.sms;

import java.util.stream.IntStream;

public class Exercise5 extends BaseExercise {

    public static void main (String[] args) {
        IntStream.range(1, 1000).forEach(num-> System.out.println(num));
    }
    
}

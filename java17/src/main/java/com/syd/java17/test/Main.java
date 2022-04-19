package com.syd.java17.test;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//        System.out.println(Instant.now());
//        SimpleDateFormat sdf = new SimpleDateFormat();
        DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse("07/01/2022 14:00:00", inFormat);
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("EEE, MMM d yyyy, KK:mm a");
        String outDateStr = ldt.format(outFormat);
        System.out.println(outDateStr);
    }
}

package org.main;

import listeners.TestListener;
import timetableBot.TimetableBot;

public class Main {
    public static void main(String[] args) {
        System.out.println("App ran!");
        TimetableBot.build();
        TimetableBot.addListener(new TestListener());
        TimetableBot.run();
    }
}
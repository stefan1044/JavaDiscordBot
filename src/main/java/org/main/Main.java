package org.main;

import codeGenerators.StaticCodeFactory;
import codeGenerators.StaticCodesManager;
import listeners.TestListener;
import timetableBot.TimetableBot;

import java.sql.SQLOutput;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Main.generateNewCodes(15);
        Main.printCodes();
        Main.startBot();
    }

    private static void startBot(){
        System.out.println("Starting bot!");
        StaticCodesManager.loadCodesList();
        System.out.println(StaticCodesManager.getCodes());

        TimetableBot.build();
        TimetableBot.addListener(new TestListener());
        TimetableBot.run();
        TimetableBot.loadMembers();
    }
    private static void generateNewCodes(int count){
        System.out.println("Generating " + count + " new codes!");

        IntStream.range(0, count).forEach( (n) -> {
            var code = StaticCodeFactory.getRandomCode();
            StaticCodesManager.addCodeToList(code);
        });
        StaticCodesManager.writeCodesToDatabase();
    }
    private static void addNewCodes(int count){
        System.out.println("Adding " + count + " new codes!");

        StaticCodesManager.loadCodesList();
        IntStream.range(0, count).forEach( (n) -> {
            var code = StaticCodeFactory.getRandomCode();
            StaticCodesManager.addCodeToList(code);
        });
    }
    private static void printCodes(){
        System.out.println("Printing codes!");

        StaticCodesManager.loadCodesList();
        var codeList = StaticCodesManager.getCodes();
        for(var code: codeList){
            System.out.println(code);
        }
    }
}
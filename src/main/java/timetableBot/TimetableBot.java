package timetableBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class TimetableBot {

    private static JDA jda;

    public static void build() {
        // TODO: Get token from .env file
        TimetableBot.jda = JDABuilder.createDefault("MTExMDY0NDYxNTk4OTcwNjg3NA.GZb_tj.MiJdFefxoSlwoIxlnX4GnrhsMupyXqsqldO6Ag").build();
    }
    public static void run(){
        if (TimetableBot.jda == null){
            throw new RuntimeException("JDA was not built before running!");
        }
        try {
            jda.awaitReady();
        } catch (InterruptedException e){
            System.out.println("Bot was interrupted while building! Closing process");
            System.exit(1);
        }
    }

    public static void addListener(ListenerAdapter listener){
        TimetableBot.jda.addEventListener(listener);
    }
}

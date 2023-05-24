package timetableBot;

import listeners.NewMemberListener;
import listeners.RemoveMemberListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.List;


public class TimetableBot {

    private static JDA jda;
    private static Guild guild;
    private static List<Member> members;

    public static void build() {
        var env = System.getenv();
        TimetableBot.jda = JDABuilder
                .createDefault(env.get("TOKEN"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();
        TimetableBot.addDefaultListeners();
    }
    public static void run(){
        if (TimetableBot.jda == null){
            throw new RuntimeException("JDA was not built before running!");
        }
        try {
            jda.awaitReady();
            guild = jda.getGuilds().get(0);
        } catch (InterruptedException e){
            System.out.println("Bot was interrupted while building! Closing process");
            System.exit(1);
        }
    }

    /**
     * Method that should be called when building the JDA. Adds all event listeners that need to be present on start.
     */
    private static void addDefaultListeners(){
        TimetableBot.jda.addEventListener(new NewMemberListener());
        TimetableBot.jda.addEventListener(new RemoveMemberListener());
    }
    public static void addListener(ListenerAdapter listener){
        TimetableBot.jda.addEventListener(listener);
    }

    public static void loadMembers(){
        TimetableBot.members = TimetableBot.guild.getMembers();
    }

    public static Guild getGuild() {
        return guild;
    }

    public static List<Member> getMembers() {
        return members;
    }
}

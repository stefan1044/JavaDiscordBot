package timetableBot;

import codeGenerators.Code;
import database.Database;
import listeners.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import preferences.Preference;
import preferences.PreferenceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimetableBot {

    public static Role ROLE_FIRST_YEAR;
    public static Role ROLE_SECOND_YEAR;
    public static Role ROLE_THIRD_YEAR;
    private static JDA jda;
    private static Guild guild;
    private static List<Member> members;
    
    public static Map<Code, List<Preference>> getPreferenceMap() {
        return preferenceMap;
    }
    
    private static Map<Code, List<Preference>> preferenceMap = new HashMap<>(0);

    public static void build() {
        var env = System.getenv();
        TimetableBot.jda = JDABuilder
                .createDefault(env.get("TOKEN"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
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
            TimetableBot.addRoles();
//            TimetableBot.preferenceMap = Database.getPreferences();
            System.out.println(TimetableBot.preferenceMap);
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
        TimetableBot.jda.addEventListener(new CodeSignupListener());
        TimetableBot.jda.addEventListener(new PreferenceListener());
        TimetableBot.jda.addEventListener(new CalculateTimeTableListener());
        TimetableBot.jda.addEventListener(new GuildJoinListener());
    }
    private static void addRoles(){
        try {
            TimetableBot.ROLE_FIRST_YEAR = TimetableBot.getGuild().getRolesByName("An 1", false).get(0);
            TimetableBot.ROLE_SECOND_YEAR = TimetableBot.getGuild().getRolesByName("An 2", false).get(0);
            TimetableBot.ROLE_THIRD_YEAR = TimetableBot.getGuild().getRolesByName("An 3", false).get(0);
        }catch (IndexOutOfBoundsException e){
            throw new RuntimeException("Could not get roles!");
        }
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

package listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import timetableBot.TimetableBot;

public class NewMemberListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        TimetableBot.loadMembers();
        System.out.printf("New member joined! Current member list:\n%s\n", TimetableBot.getMembers()); //TODO: remove in production!, only for testing
    }
}

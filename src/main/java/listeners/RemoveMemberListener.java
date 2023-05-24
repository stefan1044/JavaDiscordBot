package listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import timetableBot.TimetableBot;

public class RemoveMemberListener extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        TimetableBot.loadMembers();
        System.out.printf("Member left the guild! Current member list:\n%s\n", TimetableBot.getMembers()); //TODO: remove in production!, only for testing
    }
}

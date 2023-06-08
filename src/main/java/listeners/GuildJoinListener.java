package listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GuildJoinListener extends ListenerAdapter {
    
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commands = new ArrayList<>();
        commands.add(
                Commands.slash("prefer", "Select when would you prefer a class to take place")
                .addOptions(new OptionData(OptionType.STRING, "class", "The class for which you want to " +
                                "set your " + "preference", true),
                new OptionData(OptionType.STRING, "day",
                        "The day of the week in which you would like to take the class", true)
                        .addChoice("MONDAY", "monday")
                        .addChoice("TUESDAY", "tuesday")
                        .addChoice("WEDNESDAY", "wednesday")
                        .addChoice("THURSDAY", "thursday")
                        .addChoice("FRIDAY", "friday")
                ,
                new OptionData(OptionType.INTEGER, "starthour",
                        "The hour at which you would like to start", true)
                        .addChoice("8AM", 8)
                        .addChoice("10AM", 10)
                        .addChoice("12AM", 12)
                        .addChoice("2PM", 14)
                        .addChoice("4PM", 16)
                        .addChoice("6PM", 18)
                )
        );
        commands.add(
                Commands.slash("calculatetimetable", "Calculate the time table based on new preferences")
        );
        event.getGuild().updateCommands().addCommands(commands).queue();
    }
}

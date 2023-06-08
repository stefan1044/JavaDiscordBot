package listeners;

import codeGenerators.Code;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import preferences.Preference;
import timetableBot.TimetableBot;

import java.util.List;
import java.util.Map;

public class CalculateTimeTableListener extends ListenerAdapter {
    
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("calculatetimetable")){
            return;
        }
        Map<Code, List<Preference>> preferenceMap = TimetableBot.getPreferenceMap();
        System.out.println(preferenceMap);
        event.reply("da").queue();
    }
}

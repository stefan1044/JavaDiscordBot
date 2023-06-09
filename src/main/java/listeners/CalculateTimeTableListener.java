package listeners;

import functionalities.TimeTable_SA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CalculateTimeTableListener extends ListenerAdapter {
    
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("calculatetimetable")){
            return;
        }
//        Map<Code, List<Preference>> preferenceMap = TimetableBot.getPreferenceMap();
//        System.out.println(preferenceMap);
//        event.reply("da").queue();
        event.deferReply().queue();
        String timetable = TimeTable_SA.run();
        event.getHook().sendMessage(timetable).setEphemeral(true).queue();
    }
}

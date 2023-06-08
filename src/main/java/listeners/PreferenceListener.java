package listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import preferences.Preference;
import timetableBot.TimetableBot;

import java.util.ArrayList;
import java.util.Objects;


public class PreferenceListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if (!event.getName().equals("prefer")){
            return;
        }

        String preferredClass = Objects.requireNonNull(event.getOption("class")).getAsString();
        String preferredDay = Objects.requireNonNull(event.getOption("day")).getAsString();
        int preferredStartHour = Objects.requireNonNull(event.getOption("starthour")).getAsInt();
        
        System.out.println("Received: " + preferredClass + " " + preferredDay + " " + preferredStartHour);
        
        Preference preference = new Preference(preferredClass, preferredDay, preferredStartHour);
        String validity = Preference.checkValidity(preference);
        if (validity.equals(Preference.IS_VALID)){
            Member member = event.getMember();
            TimetableBot.getPreferenceMap().computeIfAbsent(member, k -> new ArrayList<>(0)).add(preference);
            TimetableBot.writePreferenceMapToFile();
        }
        
        event.reply(validity).setEphemeral(true).queue();
    }
}

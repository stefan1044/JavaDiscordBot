package listeners;

import codeGenerators.Code;
import codeGenerators.StaticCodesManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import preferences.Preference;
import preferences.PreferenceManager;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
            String nickname;
            try{
                nickname = Objects.requireNonNull(event.getMember()).getNickname();
            }catch (NullPointerException e){
                e.printStackTrace();
                return;
            }
            String[] names = nickname.split(" ");
            Code studentCode;
            try {
                System.out.println(new ArrayList<>(List.of(names)));
                studentCode = StaticCodesManager.getCodes().stream().filter(
                        (code) -> code.getFirstname().equals(names[0]) && code.getLastname().equals(
                                names[1])).findFirst().get();
            }catch (NoSuchElementException e){
                e.printStackTrace();
                return;
            }
            
            PreferenceManager.writePreferenceToDatabase(preference, studentCode);
        }
        
        event.reply(validity).setEphemeral(true).queue();
    }
}

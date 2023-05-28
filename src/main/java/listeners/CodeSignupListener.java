package listeners;

import codeGenerators.Code;
import codeGenerators.StaticCodesManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import timetableBot.TimetableBot;

import java.util.Optional;

public class CodeSignupListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        System.out.println("Received message on CodeSignupListener!\nMessage received was ");
        System.out.println(event.getMessage().getContentDisplay());
        if (event.isFromGuild()) {
            System.out.println("Message was not private!");
            return;
        }

        String userId = event.getAuthor().getId();
        Optional<Member> member = TimetableBot.getGuild().getMembers().stream()
                .filter(existingMember -> existingMember.getUser().getId().equals(userId))
                .findFirst();
        if(member.isEmpty()){
            System.out.println("User is not part of guild!");
            return;
        }

        String message = event.getMessage().getContentDisplay();
        Optional<Code> code = StaticCodesManager.getCodes().stream()
                .filter(existingCode -> existingCode.getCode().equals(message))
                .findFirst();
        if(code.isEmpty()){
            System.out.println("Received wrong code");
            return;
        }

        TimetableBot.getGuild().removeRoleFromMember(event.getAuthor(), TimetableBot.ROLE_FIRST_YEAR).queue();
        TimetableBot.getGuild().removeRoleFromMember(event.getAuthor(), TimetableBot.ROLE_SECOND_YEAR).queue();
        TimetableBot.getGuild().removeRoleFromMember(event.getAuthor(), TimetableBot.ROLE_THIRD_YEAR).queue();
        if (code.get().getYear() == 1){
            TimetableBot.getGuild().addRoleToMember(event.getAuthor(), TimetableBot.ROLE_FIRST_YEAR).queue();
        } else if(code.get().getYear() == 2){
            TimetableBot.getGuild().addRoleToMember(event.getAuthor(), TimetableBot.ROLE_SECOND_YEAR).queue();
        } else{
            TimetableBot.getGuild().addRoleToMember(event.getAuthor(), TimetableBot.ROLE_THIRD_YEAR).queue();
        }

        member.get().modifyNickname(code.get().getFirstname() + " " + code.get().getLastname()).queue();
    }
}

package listeners;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TestListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event){
        System.out.printf("Bot is ready! Available guilds: %d", event.getGuildAvailableCount());
    }
}

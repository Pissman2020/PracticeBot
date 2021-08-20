package lib;

import java.time.LocalTime;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String prefix = "!";
		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(prefix + "test")) {
			if (args[1].matches("\\b\\d{1,2}:\\d{2}\\b")) {
				event.getChannel().sendMessage("Command !time requires a time with the format HH:MM and a username.").queue();
			} else if (args[2].equalsIgnoreCase(null)) {
				event.getChannel().sendMessage("Command !time requires a time with the format HH:MM and a username.").queue();
			} else {
				
				String timeAsString = args[1];
				String userConvertFrom = args[2];
				
				LocalTime time = LocalTime.parse(timeAsString);
				
				
				
				String response = "time";
				event.getChannel().sendMessage(response).queue();
			}
		}
	}
}
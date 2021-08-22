package lib;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String prefix = "!";
		LocalTime time = null;
		String timeAsEpoch;
		String errorMessage = "Command !time requires a time in 24 hour format and timezone. e.g. `!time 17:00 EST`";
		
		String[] args = event.getMessage().getContentRaw().split("\\s");
		
			
		if (args[0].equalsIgnoreCase(prefix + "time")) {
			if (args[1].matches("\\b\\d{1,2}:\\d{2}\\b")) {
				if (args[1].length() == 5) {		
					String timeAsString = args[1];
					time = LocalTime.parse(timeAsString);
				} else if (args[1].length() == 4) {
					String timeAsString = "0" + args[1];
					time = LocalTime.parse(timeAsString);
				} else if (args[1].length() > 4) {
					event.getChannel().sendMessage(errorMessage).queue();
				} else if (args[1].length() < 5) {
					event.getChannel().sendMessage(errorMessage).queue();
				}
					
		 		LocalDateTime ldt = time.atDate(LocalDate.parse("2021-01-01"));
		 		Instant timeInstant = ldt.atZone(ZoneId.systemDefault()).toInstant();
		 		long timeAsLong = timeInstant.getEpochSecond();
		 		timeAsEpoch = String.valueOf(timeAsLong);
					
				event.getChannel().sendMessage("<t:" + timeAsEpoch + ":t>").queue();
				
			} else {
				event.getChannel().sendMessage(errorMessage).queue();				
			}
		}
	}	
}
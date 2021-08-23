package lib;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String prefix = "!";
		String[] args = event.getMessage().getContentRaw().split("\\s");
		
		//!time command
		HashMap<String, String> aliasMap = new HashMap<>();
        aliasMap.put("EST", "America/New_York");
        aliasMap.put("CST", "America/Chicago");
		
		if (args[0].equalsIgnoreCase(prefix + "time")) {
			
			LocalTime time = null;
			String timeAsEpoch;
			String errorMessage = "Command !time requires a time in 24 hour format and timezone. e.g. `!time 17:00 EST`";
			
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
				
				String zone = args[2].toUpperCase();
				ZoneId zoneId = ZoneId.of(zone, aliasMap);
				System.out.println(zoneId);
				
//				try {
//					zoneId = ZoneId.of(zone);
//					LocalDateTime ldt = time.atDate(LocalDate.parse("2021-01-01"));
//					Instant timeInstant = ldt.atZone(zoneId).toInstant();
//					long timeAsLong = timeInstant.getEpochSecond();
//					timeAsEpoch = String.valueOf(timeAsLong);
//					
//					event.getChannel().sendMessage("<t:" + timeAsEpoch + ":t>").queue();
//
//				} catch (ZoneRulesException e) {
//				event.getChannel().sendMessage(zone + " is not a supported timezone.").queue();
//				}
				
				if (zoneId instanceof ZoneId) {
					LocalDateTime ldt = time.atDate(LocalDate.parse("2021-01-01"));
					Instant timeInstant = ldt.atZone(zoneId).toInstant();
					long timeAsLong = timeInstant.getEpochSecond();
					timeAsEpoch = String.valueOf(timeAsLong);
					
					event.getChannel().sendMessage("<t:" + timeAsEpoch + ":t>").queue();
				} else {
					event.getChannel().sendMessage(errorMessage).queue();
				}
				
			} else {
				event.getChannel().sendMessage(errorMessage).queue();				
			}
			
				
			
		}
	}	
}
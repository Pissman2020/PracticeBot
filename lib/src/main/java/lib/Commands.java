package lib;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.HashMap;
import java.util.List;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Commands extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String prefix = "!";
		String[] args = event.getMessage().getContentRaw().split("\\s");
		Guild guild = event.getGuild();
		
		//!time command
		if (args[0].equalsIgnoreCase(prefix + "time")) {
			
			LocalTime time = null;
			String timeAsEpoch = null;
			String errorMessage = "Command !time requires a time in 24 hour format and timezone. e.g. `!time 17:00 EST`";
			HashMap<String, String> aliasMap = new HashMap<>();
			aliasMap.put("ECT", "Europe/Monaco");
			aliasMap.put("ART", "Egypt");
			aliasMap.put("EAT", "Africa/Asmara");
			aliasMap.put("MET", "Iran");
			aliasMap.put("NET", "Asia/Baku");
			aliasMap.put("PLT", "Asia/Ashgabat");
			aliasMap.put("IST", "Asia/Calcutta");
			aliasMap.put("BST", "Asia/Bishkek");
			aliasMap.put("VST", "Asia/Saigon");
			aliasMap.put("CTT", "Asia/Hong_Kong");
			aliasMap.put("JST", "Asia/Tokyo");
			aliasMap.put("ACT", "Australia/South");
			aliasMap.put("AET", "Australia/Melbourne");
			aliasMap.put("SST", "Pacific/Norfolk");
			aliasMap.put("NST", "Pacific/Auckland");
			aliasMap.put("MIT", "Pacific/Samoa");
			aliasMap.put("AST", "America/Sitka");
			aliasMap.put("PST", "America/Vancouver");
			aliasMap.put("PNT", "America/Phoenix");
			aliasMap.put("MST", "America/Denver");
	        aliasMap.put("EST", "America/New_York");
	        aliasMap.put("CST", "America/Winnipeg");
	        aliasMap.put("PRT", "America/Puerto_Rico");
	        aliasMap.put("CNT", "Canada/Newfoundland");
	        aliasMap.put("AGT", "America/Argentina/Cordoba");
	        aliasMap.put("BET", "Brazil/DeNoronha");
	        aliasMap.put("CAT", "Atlantic/Cape_Verde");
	        aliasMap.put("IET", "America/New_York");
			
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
				
				String zone = args[2];
				ZoneId zoneId = null;
				
				try {
					zoneId = ZoneId.of(zone);
					LocalDateTime ldt = time.atDate(LocalDate.parse("2021-01-01"));
					Instant timeInstant = ldt.atZone(zoneId).toInstant();
					long timeAsLong = timeInstant.getEpochSecond();
					timeAsEpoch = String.valueOf(timeAsLong);
					event.getChannel().sendMessage("<t:" + timeAsEpoch + ":t>").queue();
				} catch (ZoneRulesException e) {
					try {
						zone.toUpperCase();
						zoneId = ZoneId.of(zone, aliasMap);
						LocalDateTime ldt = time.atDate(LocalDate.parse("2021-01-01"));
						Instant timeInstant = ldt.atZone(zoneId).toInstant();
						long timeAsLong = timeInstant.getEpochSecond();
						timeAsEpoch = String.valueOf(timeAsLong);
						event.getChannel().sendMessage("<t:" + timeAsEpoch + ":t>").queue();
					} catch (ZoneRulesException f) {
						event.getChannel().sendMessage(zone + " is not a supported timezone. List of supported timezones: https://ibm.co/3sEd5BW").queue();
					}
				}
				
			} else {
				event.getChannel().sendMessage(errorMessage).queue();				
			}
			
		}
		
		
		//members command
		if (args[0].equalsIgnoreCase(prefix + "members")) {
			
			List<Role> roles = guild.getRolesByName(args[1], true);
			Role role = null;
			
			try {
				role = roles.get(0);
				System.out.println(role);
			} catch (IndexOutOfBoundsException e) {
			}
			
			List<Member> members = guild.getMembersWithRoles(role);
			event.getChannel().sendMessage(members.get(0).toString()).queue();
			
		}
		
	}	
}
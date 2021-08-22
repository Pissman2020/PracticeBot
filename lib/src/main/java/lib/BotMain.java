package lib;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.ZoneId;
import java.time.zone.ZoneRulesProvider;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import javax.security.auth.login.LoginException;

public class BotMain extends ListenerAdapter {

	
	public static void main(String[] args)
	{
		Properties p = Config.loadproperties();
		
		String token = p.getProperty("token");
		
 		try
		{
			JDA jda = JDABuilder.createDefault(token)
					.addEventListeners(new BotMain())
					.build();
			jda.awaitReady();
			jda.addEventListener(new Commands());
			System.out.println("Finished Building JDA!");
		}
		catch (LoginException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
 		//Section of just fucking around to find how to get a unix timecode from a time
// 		LocalTime lt = LocalTime.parse("18:00");
// 		LocalDateTime ldt = lt.atDate(LocalDate.parse("2021-01-01"));
// 		Instant timeInstant = ldt.atZone(ZoneId.systemDefault()).toInstant();
// 		long timeAsLong = timeInstant.getEpochSecond();
// 		String timeAsEpoch = String.valueOf(timeAsLong);
// 		System.out.println(timeAsEpoch);
 		Set<String> zones = ZoneRulesProvider.getAvailableZoneIds();
 		System.out.println(zones.toString());
 	
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		//general information gathering
		//JDA jda = event.getJDA();
		//long responseNumber = event.getResponseNumber();
		
		//event specific information
		User author = event.getAuthor();
		Message message = event.getMessage();
		//MessageChannel channel = event.getChannel();
		
		//creates a string of the content of the event
		String msg = message.getContentRaw();
		
		//checks if the author of the event is a bot
		//boolean bot = author.isBot();
		
		 if (event.isFromType(ChannelType.TEXT))         //If this message was sent to a Guild TextChannel
	        {
	            //Because we now know that this message was sent in a Guild, we can do guild specific things
	            // Note, if you don't check the ChannelType before using these methods, they might return null due
	            // the message possibly not being from a Guild!

	            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
	            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
	            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!

	            String name;
	            if (message.isWebhookMessage())
	            {
	                name = author.getName();                //If this is a Webhook message, then there is no Member associated
	            }                                           // with the User, thus we default to the author for name.
	            else
	            {
	                name = member.getEffectiveName();       //This will either use the Member's nickname if they have one,
	            }                                           // otherwise it will default to their username. (User#getName())

	            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
	        }
	        else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
	        {
	            //The message was sent in a PrivateChannel.
	            //In this example we don't directly use the privateChannel, however, be sure, there are uses for it!
	            //PrivateChannel privateChannel = event.getPrivateChannel();

	            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
	        }    
	}
	
}
	
	
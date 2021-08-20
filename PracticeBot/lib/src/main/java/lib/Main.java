package lib;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {

	public static void main(String[] args) throws LoginException{
		

		JDA api = JDABuilder.createDefault("ODc2OTU0NDY4NTg1OTk2Mjg5.YRrlfg.DJTrCJKpIQ3H1DM82MF5R5BoyC8").build();
		api.addEventListener(new MyListener());

		
	}
	
}

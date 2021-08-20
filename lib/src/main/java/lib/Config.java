package lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	public static Properties loadproperties(){
		try (InputStream input = Config.class.getClassLoader().getResourceAsStream("Config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            //System.out.println(prop.getProperty("db.url"));
            //System.out.println(prop.getProperty("db.user"));
            //System.out.println(prop.getProperty("db.password"));
            
            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
            
            return null;
        }
	}
	
}

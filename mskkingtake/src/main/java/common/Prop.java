package common;

import java.io.IOException;
import java.util.Properties;


public class Prop {
	
	static Properties properties = new Properties();
	static {
		try {
			properties.load(Prop.class.getClassLoader().getResourceAsStream("common/prop.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperties(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * TEST
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		System.out.println(Prop.class.getResource(""));
		System.out.println(getProperties("mskkingtake"));
	}
}

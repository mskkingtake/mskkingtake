package common;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class RandomUtil {

	public static void main(String[] args) {       
		for(int i = 0; i < 20; i++) {
			System.out.println(getRandomInt(500));
			System.out.println(getRandomInt());
		}

	}
	
	/**
	 * 生成一个随机数
	 * @return
	 */
	private static int getRandomInt(int maxInt) {
		return (int)(Math.random() * maxInt);
	}
	
	/**
	 * 随机取出X个不同的随机数
	 * @param args
	 */
	public static Map<Integer, Integer> getRandomInt() {
		int tryCount = 0;
		int irandomMapSize = 1;
		Map<Integer, Integer> randomMap = new HashMap<Integer, Integer>();
		
		Random random = new Random();
		while(true) {
			if(tryCount++ > 100) {
				break;
			}
			
			int ran = random.nextInt(5);
			if(!randomMap.containsValue(ran)) {
				randomMap.put(irandomMapSize++, ran);
			}
			
			if(randomMap.size() >= 3) {
				break;
			}
		}
		
		return randomMap;
	}
}

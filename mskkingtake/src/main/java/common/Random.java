package common;

public class Random {

	public static void main(String[] args) {
		for(int i = 0; i < 20; i++) {
			System.out.println(getRandomInt(500));
		}

	}
	
	/**
	 * 生成一个随机数
	 * @return
	 */
	private static int getRandomInt(int maxInt) {
		return (int)(Math.random() * maxInt);
	}

}

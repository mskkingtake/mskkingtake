package test;

public class Test {
	static int monthcount = 12*5;
	static double rate = 0.6929;

	public static void main(String[] args) {
//		getRealMoney();
		getRealMoney1();
	}
	
	private static void getRealMoney() {
		double sumMoney = 0;
		
		for(int i = 0; i < monthcount; i++) {
			sumMoney = sumMoney + 1000;
			sumMoney = sumMoney + sumMoney / 10000 * rate;
			
			System.out.println("第" + i + "个月一共是：" + sumMoney);
		}
	}
	
	private static void getRealMoney1() {
		double sumMoney = 0;
		double sumRate = 0;
		
		for(int i = 0; i < 12 * 25; i++) {
			sumMoney = sumMoney + 1000;
			sumMoney = sumMoney + sumMoney / 10000 * rate * 30;
			sumRate = sumRate + sumMoney / 10000 * rate * 30;
			
			System.out.println("第" + i + "月一共是：" + sumMoney + "///" + sumRate);
		}
		
		for(int i = 0; i < 12 * 20; i++) {
			sumMoney = sumMoney - 1692.94;
			sumMoney = sumMoney + sumMoney / 10000 * rate * 30;
			sumRate = sumRate + sumMoney / 10000 * rate * 30;
			
			System.out.println("第" + i + "月一共是：" + sumMoney + "///" + sumRate);
		}
	}

}

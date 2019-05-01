package soctet.soctetClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SoctetClient {

	private static Socket client;

	public static void main(String[] args) {
		try {
			client = new Socket("127.0.0.1", 7788);
			client.setSoTimeout(10000);
			
			//获取键盘输入 
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			//获取Socket的输出流，用来发送数据到服务端  
			PrintStream out = new PrintStream(client.getOutputStream());
			
			//获取Socket的输入流，用来接收从服务端发送过来的数据  
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			while(true) {
				System.out.print("输入信息：");
				String str = input.readLine();
				//发送数据到服务端  
				out.println(str);
				try{
					//从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
					String echo = buf.readLine();
					System.out.println(echo);
				} catch(SocketTimeoutException e){
					System.out.println("Time out, No response");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

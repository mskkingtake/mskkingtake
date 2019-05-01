package soctet.socketServer;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerMain {

	public static void main(String[] args) {
		try {
			//服务端在20006端口监听客户端请求的TCP连接
			ServerSocket server = new ServerSocket(7788);
			Socket client = null;
			boolean f = true;
			while(f) {
				//等待客户端的连接，如果没有获取连接
				client = server.accept();
				System.out.println("与客户端连接成功！");
				
				//为每个客户端连接开启一个线程
				new Thread(new SocketServer(client)).start();
			}
			server.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package testSocketServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private ServerSocket serverSocket;
	public List<Dealer> dealers = new ArrayList<Dealer>();
	
	public void start() throws Exception{
		serverSocket = new ServerSocket(427);
		while(true){
			Socket socket = serverSocket.accept();
			
			synchronized (this) {
				//创建一个“处理者”来处理新的连接
				Dealer d = new Dealer(this, socket);
				this.dealers.add(d);
				new Thread(d).start();
				System.out.println("客户端打开了一个连接，当前连接数：" + dealers.size());
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		Server server = new Server();
		server.start();
	}
}

//对客户端连接的处理者
class Dealer implements Runnable{
	Server server;
	BufferedReader br;
	PrintWriter os;

	public Dealer(Server server, Socket socket) {
		try{
			this.server = server;
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.os = new PrintWriter(socket.getOutputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void run(){
		String line;
		try{
			while((line = br.readLine()) != null){
				//将收到的信息广播给全部客户端
				synchronized (server) {
					List<Dealer> ds = server.dealers;
					for(Dealer d : ds){
						d.os.println(line);
						d.os.flush();
					}
				}
			}
		}catch(Exception e){
			synchronized (server) {
				server.dealers.remove(this);
				System.out.println("客户端关闭了一个连接，当前连接数：" + server.dealers.size());
			}
		}
	}
	
}
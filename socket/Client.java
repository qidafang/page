package testSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception{
		//连接服务器
		Socket socket = new Socket("182.92.10.238", 427);
		//Socket socket = new Socket("127.0.0.1", 427);
		PrintWriter os = new PrintWriter(socket.getOutputStream());
		BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//启动从服务器读取数据的线程
		new Thread(new Reader(is)).start();
		new Thread(new Writer(os)).start();
	}
}

//从服务器读消息的线程
class Reader implements Runnable{
	BufferedReader br;
	
	public Reader(BufferedReader br) {
		super();
		this.br = br;
	}

	public void run() {
		try{
			String line;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
		}catch(Exception e){
			System.exit(0);
		}
	}
}

//从命令行获得输入的线程
class Writer implements Runnable{

	PrintWriter os;
	
	public Writer(PrintWriter os) {
		this.os = os;
	}

	@Override
	public void run() {
			try{
			//有操作时向服务器写数据
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			String line;
			while((line = sin.readLine()) != null){
				os.println(line);
				os.flush();
			}
		}catch(Exception e){
			//...
		}
	}
	
}
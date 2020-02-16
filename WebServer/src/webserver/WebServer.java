package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	// attribute 
	private int portNumber = 80;

	// socket 
	private ServerSocket serverSocket;
	
	
	public WebServer() {
		try {
			this.serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initialize() {
		
	}
	
	public void run() {
		try {
			boolean finish = true;
			int i = 0;
			while(finish) {
				Socket socket = serverSocket.accept();
				String threadName = "Thread" + i;
				Session session = new Session(threadName, socket);
				session.initialize();
				session.start();
				if(i++ >= 10) break;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void finalize() {
		
	}
}

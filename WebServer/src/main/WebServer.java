package main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	// attribute 
	private int portNumber = 80;
	
	// socket 
	private ServerSocket serverSocket;
	private Socket socket;
	
	// stream 
	private OutputStreamWriter writer;
	
	public WebServer() {
		try {
			this.serverSocket = new ServerSocket(portNumber);
			this.socket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initialize() {
		System.out.println(socket.getInetAddress());
		try {
			OutputStream outputStream = socket.getOutputStream();
			this.writer = new OutputStreamWriter(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		 
		String message = "HTTP/1.1 200 OK \r\n";
		message += "Content-Type: text/html;charset=utf-8 \r\n";
		message += "Content-Length: " + 100 +  "\r\n";
		message += "\r\n";
		message += "<!DOCTYPE html>\n";
		message += "<html>\n"
				+ "<head>\n"
				+ "<title>Webserver Test</title>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<h1> web server implementation </h1>\n"
				+ ""
				+ "</body>\n"
				+ "</html>\r\n";
		System.out.println(message);
		try {
			writer.append(message);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void finalize() {
		try {
			writer.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

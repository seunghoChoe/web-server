package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.stream.Stream;

public class Session extends Thread{
	// stream 
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;

	private Socket socket;
	
	String LINE_FEED = "\r\n";
	
	public Session(String name, Socket socket) {
		super(name);
		this.socket = socket;
	}
	
	public void initialize() {
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.processRequest();
		this.processResponse();
		this.finalize();
	}
	
	private void processRequest() {
		System.out.println(this.getName() + "processRequest()");
		String request = new String();
		Stream<String> stream = this.bufferedReader.lines();
		Iterator<String> iterator = stream.iterator();
		while(iterator.hasNext()) {
			String line = iterator.next();
			request += line + LINE_FEED;
			if(line.isEmpty()) break;
		}
	}

	private void processResponse() {
		System.out.println(this.getName() + "processResponse()");
		String message = new String();
		String header = new String();
		String body = new String();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("./html/index.html"));
			Stream<String> stream = bufferedReader.lines();
			Iterator<String> iterator = stream.iterator();
			while(iterator.hasNext()) {
				body += iterator.next() + LINE_FEED;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		header += "HTTP/1.1 200 OK \r\n";
		header += "Content-Type: text/html;charset=utf-8" + LINE_FEED;
		header += "Content-Length: " + body.length() + LINE_FEED;
		
		message += header + LINE_FEED + body;
		
		try {
			bufferedWriter.append(message);
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void finalize() {
		System.out.println("finalize()");
		try {
			this.bufferedReader.close();
			this.bufferedWriter.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

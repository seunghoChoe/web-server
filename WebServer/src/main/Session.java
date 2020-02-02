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
	String SPACE = " ";
	String HTML_DIRECTORY_PATH = "./html";
	String HTML_EXTENDER = ".html";
	String DEFAULT_HTML_FILE = "/index";
	
	
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
		String requestURI = this.processRequest();
		this.processResponse(requestURI);
		this.finalize();
	}
	
	private String processRequest() {
		System.out.println(this.getName() + ": processRequest()");
		String request = new String();
		Stream<String> stream = this.bufferedReader.lines();
		Iterator<String> iterator = stream.iterator();
		while(iterator.hasNext()) {
			String line = iterator.next();
			request += line + LINE_FEED;
			if(line.isEmpty()) break;
		}
		String[] tokens = request.split(LINE_FEED);
		String[] heads = tokens[0].split(SPACE);
		System.out.println("request path: " + heads[1]);
		return heads[1];
	}

	private void processResponse(String requestURI) {
		System.out.println(this.getName() + ": processResponse()");
		String message = new String();
		String header = new String();
		String body = new String();
		try {
			if(requestURI.equals("/")) requestURI = DEFAULT_HTML_FILE;
			String htmlFilePath = HTML_DIRECTORY_PATH + requestURI + HTML_EXTENDER;
			System.out.println("html file path : " + htmlFilePath);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(htmlFilePath));
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

package webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;

import common.Constants;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class Session extends Thread{
	// stream 
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;

	private Socket socket;
	
	
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
		try {
			HttpRequest httpRequest = new HttpRequest(this.bufferedReader.lines());
			String uriPath = httpRequest.getURIPath();
			if(uriPath.contains("favicon.ico")) {
				this.processFavicon(uriPath);
				return;
			}
			HttpResponse response = new HttpResponse(uriPath);
			String message = response.getMessage();
//			System.out.println(message);
			bufferedWriter.append(message);
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.finalize();
	}
	

	private void processFavicon(String uriPath) {
		try {
			String faviconPath = Constants.HTML_DIRECTORY_PATH + uriPath;
			System.out.println("favicon path : " + faviconPath);
			File favicon = new File(faviconPath);
			byte[] byteArray = Files.readAllBytes(favicon.toPath());
			
			OutputStream out = socket.getOutputStream();
			out.write(byteArray);
			out.flush();
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

package webserver.request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

import common.Constants;

public class HttpRequest {
	private HashMap<String, String> headers;
	private String method;
	private String URIPath;
	private String protocol;
	
	public HttpRequest(Stream<String> stream) {
		// init attribute
		this.headers = new HashMap<String, String>();
		Iterator<String> iterator = stream.iterator();
		
		this.parseRequestLine(iterator.next());
		// parsing header
		while(iterator.hasNext()) {
			String line = iterator.next();
			// In case of empty line
			if(line.isEmpty()) break;
			String[] header = line.split(":");
			headers.put(header[0], header[1].trim());
		}
		
		// parsing body
//		while(iterator.hasNext()) {
//			
//		}
	}

	private void parseRequestLine(String requestLine) {
		System.out.println("requestLine : " + requestLine);
		String[] toekns = requestLine.split(Constants.SPACE);
		this.method = toekns[0];
		this.URIPath = toekns[1];
		this.protocol = toekns[2];
	}

	public String getMethod() {
		return this.method;
	}
	
	public String getProtocol() {
		return this.protocol;
	}
	
	public String getURIPath() {
		return this.URIPath;
	}
	
	public HashMap<String, String> getHeaders() {
		return this.headers;
	}
}

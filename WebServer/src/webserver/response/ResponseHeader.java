package webserver.response;

import java.util.HashMap;

import common.Constants;

public class ResponseHeader {
	private String header;
	private HashMap<String, Object> headers;
	private String responseLine;

	public ResponseHeader() {
		this.responseLine = "HTTP/1.1 200 OK \r\n";
		this.headers = new HashMap<String, Object>();
		headers.put("Content-Type", "text/html;charset=utf-8");
		headers.put("Content-Length", 0);
	}
	
	public void setContentLength(int length) {
		this.headers.put("Content-Length", length);
	}

	public String getHeader() {
		System.out.println("responseLine : " + this.responseLine);
		this.header = new String();
		this.header += this.responseLine;
		for(String key : this.headers.keySet()) {
			this.header += key + ": " + headers.get(key) + Constants.LINE_FEED;
		}
		return this.header;
	}

}

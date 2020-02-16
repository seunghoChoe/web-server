package webserver.response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.stream.Stream;

import common.Constants;

public class HttpResponse {
	private String message;

	public HttpResponse(String requestURI) {
		this.message = new String();
		ResponseHeader responseHeader = new ResponseHeader();
		String body = this.readHtml(requestURI);
		responseHeader.setContentLength(body.length());
		String header = responseHeader.getHeader();
		this.message += header + Constants.LINE_FEED + body;
	}

	private String readHtml(String requestURI) {
		String body = new String();
		try {
			if(requestURI.equals("/")) requestURI = Constants.DEFAULT_HTML_FILE;
			String htmlFilePath = Constants.HTML_DIRECTORY_PATH + requestURI;
			System.out.println("html file path : " + htmlFilePath);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(htmlFilePath));
			Stream<String> stream = bufferedReader.lines();
			Iterator<String> iterator = stream.iterator();
			while(iterator.hasNext()) {
				body += iterator.next() + Constants.LINE_FEED;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body;
	}

	public String getMessage() {
		return this.message;
	}

}

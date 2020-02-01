package main;

public class Main {

	public static void main(String[] args) {
		WebServer webServer = new WebServer();
		webServer.initialize();
		webServer.run();
		webServer.finalize();
	}

}

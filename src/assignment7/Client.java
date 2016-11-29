package assignment7;

import java.net.Socket;

public class Client {
	Socket socket;
	int username;
	
	public Client(int username, Socket socket) {
		this.socket = socket;
		this.username = username;
		return;
	}
}

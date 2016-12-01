package assignment7;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{
	int type;
	ArrayList<Integer> recipients;
	String content;
	
	public Message(int type, String content) {
		this.type = type;
		this.recipients = new ArrayList<Integer>();
		Integer Client1 = 1;
		recipients.add(Client1);
		this.content = content;
		return;
		
	}
}

/* ChatRoom
 * EE422C Project 7 submission by
 * Alex Lostak
 * ajl3287
 * 16460
 * Jonah Harris
 * jlh6487
 * 16455
 * Slip days used: 1
 * Fall 2016
 */
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
//		Integer Client1 = 5;
//		recipients.add(Client1);
//		Client1 = 1;
//		recipients.add(Client1);
		this.content = content;
		return;
		
	}
}

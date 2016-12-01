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

import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatClient extends Application {
	private TextArea incoming;
	private TextField outgoing;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private ArrayList<Integer> chats;
	private ArrayList<ChatWindow> openWindows = new ArrayList<ChatWindow>();
	
	

	public void run() throws Exception {
		launch();
	}


//	static GridPane grid = new GridPane();
// 	public static int sceneScale = 50;
//	public class SecondStage extends Stage {
//    	Rectangle2D primaryScreenBounds2 = Screen.getPrimary().getVisualBounds();
//    	Label title = new Label("Active Chat:");
//    	GridPane chatSelect = new GridPane();
//
//    	SecondStage(){
//    		chats = new ArrayList<Integer>();
//    		this.setX(primaryScreenBounds2.getMaxX()/2);
//            this.setY(primaryScreenBounds2.getMinY());
//            this.setWidth(primaryScreenBounds2.getWidth()/2);
//            this.setHeight(primaryScreenBounds2.getHeight()/2);
//    	    chatSelect.add(title, 0, 0);
//    	    Label alex = new Label("Alex");
//    	    Label jonah = new Label("Jonah");
//    	    Label jasmine = new Label("Jasmine");
//    	    Label megan = new Label("Megan");
//    	    
//    	    
//    	    RadioButton select1 = new RadioButton();
//    	    Integer s1 = new Integer(1);
//    	    select1.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                	if (select1.isSelected()){
//                		chats.add(s1);
//                	}
//                	else {
//                		if (chats.contains(s1)) {
//                			chats.remove(s1);
//                		}
//                	}
//                }  
//            } ) ;
//    	    
//    	    
//    	    RadioButton select2 = new RadioButton();
//    	    Integer s2 = new Integer(2);
//    	    select2.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                	if (select2.isSelected()){
//                		chats.add(s2);
//                	}
//                	else {
//                		if (chats.contains(s2)) {
//                			chats.remove(s2);
//                		}
//                	}
//                }  
//            } ) ;
//    	    
//    	    
//    	    RadioButton select3 = new RadioButton();
//    	    Integer s3 = new Integer(3);
//    	    select3.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                	if (select3.isSelected()){
//                		chats.add(s3);
//                	}
//                	else {
//                		if (chats.contains(s3)) {
//                			chats.remove(s3);
//                		}
//                	}
//                }  
//            } ) ;
//    	    
//    	    
//    	    RadioButton select4 = new RadioButton();
//    	    Integer s4 = new Integer(4);
//    	    select4.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                	if (select4.isSelected()){
//                		chats.add(s4);
//                	}
//                	else {
//                		if (chats.contains(s4)) {
//                			chats.remove(s4);
//                		}
//                	}
//                }  
//            } ) ;
//    	    
//    	    
//    	    
//    	    
//    	    int row = 0;
//    	    int column = 0;
//    	    chatSelect.add(alex,    column, row + 1);
//    	    chatSelect.add(jonah,   column, row + 2);
//    	    chatSelect.add(jasmine, column, row + 3);
//    	    chatSelect.add(megan,   column, row + 4);
//    	    column += 1;
//    	    chatSelect.add(select1, column, row + 1);
//    	    chatSelect.add(select2, column, row + 2);
//    	    chatSelect.add(select3, column, row + 3);
//    	    chatSelect.add(select4, column, row + 4);
//    	    this.setTitle("Chats");
//    	    Scene scene = new Scene(chatSelect, primaryScreenBounds2.getWidth()/2, primaryScreenBounds2.getHeight()/2);
//    	    scene.setFill(javafx.scene.paint.Color.ANTIQUEWHITE);
//    	    this.setScene(scene);
//    	    this.show();
//    	   }    
//    	}
	
	@Override 
	public void start(Stage selection){
		try {
			setUpNetworking();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Rectangle2D primaryScreenBounds2 = Screen.getPrimary().getVisualBounds();
		chats = new ArrayList<Integer>();
		GridPane chatSelect = new GridPane();
		selection.setX(primaryScreenBounds2.getMaxX()/2);
        selection.setY(primaryScreenBounds2.getMinY());
        selection.setWidth(primaryScreenBounds2.getWidth()/2);
        selection.setHeight(primaryScreenBounds2.getHeight()/2);
	    //selection.add(title, 0, 0);
	    Label alex = new Label("Client 1");
	    Label jonah = new Label("Client 2");
	    Label jasmine = new Label("Client 3");
	    Label megan = new Label("Client 4");
	    
	    Button createChat = new Button("Start Chat");
	    createChat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//check if window already open
            	boolean windowExists = false;
            	for (ChatWindow c : openWindows) {
            		if (c.equalRecipients(chats))
            			windowExists = true;
            	}
            	if (!windowExists) {
            		ArrayList<Integer> chatCopy = new ArrayList<Integer>();
            		for (Integer i : chats) {
            			chatCopy.add(i);
            		}
            		ChatWindow newWindow = new ChatWindow(chatCopy, writer, reader);
                	openWindows.add(newWindow);
            	}
            	return;
            }  
        } ) ;
	    
	    RadioButton select1 = new RadioButton();
	    Integer s1 = new Integer(1);
	    select1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (select1.isSelected()){
            		chats.add(s1);
            	}
            	else {
            		if (chats.contains(s1)) {
            			chats.remove(s1);
            		}
            	}
            }  
        } ) ;
	    
	    
	    RadioButton select2 = new RadioButton();
	    Integer s2 = new Integer(2);
	    select2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (select2.isSelected()){
            		chats.add(s2);
            	}
            	else {
            		if (chats.contains(s2)) {
            			chats.remove(s2);
            		}
            	}
            }  
        } ) ;
	    
	    
	    RadioButton select3 = new RadioButton();
	    Integer s3 = new Integer(3);
	    select3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (select3.isSelected()){
            		chats.add(s3);
            	}
            	else {
            		if (chats.contains(s3)) {
            			chats.remove(s3);
            		}
            	}
            }  
        } ) ;
	    
	    
	    RadioButton select4 = new RadioButton();
	    Integer s4 = new Integer(4);
	    select4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (select4.isSelected()){
            		chats.add(s4);
            	}
            	else {
            		if (chats.contains(s4)) {
            			chats.remove(s4);
            		}
            	}
            }  
        } ) ;
	    
	    
	    
	    
	    int row = 0;
	    int column = 2;
	    chatSelect.add(alex,    column, row + 1);
	    chatSelect.add(jonah,   column, row + 2);
	    chatSelect.add(jasmine, column, row + 3);
	    chatSelect.add(megan,   column, row + 4);
	    column += 1;
	    chatSelect.add(select1, column, row + 1);
	    chatSelect.add(select2, column, row + 2);
	    chatSelect.add(select3, column, row + 3);
	    chatSelect.add(select4, column, row + 4);
	    chatSelect.add(createChat, column - 2, row);
	    selection.setTitle("Chats");
	    Scene scene = new Scene(chatSelect, primaryScreenBounds2.getWidth()/2, primaryScreenBounds2.getHeight()/2);
	    scene.setFill(javafx.scene.paint.Color.ANTIQUEWHITE);
	    //scene.setUserAgentStylesheet("DarkTheme.css");
	    selection.setScene(scene);
	    selection.show();
	}
	

	
	
	
	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket sock = new Socket("127.0.0.1", 4242);
		ObjectOutputStream outToServer = new ObjectOutputStream(sock.getOutputStream());
		writer = outToServer;
		ObjectOutputStream w = writer;
		ObjectInputStream inFromServer = new ObjectInputStream(sock.getInputStream());
		reader = inFromServer;
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
	}
	 
	
	class handleEvent implements EventHandler {

		@Override
		public void handle(Event event) {
			// TODO Auto-generated method stub
			
		}
		
	}
 
	
	public static void main(String[] args) {
		try {
			new ChatClient().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class IncomingReader implements Runnable {
		public void run() {
			Message message;
			try {
				//while ((message = (Message) reader.readObject()) != null) {
				while (true) {
				message = (Message) reader.readObject();
				//see recipients in message
					//get take arraylist of recipients from incoming message
					//go through chat windows and wait til find the window where all of the recipients match
				ArrayList<Integer> recipients = message.recipients;
				ChatWindow windowToAppend = null;
				boolean windowFound = false;
				for (ChatWindow c : openWindows) {
					if (c.equalRecipients(recipients) == true) {
						windowToAppend = c;
						windowFound = true;
						break;
					}
				}
				if (windowFound == false) {
					//create new window
					windowToAppend = new ChatWindow(recipients, writer, reader);
					openWindows.add(windowToAppend);
					
				}
				//check if window open with those recipients
					//if open, update it
					//if not open new window and update it
				windowToAppend.incoming.appendText(message.content + "\n");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

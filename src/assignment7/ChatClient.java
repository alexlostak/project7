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
	
	

	public void run() throws Exception {
		
		
		launch();
	}

//	private void initView() {
//		JFrame frame = new JFrame("Ludicrously Simple Chat Client");
//		JPanel mainPanel = new JPanel();
//		incoming = new JTextArea(15, 50);
//		incoming.setLineWrap(true);
//		incoming.setWrapStyleWord(true);
//		incoming.setEditable(false);
//		JScrollPane qScroller = new JScrollPane(incoming);
//		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//		outgoing = new JTextField(20);
//		JButton sendButton = new JButton("Send");
//		sendButton.addActionListener(new SendButtonListener());
//		mainPanel.add(qScroller);
//		mainPanel.add(outgoing);
//		mainPanel.add(sendButton);
//		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
//		frame.setSize(650, 500);
//		frame.setVisible(true);
//
//	}

	static GridPane grid = new GridPane();
 	public static int sceneScale = 50;
	public class SecondStage extends Stage {
    	Rectangle2D primaryScreenBounds2 = Screen.getPrimary().getVisualBounds();
    	Label title = new Label("Active Chat:");
    	GridPane chatSelect = new GridPane();

    	SecondStage(){
    		chats = new ArrayList<Integer>();
    		this.setX(primaryScreenBounds2.getMaxX()/2);
            this.setY(primaryScreenBounds2.getMinY());
            this.setWidth(primaryScreenBounds2.getWidth()/2);
            this.setHeight(primaryScreenBounds2.getHeight()/2);
    	    chatSelect.add(title, 0, 0);
    	    Label alex = new Label("Alex");
    	    Label jonah = new Label("Jonah");
    	    Label jasmine = new Label("Jasmine");
    	    Label megan = new Label("Megan");
    	    
    	    
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
    	    int column = 0;
    	    chatSelect.add(alex,    column, row + 1);
    	    chatSelect.add(jonah,   column, row + 2);
    	    chatSelect.add(jasmine, column, row + 3);
    	    chatSelect.add(megan,   column, row + 4);
    	    column += 1;
    	    chatSelect.add(select1, column, row + 1);
    	    chatSelect.add(select2, column, row + 2);
    	    chatSelect.add(select3, column, row + 3);
    	    chatSelect.add(select4, column, row + 4);
    	    this.setTitle("Chats");
    	    Scene scene = new Scene(chatSelect, primaryScreenBounds2.getWidth()/2, primaryScreenBounds2.getHeight()/2);
    	    scene.setFill(javafx.scene.paint.Color.ANTIQUEWHITE);
    	    this.setScene(scene);
    	    this.show();
    	   }    
    	}
	
//	public void actionPerformed(){
//		   Object source = event.getSource();
//
//		   if(source == send){
//			   Message outgoingMsg = new Message(1, outgoing.getText());
//				try {
//					writer.writeObject(outgoingMsg);
//					writer.flush();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				outgoing.setText("");
//				outgoing.requestFocus();
//		   }
//	} 
	
	@Override
	public void start(Stage chatPane){
		//send.setActionHandler(ActionEvent.ACTION, actionPerformed());
		try {
			setUpNetworking();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new SecondStage();
		chatPane.setTitle("A&J Chat");
		incoming = new TextArea();
		incoming.setWrapText(true);
		incoming.setEditable(false);
		ScrollPane history = new ScrollPane(incoming);
		history.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		history.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		outgoing = new TextField();
		
		Button send = new Button("Send");

		send.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Message outgoingMsg = new Message(1, outgoing.getText());
            	try {
            		ObjectOutputStream w = writer;
            		writer.writeObject(outgoingMsg);
            		writer.flush();
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
            	outgoing.setText("");
        		outgoing.requestFocus();
            }  
        } ) ;
		
        
        
		
		GridPane center = new GridPane();
		
		TabPane convos = new TabPane();
		//BorderPane borderPane = new BorderPane();
        Tab tab = new Tab();
        tab.setText("A");
        VBox chat = new VBox();
        chat.getChildren().add(new Label("Messenging A"));  
        chat.getChildren().addAll(history, outgoing, send);
        chat.setAlignment(Pos.CENTER);
        tab.setContent(chat);
        convos.getTabs().add(tab);
		
		center.add(history, 0, 0);
		center.add(outgoing, 0, 1);
		center.add(send, 1, 1);
		//center.add(convos, 0, 2);
		//center.add(whosOnline, 0, 3);
		
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        chatPane.setX(primaryScreenBounds.getMinX());
        chatPane.setY(primaryScreenBounds.getMinY());
        chatPane.setWidth(primaryScreenBounds.getWidth()/2);
        chatPane.setHeight(primaryScreenBounds.getHeight()/2);
		Scene myScene = new Scene(center, primaryScreenBounds.getWidth()/2, primaryScreenBounds.getHeight()/2);
		chatPane.setScene(myScene);
        chatPane.show();
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
				//check if window open with those recipients
					//if open, update it
					//if not open new window and update it
						incoming.appendText(message.content + "\n");
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

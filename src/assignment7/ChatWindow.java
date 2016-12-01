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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChatWindow extends Stage {
	public TextArea incoming;
	private TextField outgoing;
	public ArrayList<Integer> recipients;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	
	public boolean equalRecipients(ArrayList<Integer> otherRecipients) {
		if (otherRecipients.size() != recipients.size())
			return false;
		for (Integer i : otherRecipients) {
			if (recipients.contains(i) == false)
				return false;
			
		}
		return true;
	}
	
	private String createWindowTitle() {
		String title = "Chatting with Clients: ";
		
		for (Integer i : recipients) {
			title += i.toString();
			title += " ";
		}
		return title;
	}
	
	public ChatWindow(ArrayList<Integer> recipients, ObjectOutputStream writer, ObjectInputStream reader) {
		this.writer = writer;
		this.reader = reader;
		this.recipients = recipients;
		String windowTitle = createWindowTitle();
		this.setTitle(windowTitle);
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
            	outgoingMsg.recipients = recipients;
            	try {
            		//ObjectOutputStream w = writer;
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
        this.setX(primaryScreenBounds.getMinX());
        this.setY(primaryScreenBounds.getMinY());
        this.setWidth(primaryScreenBounds.getWidth()/2);
        this.setHeight(primaryScreenBounds.getHeight()/2);
		Scene myScene = new Scene(center, primaryScreenBounds.getWidth()/2, primaryScreenBounds.getHeight()/2);
		//myScene.setUserAgentStylesheet("DarkTheme.css");
		this.setScene(myScene);
        this.show();

	}



}

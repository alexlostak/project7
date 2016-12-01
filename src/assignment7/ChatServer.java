package assignment7;

//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class ChatServer {
	public static void main(String[] args) {
		try {
			new ChatServer().setUpNetworking();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Integer clientCount = 1;
	private HashMap<Socket, Integer> serverClientIds = new HashMap<Socket, Integer>();
	private HashMap<Integer, Socket> serverClientSockets = new HashMap<Integer, Socket>();
	private HashMap<Socket, ClientObserver> socketObservers = new HashMap<Socket, ClientObserver>();
	private ArrayList<ChatGroup> chatGroups = new ArrayList<ChatGroup>();
	private ChatGroup allClients = new ChatGroup();
	
	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		ServerSocket serverSock = new ServerSocket(4242);
		chatGroups.add(allClients);
		//launch four clients
		
		
		
		while (true) {
			Socket clientSocket = serverSock.accept();
			serverClientIds.put(clientSocket, clientCount);
			serverClientSockets.put(clientCount, clientSocket);
			allClients.addClient(clientSocket);
			System.out.print("Now added Client Number: " + clientCount);
			clientCount++;
			//ClientObserver writer = new ClientObserver(clientSocket.getOutputStream());
			//this.addObserver(writer);
			Thread t = new Thread(new ClientHandler(clientSocket));
			t.start();
			System.out.println("got a connection");
		}
	}
	
	private ChatGroup findChatGroup(ChatGroup g) {
		if (g.clientSockets.size() == 0)
			return allClients;
		for (ChatGroup c : chatGroups) {
			if (c.equals(g))
				return c;
		}
		ChatGroup newGroup = new ChatGroup(g.clientSockets);
		return newGroup;
	}
	
	public class ChatGroup extends Observable {
		private ArrayList<Socket> clientSockets;
		
		public ChatGroup() {
			clientSockets = new ArrayList<Socket>();
			return;
		}
		
		public ChatGroup(ArrayList<Socket> clientSockets) {
			this.clientSockets = clientSockets;
			initClientObservers();
			return;
			
		}
		
		private void initClientObservers() {
			for(Socket s : clientSockets) {
				try {
					//ClientObserver writer = new ClientObserver(s.getOutputStream());
					ClientObserver writer = socketObservers.get(s);
					if (writer == null) {
						writer = new ClientObserver(s.getOutputStream());
						socketObservers.put(s, writer);
					}
					this.addObserver(writer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void addClient(Socket s) {
			if (clientSockets.contains(s) == false)
				clientSockets.add(s);
			try {
				ClientObserver writer = socketObservers.get(s);
				if (writer == null) {
					writer = new ClientObserver(s.getOutputStream());
					socketObservers.put(s, writer);
				}
				this.addObserver(writer);
			} catch (IOException e){
				e.printStackTrace();
			}
//			ClientObserver writer = socketObservers.get(s);
//			
//			try {
//				writer = new ClientObserver(s.getOutputStream());
//				socketObservers.put(s, writer);
//				this.addObserver(writer);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			return;
		}
		
		public boolean equals(ChatGroup other) {
			if (clientSockets.size() != other.clientSockets.size())
				return false;
			for (Socket s : clientSockets) {
				if (other.clientSockets.contains(s) == false)
					return false;
			}
			return true;
		}
		
		public void broadcastMessage(Message m) {
			setChanged();
			notifyObservers(m);
			return;
		}
		
	}
	class ClientHandler implements Runnable {
		private ObjectInputStream reader;
		private Socket sock;

		public ClientHandler(Socket clientSocket) {
			sock = clientSocket;
			try {
				reader = new ObjectInputStream(sock.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private ArrayList<Socket> idToSockets(ArrayList<Integer> clientIds) {
			if (clientIds.size() == 0) {
				return null;
			}
			ArrayList<Socket> idSockets = new ArrayList<Socket>();
			for (Integer i : clientIds) {
				Socket idSocket = serverClientSockets.get(i);
				idSockets.add(idSocket);
			}
			return idSockets;
		}
		
//		private ChatGroup findChatGroup(ArrayList<Socket> idSockets) {
//			ChatGroup socketGroup;
//			for (ChatGroup )
//			return socketGroup;
//		}

		public void run() {
			Message message;
			try {
				//while ((message = (Message) reader.readObject()) != null) {
				while (true) {
					message = (Message) reader.readObject();
					//takes message and finds out who it is from, who it needs to go to, and the content of the message
					//finds who it is from
					Integer clientID = serverClientIds.get(sock);
					//finds who it needs to go to
						//search list of chat groups for one that has all of those sockets that 
						//the person wants to deliver the message to
					//take list of recipients and convert to sockets
					ArrayList<Socket> idSockets = idToSockets(message.recipients);
					//find chat group with those sockets
					ChatGroup g;
					if (idSockets == null)
						g = allClients;
					else {
						if (idSockets.contains(sock) == false)
							idSockets.add(sock);
						g = findChatGroup(new ChatGroup(idSockets));
					}
					//notify that ChatGroup
					String intString = clientID.toString();
					String newMessageString = "Client " + intString + ": " + message.content;
					message.content = newMessageString;
					g.broadcastMessage(message);
					System.out.println(message.content);
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}

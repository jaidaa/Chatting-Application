package TheChat;

import java.net.*;
import java.util.logging.LoggingPermission;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Client {
	Socket socket;
	BufferedReader inFromUser;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	public Server server;
	String ip;
	String destination = "";
	String name;
	GUI gui;

	public Client(String name, int portNumber) throws UnknownHostException, IOException {

		socket = new Socket(name, portNumber);
		outToServer = new DataOutputStream(socket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		inFromUser = new BufferedReader(new InputStreamReader(System.in));
		this.gui = new GUI(this);

		// this.joinServer();

	}

	@SuppressWarnings("deprecation")
	public void joinServer() throws IOException {
		
		gui.needAnotherName = false;
		boolean flag = false;

		if (inFromServer.ready()) {
			String word = inFromServer.readLine();
			if (word.equals("no")) {
				gui.needAnotherName = true;
				// outToServer.writeBytes(gui.getSuggestedName() + '\n');
			} else if(word.equals("yes"))
				{
				gui.needAnotherName = false;
				flag = true;
				System.out.println(gui.suggestedName);
				this.doSomething();
			}
		}

	}
	

	public void doSomething() throws IOException {

		while (true) {

			if (inFromServer.ready()) {
				String serverMessage = inFromServer.readLine();
				String[] parts = serverMessage.split("@@");
				String sender = parts[0];
				String message = parts[1];
				gui.display(sender, message);
			}
		}
	}

	// if (inFromUser.ready()) {
	// if(())
	// String incomingMessage = inFromUser.readLine();

	// if (incomingMessage.equals("Online!!!")) {

	// this.GetMemberList();
	// } else {

	// if (incomingMessage.startsWith("-->")) {
	// destination = incomingMessage.substring(3);
	// } else {

	// this.Chat(name, destination, 2, incomingMessage);
	// }
	// }
	// if (incomingMessage.equalsIgnoreCase("bye") ||
	// (incomingMessage.equalsIgnoreCase("quit"))) {
	//
	// this.Quit();
	// }
	//
	// }
	//
	// }
	//
	// }

	public  JComboBox<String> GetMemberList() {

		 JComboBox<String> members = new JComboBox<String>();
		 String[] online;
		try {
			outToServer.writeBytes("Online!!!" + "\n");
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		try {
			if (inFromServer.ready()) { //if walla while?! 
				String people = inFromServer.readLine();
				//System.out.println("people containsss " + people);
				if(!people.equals("yes") && !people.equals("no")) {  // aw or mesh 3arfa 
				online = people.split("_");
				for (int i = 0; i < online.length; i++) {

					if (!online[i].equals(name)) {
					 // dont want to include myself as online
							members.addItem(online[i]);
							System.out.println("an online member is: " + online[i]);
							
						}
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("length of online is: " + online.length);
//		System.out.println("length of members is: " + members.getSize());
		return members;
	}

	public void Chat(String source, String destination, int TTL, String message) throws IOException {

		outToServer.writeBytes(destination + "-->" + source + "@@" + message + "\n");
	}

	public static void main(String args[]) throws UnknownHostException, IOException {

		// new Client("",7220);
		//new Client("", 7221);
		 new Client("", 7221);
		// new Client("",7222);
		// new Client("",7223);

	}

	public void Quit() throws IOException {

		outToServer.writeBytes("bye" + "\n");
		System.exit(0);
	}

}

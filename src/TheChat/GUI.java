package TheChat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI {
	JFrame mainFrame;
	JPanel loginPanel;
	JTextField userNameText;
	// JPanel choosingPanel;
	// ChatWindow chatWindow=null;
	String suggestedName;
	String user1 = "";
	String user2 = "";
	 static ArrayList<ChatWindow> windows = new ArrayList<ChatWindow>();

	static  ArrayList<String> chattingWith = new ArrayList<String>();
	// ArrayList<Client> users = new ArrayList<Client>();
	boolean needAnotherName;
	

	public GUI(Client c) {
		mainFrame = new JFrame();
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		Login(c);

	}

	public void Login(Client c) {

		loginPanel = new JPanel();
		loginPanel.setLayout(new FlowLayout());
		mainFrame.add(loginPanel);
		mainFrame.setSize(800, 600);
		JLabel namelabel = new JLabel("Please choose your user name: ", JLabel.RIGHT);

		userNameText = new JTextField(20);

		JButton loginButton = new JButton("Login");

		loginPanel.add(namelabel);
		loginPanel.add(userNameText);
		loginPanel.add(loginButton);
		loginPanel.setVisible(true);
		userNameText.setVisible(true);
		loginButton.setVisible(true);
		namelabel.setVisible(true);
		// loginButton.setFocusable(false);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					suggestedName = userNameText.getText();
					c.outToServer.writeBytes(getSuggestedName() + '\n');
					c.joinServer();

					if (needAnotherName == true) {
						System.out.println("ana wselt hena ya 3ial 1");
						JOptionPane.showMessageDialog(loginPanel, "Name already in use, please choose another name.");
						// mainFrame.remove(loginPanel);
					}

					else if(needAnotherName == false) {
						System.out.println("ana wselt hena ya 3ial ");
						c.name = suggestedName;
						user1 = suggestedName;
						JOptionPane.showMessageDialog(loginPanel, "Hello there, welcome to my humble app!");

						mainFrame.dispose();
						choosingMember(c);
					}
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			}
		});

	}

	public static void choosingMember(Client c) {
		JFrame choosing = new JFrame("Choosing a friend");
		choosing.setSize(400, 400);
		choosing.setVisible(true);

		JPanel choosingPanel = new JPanel();
		choosingPanel.setVisible(true);
		choosing.add(choosingPanel);
		JButton letMeChoose = new JButton("Get me the online people");
		choosingPanel.add(letMeChoose);
		letMeChoose.setVisible(true);
		letMeChoose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				letMeChoose.setVisible(false);
				JLabel text = new JLabel("Please choose a friend");
				text.setVisible(true);
				choosingPanel.add(text);
				//onlineMembers = new JComboBox<String>();
				JComboBox<String> onlineMembers = null;
				onlineMembers = c.GetMemberList() ;
				
				choosingPanel.add(onlineMembers);
				onlineMembers.setVisible(true);
				onlineMembers.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent event) {
						
						String destination = (String) ((JComboBox<String>) event.getSource()).getSelectedItem();
						chattingWith.add(destination);
						choosing.dispose();
						ChatWindow chatWindow = new ChatWindow(c, destination);
						// c is ME,the client..destination is the string person
						// im
						// talking to
						windows.add(chatWindow);

					}

				});

			}

			
		});

	}

	public void display(String sender, String message) {

		int chatWindowLocation = chattingWith.indexOf(sender);
		windows.get(chatWindowLocation).threads.append("<" + sender + ">: " + message);

	}

	public static void main(String[] args) {

	

	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public String getUser2() {
		return user2;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}


	public String getUserNameText() {
		// TODO Auto-generated method stub
		return userNameText.toString();
	}

	public String getSuggestedName() {
		// TODO Auto-generated method stub
		return suggestedName;
	}



}

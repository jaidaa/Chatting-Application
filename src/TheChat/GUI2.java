package TheChat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.ArrayList;

import javax.swing.AbstractButton;
//import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.IconView;

public class GUI2 {
	JFrame mainFrame;
	JPanel Panel;
	JPanel loginPanel;
	JPanel choosingPanel;
	JFrame chatWindow;
	String user1 = "";
	String user2 = "";
	JTextField messageBox = new JTextField(400);
	JTextArea chatBox = new JTextArea();
	boolean needAnotherName;
	JComboBox<String> onlineMembers;

	public GUI2() {
		TheWindow();
		// chatWindow();
		// this.Login();
	}

	public static void main(String[] args) {

		new GUI2();

	}

	public void TheWindow() {
		mainFrame = new JFrame();
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

//		Panel = new JPanel();
//		Panel.setLayout(new FlowLayout());
//
//		mainFrame.add(Panel);
		mainFrame.setVisible(true);

		this.Login();
	}

	public void chatWindow(String user1, String user2) {
		// mainFrame = new JFrame();
		// mainFrame.setSize(400, 400);
		// mainFrame.setLayout(new GridLayout(3, 1));
		// mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		//
		// Panel = new JPanel();
		// Panel.setLayout(null);
		//
		// JPanel mainPanel = new JPanel();
		// mainPanel.setLayout(new BorderLayout());
		//
		// // JTextField messageBox = new JTextField(30);
		messageBox.requestFocusInWindow();

		JLabel user1_name = new JLabel("I am " + user1, null, JLabel.LEFT);
		JLabel user2_name = new JLabel("I am chatting with " + user2);

		JButton send = new JButton();
		send.setAlignmentX(150);
		send.setAlignmentY(150);
		send.setText("Send");
		send.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Execute when button is pressed
				if (messageBox.getText().length() < 1) {
					// do nothing
				} else if (messageBox.getText().equals(".clear")) {
					chatBox.setText("Cleared all messages\n");
					messageBox.setText("");
				} else {
					chatBox.append("<" + user1 + ">:  " + messageBox.getText() + "\n");
					messageBox.setText("");
				}
				messageBox.requestFocusInWindow();
			}
		});

		chatBox.setEditable(false);
		chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
		chatBox.setLineWrap(true);

		// mainFrame.add(mainPanel);
		// mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// mainFrame.setSize(470, 300);
		// mainFrame.setVisible(true);
		//
		// mainFrame.add(new JScrollPane(chatBox), BorderLayout.CENTER);
		// mainFrame.add(user1_name);
		// mainFrame.add(user2_name);
		// mainFrame.add(messageBox);
		// mainFrame.add(send);
		// mainFrame.add(Panel);
		//mainFrame.setVisible(true);
	}

	private void Login() {

		loginPanel = new JPanel();
		loginPanel.setLayout(new FlowLayout());
		loginPanel.setVisible(true);
		mainFrame.add(loginPanel);
		
		JLabel namelabel = new JLabel("UserName: ", JLabel.RIGHT);
		final JTextField userNameText = new JTextField(20);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(needAnotherName == true)
				{
					JOptionPane.showMessageDialog(Panel,
						    "Please choose another name.",
						    "Name already in use.",
						    JOptionPane.PLAIN_MESSAGE);
				}
				user1 = userNameText.toString();
				choosingMember();
				
				
//				
//				
//				onlineMembers = new JComboBox<String>();
//				onlineMembers.setVisible(true);
//				onlineMembers.addItem("Mirette");
//				onlineMembers.addItem("Touts");
//				Panel.add(onlineMembers);
//				onlineMembers.addActionListener(new ActionListener() 
//				{
//
//					public void actionPerformed(ActionEvent event)
//					{
//						//JComboBox<String> combo = (JComboBox<String>) event.getSource();
//						user2 = (String) ((JComboBox<String>) event.getSource()).getSelectedItem();
//
//					}
//
//				});
//
			}

		});

		Panel.add(namelabel);
		Panel.add(userNameText);
		Panel.add(loginButton);
		mainFrame.setVisible(true);

		chatWindow(user1, user2);
	}
	
	public void choosingMember()
	{

		onlineMembers = new JComboBox<String>();
		onlineMembers.setVisible(true);
		onlineMembers.addItem("Mirette");
		onlineMembers.addItem("Touts");
		Panel.add(onlineMembers);
		onlineMembers.addActionListener(new ActionListener() 
		{

			public void actionPerformed(ActionEvent event)
			{
				//JComboBox<String> combo = (JComboBox<String>) event.getSource();
				user2 = (String) ((JComboBox<String>) event.getSource()).getSelectedItem();

			}

		});
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JPanel getPanel() {
		return Panel;
	}

	public void setPanel(JPanel panel) {
		Panel = panel;
	}

	public JFrame getChatWindow() {
		return chatWindow;
	}

	public void setChatWindow(JFrame chatWindow) {
		this.chatWindow = chatWindow;
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

	public JTextField getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(JTextField messageBox) {
		this.messageBox = messageBox;
	}

	public JTextArea getChatBox() {
		return chatBox;
	}

	public void setChatBox(JTextArea chatBox) {
		this.chatBox = chatBox;
	}

	public JComboBox<String> getOnlineMembers() {
		return onlineMembers;
	}

}

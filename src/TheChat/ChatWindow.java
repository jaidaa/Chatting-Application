package TheChat;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ChatWindow {
	JFrame chatWindow;
	String currentUser;
	JPanel chatPanel = new JPanel();
	JTextField messageBox = new JTextField();
	JTextArea threads = new JTextArea();
	JPanel stuff = new JPanel();
	JButton request = new JButton();
	JButton send = new JButton();
	JButton clear = new JButton();
	JButton quit = new JButton();
	
	
	public ChatWindow(Client c, String user2) {
		currentUser = user2;
		JFrame chatWindow = new JFrame("I am " + c.name + ", chatting with " + user2);
		chatWindow.setSize(400, 400);
		chatWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		chatWindow.setVisible(true);

		chatPanel.setLayout(new GridLayout(4, 1));
		chatPanel.setVisible(true);

		messageBox.setVisible(true);

		threads.setVisible(true);
		threads.setEditable(false);
		threads.setFont(new Font("Serif", Font.PLAIN, 15));
		threads.setLineWrap(true);

		chatWindow.add(chatPanel);
		chatPanel.add(threads);
		chatPanel.add(messageBox);

		messageBox.requestFocusInWindow();
		
		
		stuff.setVisible(true);
		stuff.setLayout(new GridLayout(3, 1));
		chatPanel.add(stuff);

		JLabel user2_name = new JLabel("I am chatting with " + user2, null, JLabel.CENTER);
		user2_name.setVisible(true);

		
		send.setVisible(true);
		send.setText("Send");

		
		clear.setVisible(true);
		clear.setText("Clear messages");
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				threads.setText("");
				messageBox.setText("");

			}
		});

		
		request.setVisible(true);
		request.setText("Chat with another friend");
		request.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUI.choosingMember(c);

			}
		});

		stuff.add(user2_name);
		stuff.add(send);
		stuff.add(clear);
		stuff.add(request);
		send.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				String message = (messageBox.getText());
				// Execute when button is pressed
				if (message.length() < 1) {
					// do nothing
				} else {
					threads.append("<" + c.name.toString() + ">:  " + messageBox.getText() + "\n");
					messageBox.setText("");
					try {
						c.Chat(c.name, currentUser, 2, message);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				messageBox.requestFocusInWindow();
			}
		});

		quit = new JButton("Exit conversation");
		chatPanel.add(quit);
		quit.setVisible(true);
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					c.Quit();
					chatWindow.dispose();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});	
		
	}

	public void display(String user,String message) {
		threads.append("<" + user.toString() + ">:  " + messageBox.getText() + "\n");		
	}

	
}

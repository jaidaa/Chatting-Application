package TheChat;

import java.io.*;
import java.util.*;
import java.util.zip.InflaterOutputStream;
import java.net.*;

public class CentralServer {
	String name;
	ServerSocket theBoss;
	int port;

	ArrayList<String> allMembers = new ArrayList<>();

	Socket socketForServers;
	ArrayList<Socket> serverSockets = new ArrayList<>();

	ArrayList<Thread> serverThreads = new ArrayList<Thread>();

	BufferedReader inFromSmallServer1;
	DataOutputStream outToSmallServer1;

	BufferedReader inFromSmallServer2;
	DataOutputStream outToSmallServer2;

	BufferedReader inFromSmallServer3;
	DataOutputStream outToSmallServer3;

	BufferedReader inFromSmallServer4;
	DataOutputStream outToSmallServer4;

	ArrayList<String> firstServerMembers = new ArrayList<>();
	ArrayList<String> secondServerMembers = new ArrayList<>();
	ArrayList<String> thirdServerMembers = new ArrayList<>();
	ArrayList<String> fourthServerMembers = new ArrayList<>();

	public CentralServer(String name, int portNumber) throws IOException {

		this.name = name;
		this.port = portNumber;
		this.theBoss = new ServerSocket(port);

		System.out.println("The Central Server is on!");

		while (true) {
			socketForServers = theBoss.accept();

			new Thread() {

				public void run() {
					try {
						CentralResponseSignUpRequest(socketForServers);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}

	}

	public void CentralResponseSignUpRequest(Socket socket) throws IOException, InterruptedException {

		BufferedReader inFromSmallServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outToSmallServer = new DataOutputStream(socket.getOutputStream());

		boolean flag = false;
		while (!flag) {

			String message = inFromSmallServer.readLine();

			System.out.println(message);
			String[] CentralParts = message.split(":");

			if (!(this.allMembers.contains(CentralParts[1]))) {
				outToSmallServer.writeBytes("yes" + "\n");
				this.allMembers.add(CentralParts[1]);
				flag = true;

				int serverId = Integer.parseInt(CentralParts[0]);

				switch (serverId) {
				case 1:
					this.firstServerMembers.add(CentralParts[1]);
					this.inFromSmallServer1 = inFromSmallServer;
					this.outToSmallServer1 = outToSmallServer;
					// this.serverSockets.add(socket);
					break;

				case 2:
					this.secondServerMembers.add(CentralParts[1]);
					this.inFromSmallServer2 = inFromSmallServer;
					this.outToSmallServer2 = outToSmallServer;
					// this.serverSockets.add(socket);
					break;

				case 3:
					this.thirdServerMembers.add(CentralParts[1]);
					this.inFromSmallServer3 = inFromSmallServer;
					this.outToSmallServer3 = outToSmallServer;
					// this.serverSockets.add(socket);
					break;

				case 4:
					this.fourthServerMembers.add(CentralParts[1]);
					this.inFromSmallServer4 = inFromSmallServer;
					this.outToSmallServer4 = outToSmallServer;
					// this.serverSockets.add(socket);
					break;
				}

			} else {
				outToSmallServer.writeBytes("no" + "\n");
			}

		}

		CentralServerStuff(socket, inFromSmallServer, outToSmallServer);

	}

	public void CentralServerStuff(Socket socket, BufferedReader inFromSmallServer, DataOutputStream outToSmallServer)
			throws IOException, InterruptedException {
		while (true) {
			if (inFromSmallServer.ready()) {
				String message = inFromSmallServer.readLine();

				if (message.contains(":")) {
					ClientIsSigningUp(message, inFromSmallServer, outToSmallServer);
				} else {
					if (message.equals("Online!!!")) {
						MemberListResponse(outToSmallServer);
					}

					else {

						String[] routingSplitter = message.split("-->");

						String HostToRecieve = routingSplitter[2];

						String messageToBeForwarded = routingSplitter[1];

						System.out.println("I received the message from the small server");
						System.out.println(message);

						switch (findDestinationServer(HostToRecieve)) {

						case 1:
							this.outToSmallServer1.writeBytes(HostToRecieve + ".-." + messageToBeForwarded + '\n');
							break;

						case 2:
							this.outToSmallServer2.writeBytes(HostToRecieve + ".-." + messageToBeForwarded + '\n');
							break;

						case 3:
							this.outToSmallServer3.writeBytes(HostToRecieve + ".-." + messageToBeForwarded + '\n');
							break;

						case 4:
							this.outToSmallServer4.writeBytes(HostToRecieve + ".-." + messageToBeForwarded + '\n');
							break;

						}
					}
				}
			}
		}
	}

	public void ClientIsSigningUp(String message, BufferedReader inFromSmallServer, DataOutputStream outToSmallServer)
			throws IOException {

		System.out.println(message);
		String[] CentralParts = message.split(":");

		if (!(this.allMembers.contains(CentralParts[1]))) {
			outToSmallServer.writeBytes("yes" + "\n");
			this.allMembers.add(CentralParts[1]);

			int serverId = Integer.parseInt(CentralParts[0]);

			switch (serverId) {
			case 1:
				this.firstServerMembers.add(CentralParts[1]);
				break;

			case 2:
				this.secondServerMembers.add(CentralParts[1]);
				break;

			case 3:
				this.thirdServerMembers.add(CentralParts[1]);
				break;

			case 4:
				this.fourthServerMembers.add(CentralParts[1]);
				break;
			}

		} else {
			outToSmallServer.writeBytes("no" + "\n");
		}
	}

	public static void main(String atgs[]) throws IOException {
		new CentralServer("", 4200);

	}

	public int findDestinationServer(String toBeFound) {
		if (this.firstServerMembers.contains(toBeFound))
			return 1;
		if (this.secondServerMembers.contains(toBeFound))
			return 2;
		if (this.thirdServerMembers.contains(toBeFound))
			return 3;
		if (this.fourthServerMembers.contains(toBeFound))
			return 4;

		return 0;
	}

	public void MemberListResponse(DataOutputStream out) throws IOException {
		System.out.println("I recieved the online request");

		String allTheMembersInOneLine = "";
		for (int i = 0; i < this.allMembers.size(); i++) {
			allTheMembersInOneLine = allTheMembersInOneLine + this.allMembers.get(i) + "_";
			System.out.println(this.allMembers.get(i));
		}

		out.writeBytes(allTheMembersInOneLine + '\n');

	}

}

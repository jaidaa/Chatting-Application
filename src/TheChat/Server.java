package TheChat;

import java.io.*;
import java.util.*;
import java.util.zip.InflaterOutputStream;
import java.net.*;

public class Server {

	public int port;
	ArrayList<DataOutputStream> outputs = new ArrayList<>();
	ArrayList<BufferedReader> inputs = new ArrayList<>();
	ArrayList<String> connectedClients = new ArrayList<>();
	ServerSocket theDoor;
	Socket servingSocket;

	Socket routingSocket;
	String name;

	DataOutputStream outToCentralServer;
	BufferedReader inFromCentralServer;

	int serverNumber;

	public Server(int serverNumber, String name, int portNumber, String nameCentral, int portNumberForCentral)
			throws IOException {

		this.serverNumber = serverNumber;
		this.name = name;
		port = portNumber;
		this.theDoor = new ServerSocket(port);

		System.out.println("Server " + this.serverNumber + " is ready to ,wait for it, serve!");

		this.routingSocket = new Socket(nameCentral, portNumberForCentral);

		System.out.println("Server " + this.serverNumber + " has connected succesfully to the Central Server");

		outToCentralServer = new DataOutputStream(routingSocket.getOutputStream());
		inFromCentralServer = new BufferedReader(new InputStreamReader(routingSocket.getInputStream()));

		while (true) {
			servingSocket = theDoor.accept();
			new Thread() {

				public void run() {
					try {
						joinResponse(servingSocket);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	public void joinResponse(Socket socket) throws IOException, InterruptedException {

		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
		String clientSentence = null;
		boolean flag = false;
		while (!flag) {

			if (inFromClient.ready()) {
				clientSentence = inFromClient.readLine();
				if (clientSentence != ("Online!!!")) { // just added this
					System.out.println(clientSentence);
					this.outToCentralServer.writeBytes(this.serverNumber + ":" + clientSentence + "\n");

				}
			}
				// }
				// if (inFromCentralServer.ready()) { // just added this
				// inFromServer = this.inFromCentralServer.readLine();
				// System.out.println(inFromServer);
				//
				// }
				if (inFromCentralServer.ready()) {

					String inFromServer = inFromCentralServer.readLine();
					if (inFromServer.equals("yes")) {
						outToClient.writeBytes("yes" + "\n");
						System.out.println(clientSentence + " is accepted");
						this.connectedClients.add(clientSentence);
						this.outputs.add(outToClient);
						this.inputs.add(inFromClient);
						flag = true;
						serve(socket, inFromClient, outToClient);
					} else if (inFromServer.equals("no")) {
						outToClient.writeBytes("no" + "\n");
						System.out.println("duplicate");
					}

				}
			}
		}

	
	// serve(socket, inFromClient, outToClient);

	public void serve(Socket socket, BufferedReader inFromClient, DataOutputStream outToClient)
			throws IOException, InterruptedException {
		while (true) {
			if (inFromClient.ready()/* ||inFromCentralServer.ready() */) {
				String message = inFromClient.readLine();
				if (message.equals("Online!!!")) {
					DealWithOnlineRequest(outToClient);
				} else {

					String[] parts = message.split("-->");
					int destinationID = connectedClients.indexOf(parts[0]);
					System.out.println("message going to" + parts[0]);

					if (message.equals("bye")) {
						int deadIndex = inputs.indexOf(inFromClient);
						connectedClients.remove(deadIndex);
						outputs.remove(deadIndex);
						inputs.remove(deadIndex);
						socket = null;
					}

					else {
						if (destinationID != -1) {
							outputs.get(destinationID).writeBytes(parts[1] + '\n');
						}

						else

						{
							GoToCentralServer(this.serverNumber, parts[0], 2, parts[1]);
						}
					}
				}

			}
			if (this.inFromCentralServer.ready()) {

				String recieved = this.inFromCentralServer.readLine();
				if (recieved.contains(".-.")) {
					System.out.println("gale 7agat");
					String[] yarb = recieved.split(".-.");

					int id = this.connectedClients.indexOf(yarb[0]);

					System.out.println(yarb[0]);
					System.out.println((this.connectedClients.contains(yarb[0])));
					System.out.println(yarb[1]);

					this.outputs.get(id).writeBytes(yarb[1] + '\n');
					System.out.println("e7na hena!");
				}

			}
		}

	}

	public static void main(String[] args) throws IOException {
		// new Server(1,"",7220,"",4200);
		new Server(2, "", 7221, "", 4200);
		// new Server(3, "", 7222, "", 4200);
		// new Server(4, "", 7223, "", 4200);
	}

	public void GoToCentralServer(int serverNumber, String destination, int TTL, String message)
			throws IOException, InterruptedException {

		this.outToCentralServer.writeBytes(serverNumber + "-->" + message + "-->" + destination + "\n");
		System.out.println("i forawrded to the central server");
	}

	public void DealWithOnlineRequest(DataOutputStream out) throws IOException {
		this.outToCentralServer.writeBytes("Online!!!" + "\n");

		System.out.println("i sent online to the central server");

		out.writeBytes(inFromCentralServer.readLine() + '\n');

		System.out.println("i forwarded the onlines!");
	}

	public void RecieveFromCentralServerAndForwardToLocalClient() throws IOException {

	}

}

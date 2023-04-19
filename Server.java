import java.io.*;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket;
	
	//Constructor that creates a ServerSocket listening on a specific port
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	//Start method that accepts incoming client connections and creates new thread for each client
	public void start() throws IOException {
		while (true) {
			Socket clientSocket = serverSocket.accept();
			String ipAddress = clientSocket.getInetAddress().getHostAddress();
			int port = clientSocket.getPort();
			System.out.println("Connection established with " + ipAddress + ", port = " + port);
			new Thread(new ClientHandler(clientSocket)).start();
		}
	}
	
	//Client Handler class that handles communication with a single client
	class ClientHandler implements Runnable {
		private Socket clientSocket;		//Socket connected to the client
		private ObjectOutputStream out;		//Output stream for sending messages
		private ObjectInputStream in;		//Input stream for receiving messages
		
		//Constructor that initializes the socket and input/output streams
		public ClientHandler(Socket socket) throws IOException {
			this.clientSocket = socket;
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
		}
		
		//Run method that runs a new thread and handles communication with client
		public void run() {
			try {
				//Wait for login message from client
				(blank) message = (blank) in.readObject();
				
				//If the login message is received, send a confirmation response and start messasge looping
				if (message.getType().equals("login") ) {
					message.setStatus("success");
					out.writeObject(message);
					out.flush();
				} else if (message.getType().equals("logout")) {
					message.setStatus("success");
					out.writeObject(message);
					out.flush();
					clientSocket.close();
					break;
				}
			}
		}
	}

}

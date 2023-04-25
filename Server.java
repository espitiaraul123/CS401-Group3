import java.io.*;
import java.net.*;

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors


public class Server {
	public static void main(String[] args) {
		ServerSocket server = null;

		try {
			// server is listening on port 1234
			server = new ServerSocket(1234);
			server.setReuseAddress(true);
			
			System.out.println("Server Started");
			
			// Infinite loop accepts incoming client connections and creates new thread for each client
			while (true) {
				Socket client = server.accept(); 					// socket object to receive incoming client requests
				ClientHandler clientSock = new ClientHandler(client);	// create a new thread object
				new Thread(clientSock).start();							// This thread will handle the client separately
			}
		}
		catch (IOException e) {
		}
		finally {
			if (server != null) {
				try {
					server.close();
				}
				catch (IOException e) {
				}
			}
		}
	}
	
    private static class ClientHandler implements Runnable {
        private Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
			Message message = null;
			//boolean connection = false;
			
			try {
		        // create a ObjectOutputStream so we can write data from it.
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		        // create a ObjectInputStream so we can read data from it.
		        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

		       while((message = (Message)objectInputStream.readObject()) != null) {
		    	   // do stuff
		    	   message.setText("POOP");
		    	   objectOutputStream.writeObject(message);
		       }	
			} catch (Exception e){
			}	
        }
    } 
}

import java.io.*;
import java.net.*;

// Client class
class Client {
	// driver code
	public static void main(String[] args) throws ClassNotFoundException {
		// establish a connection by providing host and port number
		try (Socket socket = new Socket("localhost", 1234)) {
	        // create a ObjectOutputStream so we can write data from it.
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

	        int choice = 1;
	        do {
	        	Message message = null;
	        	Message returnMessage = null;
	        	switch (choice) {
	        	case 1:
	        		message = new Message();
	        		objectOutputStream.writeObject(message);
	        		returnMessage = (Message)objectInputStream.readObject();
	        		System.out.println(returnMessage.getText());
	        		break;
	        	case 2:
	        		break;
	        	case 3:
	        		break;
	        	default:		
	        	}
	        } while (choice != 1);
	        socket.close();
		}
		catch (IOException e) {
		}
	}
}

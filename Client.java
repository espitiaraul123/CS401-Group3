import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

// Client class
class Client {
	// driver code
	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("inside client class");
		// establish a connection by providing host and port number
		try (Socket socket = new Socket("localhost", 1234)) {
			//create way to send data to server
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			Scanner scanner = new Scanner (System.in);
			String line = null;
			
			//start the GUI
			System.out.println("starting login GUI");
			//use show input dialog
			String username = JOptionPane.showInputDialog(null, "Hello, please enter your username");
			String password = JOptionPane.showInputDialog(null, "please enter your password");
			
			String loginInfo = username+ " "+password;
			
			//use enteredcredentials and send them to the server
			/*while (!"exit".equalsIgnoreCase(line)) {
				System.out.println("credentials have not been made");
				if (login.enteredCredentials() == true)
				{
					System.out.println("credentials have now been entered");
					break;
				}
			}*/
			System.out.println("veryfying your identity using "+loginInfo+" by first making a message object");
			Message loginMessage = new Message(MsgType.Login, MsgStatus.Undefined, loginInfo);
			
			// create a ObjectOutputStream so we can write data from it.
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
	        System.out.println("writing object");
	        
	        objectOutputStream.writeObject(loginMessage);
	        System.out.println("fetching return message");
    		Message returnMessage = (Message)objectInputStream.readObject();
    		
    		if (returnMessage.getStatus() == MsgStatus.Success) {
    			System.out.println("Successful login");
    		}
    		System.out.println(returnMessage.getText());
			//pass the message
			out.println(loginMessage);
			out.flush();
			
			//out.flush();
			/*while(!"exit".equalsIgnoreCase(line)) {
				line = scanner.nextLine();
				out.println(line);
				out.flush();
				System.out.println("Server replied " + in.readLine());
			}*/
			
			scanner.close();
			
	        /*// create a ObjectOutputStream so we can write data from it.
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
	        
	        System.out.println("inside client class again");
	        ///make the user login using the GUI
	        LoginGUI loginPage = new LoginGUI();
	        
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
	        } while (choice != 1);*/
			System.out.println("ending client program");
	        socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/// Client class
class Client {
	// driver code
	public static void main(String[] args) throws ClassNotFoundException {
		// establish a connection by providing host and port number
		try (Socket socket = new Socket("localhost", 1234)) {
	        // create a ObjectOutputStream so we can write data from it.
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
	        
	        int selectedOption = JOptionPane.showConfirmDialog(null, 
                    "Hello are you logging in as a banker?", 
                    "Choose", 
                    JOptionPane.YES_NO_OPTION); 
			if (selectedOption == JOptionPane.YES_OPTION) {
				
			
		        String username = JOptionPane.showInputDialog(null, "Hello, please enter your userID");
				//int userid = Integer.parseInt(userID);
				String password = JOptionPane.showInputDialog(null, "please enter your PIN");
				//int pin = Integer.parseInt(PIN);
				
				if ((username+password).equals("adminbanker")) {
			    	JFrame jFrame = new JFrame();
	    		    JOptionPane.showMessageDialog(jFrame, "login was successful! welcome back banker");
			    	BankerGUI guiForBanker = new BankerGUI();
			    }
			}
			else {
				//if the login is successfull open the ATM GUI
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame, "logging into customer profile");
				
                // Code to execute when createButton is clicked
            	
            	String fullname = JOptionPane.showInputDialog(frame, "Please enter your name");
	        	String userID = (JOptionPane.showInputDialog(frame, "please enter your userID"));
	        	String pin = (JOptionPane.showInputDialog(frame, "Please enter your pin"));
	        	List<String> arrayOfStrings = new ArrayList<String>();
	        	arrayOfStrings.add(fullname);
	        	arrayOfStrings.add(userID);
	        	arrayOfStrings.add(pin);
	        	Message newMessage = new Message(MsgType.Login, MsgStatus.Undefined,arrayOfStrings);
	        	
            	//send the message to the server
            	try {
            		JOptionPane.showMessageDialog(frame, "sending message");
					//ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					//ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
					objectOutputStream.writeObject(newMessage);
					newMessage = (Message)objectInputStream.readObject();
					
			        
			        
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	// create a ObjectInputStream so we can read data from it.
		        //passing message to server
            	if (newMessage.status == MsgStatus.Success) {
		        	JOptionPane.showMessageDialog(frame, "Successfully logged in and fetched customer");
		        	Customer customer = newMessage.attachedCustomer;
		        	ATM ATMgui= new ATM(customer);
            	}
		        	//add data to the array
		    
			}
	        int choice = 2;
	        do {
	        	Message message = null;
	        	//Message returnMessage = null;
	        	switch (choice) {
	        	case 1:
	        		message = new Message();
	        		objectOutputStream.writeObject(message);
	        		// returnMessage = (Message)objectInputStream.readObject();
	        		// System.out.println(returnMessage.getText());
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

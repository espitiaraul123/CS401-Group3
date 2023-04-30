import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

// Client class
class Client {
	// driver code
	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("inside client class");
		// establish a connection by providing host and port number
		try (Socket socket = new Socket("localhost", 1234)) {
			//start the GUI
			//use show input dialog
			String username = JOptionPane.showInputDialog(null, "Hello, please enter your username");
			String password = JOptionPane.showInputDialog(null, "please enter your password");
			
			String loginInfo = username+password;
			
			
			System.out.println("verifying your identity using your login info");
			Message loginMessage = new Message();
			loginMessage.makeLoginMessage(username, password);
			
			// create a ObjectOutputStream so we can write data from it.
	        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
	        //passing message to server
	        oos.writeObject(loginMessage);
	        System.out.println("fetching message");
	        //getting message from the server
    		Message returnMessage = (Message)ois.readObject();
    		System.out.println("back inside client class");
    		System.out.println("got message");
    	
    		if (returnMessage.getStatus() == MsgStatus.Failure) {
    			//make a popup
    			   JFrame jFrame = new JFrame();
    		       JOptionPane.showMessageDialog(jFrame, "login was unsuccessful");
    		   
    		}
    		else {
    			String returnM = returnMessage.username + returnMessage.password;
    			///if the return message says that the person login in as a banker, show the banker gui
    		    ///else show the ATM
    		    
    		    
    		    if (returnM.equals("adminbanker")) {
    		    	JFrame jFrame = new JFrame();
        		    JOptionPane.showMessageDialog(jFrame, "login was successful! welcome back banker");
    		    	BankerGUI guiForBanker = new BankerGUI();
    		    }
    		    else {
    		    	System.out.println(returnM);
    		    	JFrame jFrame = new JFrame();
        		    JOptionPane.showMessageDialog(jFrame, "login was successful! Fetching your profile");
        		    //if the person is a banker, show the banker gui
        		    
    		    	ATM guiForCustomer = new ATM(returnMessage.newCustomer);
    		    }
    		    //the guis will now send and receive messages from the server.
    		    
    		    while (socket.isConnected()) {
    		    	
    		    }
    		    
    		}
    		
			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

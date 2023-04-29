// Import the IOException class to handle errors
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Server {
	public static void main(String[] args) {
		ServerSocket server = null;
		
		try {
			// server is listening on port 1234
			server = new ServerSocket(1234);
			server.setReuseAddress(true);
			// The main thread is just accepting new connections
			
			System.out.println("Server Started");
			
			// Infinite loop accepts incoming client connections and creates new thread for each client
			while (true) {
				Socket client = server.accept(); 					// socket object to receive incoming client requests
				System.out.println("New client connected " + client.getInetAddress().getHostAddress());
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
					e.printStackTrace();
				}
			}
		}
	}
	
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ArrayList<Customer> listOfCustomers = new ArrayList<>();
        
        ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
        	System.out.println("inside run");
        	
			// create a ObjectOutputStream so we can write data from it.
			try {
				ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
		        // create a ObjectInputStream so we can read data from it.
				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
		      //
				Message msgFromClient;
				Message response;
			    //this loop will receive all the messages
			    while (clientSocket.isConnected()) {
			    	try {
			    		msgFromClient = (Message)ois.readObject();
				    	
				    	
				    	if (msgFromClient.getType() == MsgType.Login) {
				    		response = login(msgFromClient);
				    		oos.writeObject(response);
				    		System.out.println("sent back");
				    	}
				    	//if message type is to make a new customer, make a new customer
				    	else if (msgFromClient.getType() == MsgType.NewCustomer) {
				    		writeCustomerToFile(msgFromClient);
				    		
				    	}
			    	} catch (IOException e) {
			    		break;
			    	}
			    	
			    	//if the message type is logout, break?
			    	/*if (msgFromClient.getType() == MsgType.Logout) {
			    		break;
			    	}*/
			    	
			    }
			    
				
				clientSocket.close();
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		}
        //not finished
        public Message login(Message message) {
        	if (message.username+message.password == "adminbanker") {
        		message.status = MsgStatus.Success;
        	}
        	return message;
        	/*try {
				ObjectOutputStream objOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				// create a ObjectInputStream so we can read data from it.
		        
				ObjectInputStream objInputStream = new ObjectInputStream(clientSocket.getInputStream());
				Message recievedLoginInfo = (Message)objInputStream.readObject();
				
				//verify that the username exists.
				
					BufferedReader reader = new BufferedReader(new FileReader("CustomerData.txt"));
					
					//read until line is found
					String line;
					boolean success = false;
					while((line = reader.readLine()) != null) {
						//only read the current string until the first coma to compare usernames and passwords
						String values[] = line.split(",");
						//if the username and password are found, return customer data to the client
						//AND IF THE USER ISN'T CURRENTLY LOGGED IN
						if (values[0].equals(message.username+message.password) && values[3].equals("no")||message.username+message.password == "adminbanker") {
							success = true;
							//loginLine = line;
							System.out.println("user has been found");
							//send the line accross
							message.setStatus(MsgStatus.Success);
							message.setText(line);
							objOutputStream.writeObject(message);
							//since the user is active, change activity to yes
							values[3] = "yes";
							line = String.join(",",values);
							//write the string back to the file.
							
							Path path = Paths.get("loginCombinations.txt");
						    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
						    //values[1] is the line number, but we must turn it into an int
						    int lineNumber = Integer.parseInt(values[1]);
						    
						    lines.set(lineNumber, line);
						    Files.write(path, lines, StandardCharsets.UTF_8);
						}
						
					}
					///if the user was not found, return the message marked as a failure to the client
					if (success == false) {
						System.out.println("user has not been found");
						message.setStatus(MsgStatus.Failure);
						objOutputStream.writeObject(message);
						reader.close();
						return;
					}*/
		}
        //this function reads all the customer objects, and returns them.
        public void getAllCustomersFromFile() throws IOException, ClassNotFoundException {
        	//read all customers and get them in an array
        	FileInputStream fis = new FileInputStream("CustomerData.txt");
        	ObjectInputStream ois = new ObjectInputStream(fis);
        	
        	listOfCustomers = (ArrayList<Customer>) ois.readObject();
        	int num = 0;
        	for (Customer mycustomer: listOfCustomers) {
        		num++;
        	}
        	System.out.println("There are "+num+" customers currently saved");
        	
        	ois.close();
        	fis.close();
        }
        // writes a customer object to the file.
        public void writeCustomerToFile(Message message) throws FileNotFoundException, IOException {
        	File file = new File("CustomerData.txt");
        	
        	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        	oos.writeObject(message.newCustomer);
        	oos.close();
        }
		
			
    }
    
    
	
	/*public Message doDeposit(Customer customer, List<String> depositData) {
		Account acct = null;
		
		// if account given then process 
		//acct = (Account) depositData.get(0);	
		
		// if only the Account ID is given then find associated account
		int acctID = (int) depositData.get(0);
		// find account using account id and customers name
		acct = customer.findAccount(acctID, customer.getName());
		
		// get the amount to deposit and the transaction type
		double amount = (double) depositData.get(1);
		TransactionType type = (TransactionType) withdrawData.get(2);
		
		// deposit
		acct.deposit(amount, type);
		String balancAfterDeposit = (String) acct.getBalance();
		String amtDeposit = (String) amount;
		
		// return message type deposit
		// message text = amount to depsoit, balance of funds in account after the deposit transaction
		return (new Message(MsgType.Desposit, MsgStatus.Success, amtDeposit + "," + balancAfterDeposit));
		
	}*/
	
	/*public boolean doWithdraw(Customer customer, List<String> withdrawData) {
		Account acct = null;
		
		// if account given
		//acct = (Account) depositData.get(0);	
		
		// if only acct id given then find account with given/known info
		int acctID = (int) withdrawData.get(0);
		acct = customer.findAccount(acctID, customer.getName());
		
		double amount = (double) withdrawData.get(1);
		TransactionType type = (TransactionType) withdrawData.get(2);
		
		String amtWithdraw = (String) amount;

		// if amount is less than balance thenn withdraw
		if(acct.getBalance() > 0.01 && amount <= acct.getBalance()) {
			
			if(acct.withdraw(amount, type)) {
				String balancAfteWithdraw = (String) acct.getBalance();
				
				// message text = amount to withdraw, balance of funds in account after the withdraw transaction
				return (new Message(MsgType.Withdraw, MsgStatus.Success, amtWithdraw + "," + balancAfteWithdraw));

				
			}
		}
		else {
			return (new Message(MsgType.Withdraw, MsgStatus.Falure, "Insufficient Funds"));
		}
		
		
	}*/
	
	
	/*public Message doTransfer(Customer customer, List<String> transferData) {
		Account accountSender = null;
		Account accountReceipient = null;
		
		// if accounts given
		//accountSender = (Account) transferData.get(0);
		//accountReceipient = (Account) transferData.get(1);
		
		// if only acct id given
		int idFrom = (int) transferData.get(0);
		int idTo = (int) transferData.get(1);
		accountSender = customer.findAccount(idFrom, customer.getName());
		accountReceipient = customer.findAccount(idTo, customer.getName());

		String amountToTransfer = transferData.get(2);
		double amount = (double) amountToTransfer;
		
		if(accountSender.transfer(amount, accountReceipient)) {
			
			
			return (new Message(MsgType.Transfer, MsgStatus.Success, amountToTransfer + "," + accountSender.getBalance()+ "," + accountReceipient.getBalance()));

		}
		else {
			
			return (new Message(MsgType.Transfer, MsgStatus.Failure, "Failed to transfer");

		}

	}*/
	
	/*public static Message newCustomer(Message message) {
		
		String name = message.name;
		int pin = message.pin;
		String address = message.Address;
		
		Customer newCustomer = new Customer(pin, name, address);
		String customerInfo = name + "," + newCustomer.getUserID();
		return (new Message(MsgType.NewCustomer, MsgStatus.Success, customerInfo));

	}*/
	
	/*public Message newAccount(Customer customer, List<String> accountData) {
		
		String name = accountData.get(0);
		AccountType type = (AccountType) accountData.get(1);
		double initialDeposit = (double) accountData.get(2);
		
		Account newAccount = customer.addAccount(initialDeposit, type);
		
		String accountInfo = name + "," + newAccount.getAccountNumber() + "," + newAccout.getBalance();
		return (new Message(MsgType.NewAccount, MsgStatus.Success, accountInfo));

	}*/
	
	public Message removeCustomer(List<String> customerData) {
		
		// use hash map?
		return (new Message(MsgType.RemoveCustomer, MsgStatus.Undefined, "Remove Customer"));
		
	}
	
	/*public Message removeAccount(Customer customer, List<String> accountData) {
		
		int accountNumber = (int) accountData.get(0);
		
		customer.closeAccount(accountNumber);
		
		return (new Message(MsgType.RemoveAccount, MsgStatus.Success, "Account Closed"));
		
	}*/
	
	
	public Message loggingOut(List<String> customerData) {
		
		return (new Message(MsgType.Logout, MsgStatus.Success, "Logged out!"));
		
	}
			
			
        	
   
}

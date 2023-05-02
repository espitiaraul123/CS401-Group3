import java.io.*;
import java.net.*;
import java.util.*;
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
				Socket client = server.accept(); 						// socket object to receive incoming client requests
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
        Customer currentCustomer;
        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
			Message message = null;
			//boolean connection = false;
			
    		// A HashMap to hold all the Customer's information
    		// including all its accounts and transactions
			
        	Map<Integer, Customer> customers = new HashMap<>();
        	readCustomersFromFile(customers);
			
			try {
		        // create a ObjectOutputStream so we can write data from it.
		        // ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		        // create a ObjectInputStream so we can read data from it.
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				while((message = (Message)objectInputStream.readObject()) != null) {
					
		        	// do stuff
		        	// Tests
					//if the message type new Customer ,call new customer with appropriate parameters
		        	if (message.getType() == MsgType.NewCustomer) {
		        		message = createNewCustomer(message.data, customers);
		        		objectOutputStream.writeObject(message);
		        		
		        	}
		        	else if (message.getType() == MsgType.NewAccount) {
		        		message = addAccount(message.data, customers);
		        		objectOutputStream.writeObject(message);
		        	}
		        	else if (message.getType() == MsgType.Withdraw) {
		        		message = withdraw(message.data, customers);
		        		objectOutputStream.writeObject(message);
		        	}
		        	/*System.out.println(getNewUserID());
		        	
		        	List<String> newCustomerData = List.of("222", "Bobby");
		        	createNewCustomer(newCustomerData, customers);
		        	writeCustomersToFile(customers);
		        	*/
		        	
		        	/* add account test
		        	List<String> data = List.of("2", "Checking"); 
		        	addAccount(data, customers);
		        	*/
		        	else if (message.getType() == MsgType.Deposit) {
		        		message = deposit(message.data, customers);
		        		System.out.println(message.attachedAccount.getBalance());
		        		objectOutputStream.writeObject(message);
		        	}
		        	else if (message.getType() == MsgType.ViewTransactions) {
		        		message = viewTransactions(message.data, customers);
		        		objectOutputStream.writeObject(message);
		        	}
		        	else if (message.getType() == MsgType.Transfer) {
		        		message = transferFunds(message.data, customers);
		        		objectOutputStream.writeObject(message);
		        	}
		        	
		        	// verifyLogin tests
		        	else if (message.getType()==MsgType.Login) {
						message = verifyLogin(message.data,customers);
						//send the message object with the customer back to the gui
						objectOutputStream.writeObject(message);
						
					}
		        	
		        	
		        	
		        	/* removeCustomer test
		        	List<String> data = List.of("2");
		        	Message returnMessage = removeCustomer(data, customers);
		        	writeCustomersToFile(customers);
		        	*/
		        	
		        	/* removeAccount test */
		        	/*List<String> data = List.of("324", "90");
		        	removeAccount(data, customers);
		        	writeCustomersToFile(customers);*/
		        	
		        	System.out.println("Done");
		        	
		        }
			} catch (Exception e) {
			}
        }
        
        // done
        public void writeCustomersToFile(Map<Integer, Customer> customers) {
        	try {
        		FileWriter customerDataFile = new FileWriter("customerData.txt");
        		BufferedWriter fileWriter = new BufferedWriter(customerDataFile);
        		

        		for (Map.Entry<Integer, Customer> customer : customers.entrySet()) {
        			Customer tempCustomer = customer.getValue();
        			fileWriter.write(tempCustomer.toString());
        		}
        		fileWriter.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }

        // done
        public void readCustomersFromFile(Map<Integer, Customer> customers) {
        	try {
        		// Open file that holds all the data for every customer.
        		// Example of file structure:
        		// customer 1
        		// account 3
        		// transaction 1
        		// transaction 2
        		// transaction 3
        		// account 2
        		// transaction 1
        		// transaction 2
        		// transaction 3
        		// customer 2
        		// etc.
        		// Each piece of data is separated by a comma.
        		FileReader customerDataFile = new FileReader("customerData.txt");
        		BufferedReader fileReader = new BufferedReader(customerDataFile);
        		// Read every line of the file, while organizing everything to a customer object
        		String line = fileReader.readLine();
        		while (line != null) {
        			// Splits up the line of text, separating everything by commas
        			String[] customerParts = line.split(",");
        			
        			// organize the data into variables (just for readability)
        			int userID = Integer.parseInt(customerParts[0]);
        			int PIN = Integer.parseInt(customerParts[1]);
        			String name = customerParts[2];
        			int numAccounts = Integer.parseInt(customerParts[3]);
        			
        			// Get all the accounts the customer has
        			List<Account> accounts = new ArrayList<>();
        			for (int i = 0; i < numAccounts; i++) {
        				// Remember every object is in its own line
        				line = fileReader.readLine();
        				// Splits up the line of text, separating everything by commas
        				String[] accountParts = line.split(",");
        				
        				// organize the data into variables (just for readability)
        				int accountID = Integer.parseInt(accountParts[0]);
        				double balance = Double.parseDouble(accountParts[1]);
        				AccountType accountType = AccountType.valueOf(accountParts[2]);
        				int numTransactions = Integer.parseInt(accountParts[3]);
        				
        				// Get all the transactions the account has
        				List<Transaction> transactions = new ArrayList<>();
        				for (int j = 0; j < numTransactions; j++) {
        					// Remember every object is in its own line
        					line = fileReader.readLine();
        					// Splits up the line of text, separating everything by commas
        					String[] transactionParts = line.split(",");
        					
        					// organize the data into variables (just for readability)
        					int transactionID = Integer.parseInt(transactionParts[0]);
        					double amount = Double.parseDouble(transactionParts[1]);
        					TransactionType transactionType = TransactionType.valueOf(transactionParts[2]);
        					String date = transactionParts[3];
        					
        					// Create a new transaction object with this data
        					Transaction transaction = new Transaction(transactionID, amount, transactionType, date);
        					// Add this transaction object into the list of transactions that this account has
        					transactions.add(transaction);
        				}
        				// Create a new account object with this data
        				Account account = new Account(accountID, balance, accountType, numTransactions, transactions);
        				// Add this account object into the list of accounts that this customer has
        				accounts.add(account);
        			}
        			// Create a new customer object with this data
        			Customer customer = new Customer(userID, PIN, name, numAccounts, accounts);
        			// Add this customer object into the HashMap of customers
        			customers.put(customer.getUserID(), customer);
        			
        			// Repeat!
        			line = fileReader.readLine();
        		}
        		// Close file when done :)
        		fileReader.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	
        }
        
        // done
        public Message createNewCustomer(List<String> data, Map<Integer, Customer> customers) {
        	//int userID = getNewID(0);
        	int userID = Integer.parseInt(data.get(1));
        	int PIN = Integer.parseInt(data.get(2));
        	String name = data.get(0);
        	System.out.println("inside createNewCustomer");
        	Customer customer = new Customer(userID, PIN, name);
        	customers.put(userID, customer);
        	currentCustomer = customer;
        	//as soon as the new customer is created, write the new map to the file
        	writeCustomersToFile(customers);
        	///immediately read
        	readCustomersFromFile(customers);
        	Message response = new Message();
        	response.status = MsgStatus.Success;
        	response.attachedCustomer = customer;
        	return response;
        }
        
        // done
        public Message removeCustomer(List<String> data, Map<Integer, Customer> customers) {
        	int userID = Integer.parseInt(data.get(0));

        	customers.remove(userID);
        	
        	Message message = new Message(MsgType.RemoveCustomer, MsgStatus.Success, "");
        	return message;
        }
        
        // done
        public Message addAccount(List<String> data, Map<Integer, Customer> customers) {
        	int userID = Integer.parseInt(data.get(0));
        	Customer customer = customers.get(userID);
        	//alt
        	int accountID = getNewID(1);
        	AccountType accountType = AccountType.valueOf(data.get(1));
        	
        	Account account = new Account(accountID, accountType);
        	
        	customer.addAccount(account);
        	customers.get(userID).addAccount(account);
        	//since the map was updated, write, and read the file again
        	writeCustomersToFile(customers);
        	readCustomersFromFile(customers);
        	///make a new response message to send back
        	Message response = new Message();
        	response.attachedCustomer = customers.get(userID);
        	response.status = MsgStatus.Success;
        	return response;
        }
        
        // done
        public Message removeAccount(List<String> data, Map<Integer, Customer> customers) {
        	int userID = Integer.parseInt(data.get(0));
        	int accountID = Integer.parseInt(data.get(1));
        	
        	Customer customer = customers.get(userID);
        	Account account = customer.getAccount(accountID);
        	
        	customer.removeAccount(account);
        	
        	Message message = new Message(MsgType.RemoveAccount, MsgStatus.Success, "");
        	return message;
        }
        
        // done
        public Message deposit(List<String> data, Map<Integer, Customer> customers) {
        	System.out.println("inside deposit");
        	int userID = Integer.parseInt(data.get(0));
        	String acco = data.get(1);
        	AccountType accountType = AccountType.unidentified;
        	if (acco.equals("checking")) {
        		accountType = AccountType.Checking;
        	}
        	else if (acco.equals("savings")) {
        		accountType = AccountType.Savings;
        	}
        	else if (acco.equals("business")) {
        		accountType = AccountType.Business;
        	}
        	
        	double amount = Double.parseDouble(data.get(2));
        	
        	Customer customer = customers.get(userID);
        	//Account account = customer.getAccount(accountID);
        	Message response = new Message();
        	//Account acc = new Account(accountType);
        	int transactionID = getNewID(2);
        	
        	if (customer.getAccount(accountType)!=null) {
        		
        		customer.getAccount(accountType).deposit(amount, transactionID);
        		System.out.println("account has been updated "+customer.getAccount(accountType).getBalance());
            	//update the rest
        		customers.replace(userID, customer);
        		//rewrite and reread the file now
        		writeCustomersToFile(customers);
        		///attach what we need back to the message
        		response.attachedCustomer = customers.get(userID);
        	}
        	
        	return response;
        	
        }
        
        // done
        public Message withdraw(List<String> data, Map<Integer, Customer> customers) {
        	Message mess = new Message();
        	int userID = Integer.parseInt(data.get(0));
        	String acco = data.get(1);
        	
        	AccountType accountType = AccountType.unidentified;
        	if (acco.equals("checking")) {
        		accountType = AccountType.Checking;
        	}
        	else if (acco.equals("savings")) {
        		accountType = AccountType.Savings;
        	}
        	else if (acco.equals("business")) {
        		accountType = AccountType.Business;
        	}
        	double amount = Double.parseDouble(data.get(2));
        	System.out.println("amount trying to get removed "+amount);
        	Customer customer = customers.get(userID);
        	
        	Account account = customer.getAccount(accountType);
        	System.out.println("current balance is "+account.getBalance());
        	
        	
        	int transactionID = getNewID(2);
        	if (account!=null) {
        		account.withdraw(amount, transactionID);
        		System.out.println("new balance is "+account.getBalance());
            	
                mess.attachedAccount = account;
                mess.attachedCustomer = customer;
                mess.status = MsgStatus.Success;
        	}
        	else {
        		mess.status = MsgStatus.Failure;
        	}
        	return mess;
        }
        public Message transferFunds(List<String> data, Map<Integer, Customer> customers) {
        	String acco = data.get(0);
        	AccountType accountType = AccountType.unidentified;
        	if (acco.equals("checking")) {
        		accountType = AccountType.Checking;
        	}
        	else if (acco.equals("savings")) {
        		accountType = AccountType.Savings;
        	}
        	else if (acco.equals("business")) {
        		accountType = AccountType.Business;
        	}
        	
        	double amount = Double.parseDouble(data.get(1));
        	
        	String acco1 = data.get(2);
        	AccountType accountType1 = AccountType.unidentified;
        	if (acco1.equals("checking")) {
        		accountType1 = AccountType.Checking;
        	}
        	else if (acco1.equals("savings")) {
        		accountType1 = AccountType.Savings;
        	}
        	else if (acco1.equals("business")) {
        		accountType1 = AccountType.Business;
        	}
        	int userID = Integer.parseInt(data.get(3));
        	Customer customer = customers.get(userID);
        	Message mess = new Message();
        	
        	Account accountToWithDrawFrom = customer.getAccount(accountType);
        	Account accountToTransferTo = customer.getAccount(accountType1);
        	int transactionID = getNewID(2);
        	int transactionID1 = getNewID(2);
        	if (accountToWithDrawFrom!=null&&accountToTransferTo!=null) {
        		accountToWithDrawFrom.withdraw(amount, transactionID);
        		
        		accountToTransferTo.deposit(amount, transactionID1);
        		
                System.out.println("operation was a success");
                mess.status = MsgStatus.Success;
                return mess;
        	}
        	return mess;
        	
        }
        
        public Message viewTransactions(List<String> data, Map<Integer, Customer> customers) {
            int userID = Integer.parseInt(data.get(0));
            String acco = data.get(1);
            
            AccountType accountType = AccountType.unidentified;
        	if (acco.equals("checking")) {
        		accountType = AccountType.Checking;
        	}
        	else if (acco.equals("savings")) {
        		accountType = AccountType.Savings;
        	}
        	else if (acco.equals("business")) {
        		accountType = AccountType.Business;
        	}
        	
            Customer customer = customers.get(userID);
            Account account = customer.getAccount(accountType);
            Message message = new Message();
            if (account != null) {
            	List<String> strTransactions = account.viewTransactions();
            	message = new Message(MsgType.ViewTransactions, MsgStatus.Success, strTransactions);
                return message;

            }
            
            return message;
        }
        
        
        
        
        // done
        public Message verifyLogin(List<String> data, Map<Integer, Customer> customers) {
        	System.out.println("inside verify login");
        	boolean success = false;
        	int userID = Integer.parseInt(data.get(1));
        	int PIN = Integer.parseInt(data.get(2));
        	Customer newCustomer = null;
        	List<String> oof = null;
        	Message message = new Message(MsgType.Login, MsgStatus.Failure, oof);
        	
        	if (customers.containsKey(userID)) {
            	if (customers.get(userID).getPIN() == PIN) {
            		success = true;
            		//set the new customer to the current customer if it's correct
            		newCustomer = customers.get(userID);
            		///attach this customer to the message
            		message.setStatus(MsgStatus.Success);
            		message.attachedCustomer = newCustomer;
                	
					try {
				  	    FileWriter fw;
						fw = new FileWriter("activeUsers.txt", true);
		          	    BufferedWriter bw = new BufferedWriter(fw);
	            	    bw.write(Integer.toString(userID));
	            	    bw.newLine();
	            	    bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
        	}
        	//System.out.println("returning message");
        	//attach the right object
        	return message;
        }
        
        // done
        // To get a unique userID, the current userID is retrieved from a 
        // text file, is incremented, and then stored back into the text file.
        // This function will get a unique userID, and then return it.
        public int getNewID(int idType) {
        	// IDData[0] holds largest userID
        	// IDData[0] holds largest accountID
        	// IDData[0] holds largest transactionID
    		List<String> IDData = new ArrayList<>();
        	int newID;
        	//System.out.println("inside getNewID loop");
			
        	// Read the IDData file
        	try {
               	FileReader idDataFile = new FileReader("idData.txt");
        		BufferedReader fileReader = new BufferedReader(idDataFile);
        		
        		// Read every line of the file, putting everything into the IDData list.
        		String line = fileReader.readLine();
        		while (line != null) {
        			IDData.add(line);
        			line = fileReader.readLine();
        		}	
        		fileReader.close();
        	} catch (IOException e) {
        	}
        	
        	// Increment the old userID to get a new, unique userID.
    		newID = Integer.parseInt(IDData.get(idType));
    		newID += 1;
    		
    		// Update the IDData list
    		IDData.set(idType, Integer.toString(newID));
        	
        	// Update the idData file with the new IDData list
        	try {
        		FileWriter idDataFile = new FileWriter("idData.txt");
        		BufferedWriter fileWriter = new BufferedWriter(idDataFile);
        		
        		fileWriter.write(IDData.get(0) + "\n");
        		fileWriter.write(IDData.get(1) + "\n");
        		fileWriter.write(IDData.get(2) + "\n");

        		fileWriter.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
            
        	// Return the new userID and you're done.
        	return newID;
        }
    } 
}


































// idk

/*
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
			    	}
			    	
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
					}
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

	}
	
	public Message removeCustomer(List<String> customerData) {
		
		// use hash map?
		return (new Message(MsgType.RemoveCustomer, MsgStatus.Undefined, "Remove Customer"));
		
	}
	
	/*public Message removeAccount(Customer customer, List<String> accountData) {
		
		int accountNumber = (int) accountData.get(0);
		
		customer.closeAccount(accountNumber);
		
		return (new Message(MsgType.RemoveAccount, MsgStatus.Success, "Account Closed"));
		
	}
	
	
	public Message loggingOut(List<String> customerData) {
		
		return (new Message(MsgType.Logout, MsgStatus.Success, "Logged out!"));
		
	}
}
*/





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
			
    		// A HashMap to hold all the Customer's information
    		// including all its accounts and transactions
			
        	Map<Integer, Customer> customers = new HashMap<>();
        	readCustomersFromFile(customers);
			
			try {
		        // create a ObjectOutputStream so we can write data from it.
		        // create a ObjectInputStream so we can read data from it.
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				while((message = (Message)objectInputStream.readObject()) != null) {
					
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
		        	else if (message.getType() == MsgType.UpdateCustomer) {
		        		message = Logout(message, customers);
		        		objectOutputStream.writeObject(message);
		        	}
		        	
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
		        	else if (message.getType()==MsgType.Logout) {
		        		updateCustomer(message.attachedCustomer, message.data, customers);
		        		objectOutputStream.writeObject(message);
		        	}
		        	
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
        	
        	Message message = new Message(MsgType.RemoveCustomer, MsgStatus.Success, null);
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
        	Account account = customer.getAccount(AccountType.Business);
        	
        	customer.removeAccount(account);
        	
        	Message message = new Message(MsgType.RemoveAccount, MsgStatus.Success, null);
        	return message;
        }
        
        // done
        public Message deposit(List<String> data, Map<Integer, Customer> customers) {
        	System.out.println("inside deposit");
        	int userID = Integer.parseInt(data.get(0));
        	String acco = data.get(1);
        	
        	System.out.println(acco);
        	AccountType accountType = AccountType.unidentified;
        	if (acco.equals("Checking")) {
        		accountType = AccountType.Checking;
        		System.out.println("checking");
            	
        	}
        	else if (acco.equals("Savings")) {
        		accountType = AccountType.Savings;
        		System.out.println("Savings");
            	
        	}
        	else if (acco.equals("Business")) {
        		accountType = AccountType.Business;
        		System.out.println("Business");
            	
        	}
        	
        	double amount = Double.parseDouble(data.get(2));
        	
        	Customer customer = customers.get(userID);
        	System.out.println("customer name is "+customer.getName());
        	
        	//Account account = customer.getAccount(accountID);
        	Message response = new Message();
        	//Account acc = new Account(accountType);
        	int transactionID = getNewID(2);
        	
        	if (customer.getAccount(accountType)!=null) {
        		
        		customer.getAccount(accountType).deposit(amount, transactionID);
        		System.out.println("this is from first customer from map "+customer.getAccount(accountType).getBalance());
            	//update the rest
        		//customers.replace(userID, customer);
        		//rewrite and reread the file now
        		//writeCustomersToFile(customers);
        		///attach what we need back to the message
        		response.attachedCustomer = customer;
        		System.out.println("this is from the second customer from map "+customers.get(userID).getAccount(accountType).getBalance());
            	
        		response.attachedAccount = customer.getAccount(accountType);
        		response.status = MsgStatus.Success;
        		System.out.println("this is from the second customer from map "+customers.get(userID).getAccount(accountType).getBalance());
            	
        		return response;
        		
        	}
        	else {
        		
        		response.status = MsgStatus.Failure;
        	}
        	return response;
        	
        }
        
        // done
        public Message withdraw(List<String> data, Map<Integer, Customer> customers) {
        	Message response = new Message();
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
        	if (customer.getAccount(accountType)!=null) {
        		
        		boolean re = customer.getAccount(accountType).withdraw(amount, transactionID);
        		if (re == true) {
        			System.out.println("account has been updated "+customer.getAccount(accountType).getBalance());
        			customers.replace(userID, customer);
            		//rewrite and reread the file now
            		writeCustomersToFile(customers);
            		///attach what we need back to the message
            		response.attachedCustomer = customers.get(userID);
            		response.status = MsgStatus.Success;
        		}
        		
        	}
        	else {
        		
        		response.status = MsgStatus.Failure;
        	}
        	return response;
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
        
        public void updateCustomer(Customer newCustomer, List<String> data, Map<Integer, Customer> customers) {
        	int userID = Integer.parseInt(data.get(0));
        	Integer newUserID = Integer.valueOf(userID);
        	customers.replace(userID, newCustomer);
        	
        	writeCustomersToFile(customers);
        	
        }
        public Message Logout(Message message, Map<Integer, Customer> customers) {
        	updateCustomer(message.attachedCustomer, message.data, customers);
        	writeCustomersToFile(customers);
        	message.status = MsgStatus.Success;
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
        	
        	return message;
        }
        
        // done
        // To get a unique userID, the current userID is retrieved from a 
        // text file, is incremented, and then stored back into the text file.
        // This function will get a unique userID, and then return it.
        public int getNewID(int idType) {
        	// IDData[0] holds largest userID
        	// IDData[1] holds largest accountID
        	// IDData[2] holds largest transactionID
    		List<String> IDData = new ArrayList<>();
        	int newID;
        	
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
        		System.out.println("your file was not found! >:(");
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

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
			
        	Map<Integer, Customer> customers = new HashMap<>();
			
			try {
		        // create a ObjectOutputStream so we can write data from it.
		        // ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		        // create a ObjectInputStream so we can read data from it.
		        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

		        while((message = (Message)objectInputStream.readObject()) != null) {
		        	// do stuff
		        }
			} catch (Exception e) {
				
			}
        }
        
        /* NOT TESTED YET
        public void writeCustomersToFile(Map<Integer, Customer> customers) {
        	try {
        		FileWriter customerDataFile = new FileWriter("customerData.txt");
        		BufferedWriter fileWriter = new BufferedWriter(customerDataFile);
        		
        		for (Map.Entry<Integer, Customer> customer : customers.entrySet()) {
        			fileWriter.write(customer.toString());
        		}
        		fileWriter.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
		*/

        public void readCustomersFromFile(Map<Integer, Customer> customers) {
        	try {
        		// Open file that holds all the data for every customer.
        		// Example of file structure:
        		// customer 1
        		// account 1
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
        			String address = customerParts[3];
        			int numAccounts = Integer.parseInt(customerParts[4]);
        			
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
        				String accountType = accountParts[2];
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
        					String transactionType = transactionParts[2];
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
        			Customer customer = new Customer(userID, PIN, name, address, numAccounts, accounts);
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
    } 
}

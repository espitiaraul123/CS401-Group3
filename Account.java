import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account implements Serializable{
	private int accountID;
	private double balance;
	private AccountType accountType; // account type (e.g., savings, checking, etc.)
	private int numTransactions;
	private List<Transaction> transactions;
	
	// done
	// Constructor
	Account(AccountType accountType) {
		//this.accountID = accountID;
		this.balance = 0.00;
		this.accountType = accountType;
		this.numTransactions = 0;
		this.transactions = new ArrayList<>();
	}
	// done
	// This constructor is just used for testing.
	Account(int accountID, double balance, AccountType accountType, int numTransactions, List<Transaction> transactions) {
		this.accountID = accountID;
		this.balance = balance;
		this.accountType = accountType;
		this.numTransactions = numTransactions;
		this.transactions = transactions;
	}
	
	// done
	// Getters
	public int getAccountID() {
		return accountID;
	}
	public double getBalance() {
		return balance;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public int getNumTransactions() {
		return numTransactions;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	// done
	// Setters
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public void setNumTransactions(int numTransactions) {
		this.numTransactions = numTransactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	// done
	// toString()
	// Used for reading and writing to the database(text file)
	public String toString() {
		String accountAsString = "";
		
		accountAsString = accountAsString.concat(Integer.toString(accountID) + ","
													+ Double.toString(balance) + ","
													+ accountType.toString() + ","
													+ Integer.toString(numTransactions) + "\n");
		
		// Include all the transaction information
		for(int i = 0; i < numTransactions; i++) {
			Transaction transaction = transactions.get(i);
			accountAsString = accountAsString.concat(transaction.toString());
		}

		return accountAsString;
	}

	// done
	public void deposit(double amount, int transactionID) {
		balance += amount;
		System.out.println("new balance is "+balance);
		Transaction transaction = new Transaction(transactionID, amount, TransactionType.DEPOSIT, java.time.LocalDate.now().toString());
		transactions.add(transaction);
		numTransactions++;
	}
	// done
	public boolean withdraw(double amount, int transactionID) {
		if (balance < amount) {
			return false;
		}
		
		balance -= amount;
		
		Transaction transaction = new Transaction(transactionID, amount, TransactionType.WITHDRAW, java.time.LocalDate.now().toString());
		transactions.add(transaction);
		numTransactions++;
		
		return true;
	}
	

	public List<String> viewTransactions() {
		int count = 0;
		List<String> transactionHistory = new ArrayList<String>();
		// traverses the transactions in reverse order to mention the most recent transaction first
		for(int i = numTransactions - 1; i >= 0; i--) {
			
			if(count > 5) {
				break;
			}
		
			transactionHistory.add(transactions.get(i).toString());
			count++;
		}
		
		return transactionHistory;
	}
	
	/* idk yet
	//get a transaction
	public String getTransactionsByDate(Date date) {
		String transactionsByDate = "";

		for(int i = 0; i < transactions.size(); i++) {

			if(transactions.get(i).getDate().equals(date)) {
				transactionsByDate += transactions.get(i).toString();
			}
		}

		return transactionsByDate;
	}
	
	// getter for the whole transaction history
	public String transactionHistory() {
		String transactionHistory = "";
		for(int i = 0; i < transactions.size(); i++) {
			transactionHistory += (transactions.get(i).toString() + "\n");
			
		}
		return transactionHistory;
		
	}
	

	//Method to add a transaction to transaction array    
	public void addTransaction(ArrayList<Transaction> transactions, Transaction transaction) {
		// adds a transaction (i.e., a deposit or a withdrawal) to the list of transactions
		numTransactions++;
		transactions.add(transaction); 

	}

	//Method to deposit money into the account
	public void deposit(double amount, TransactionType type) {
		balance += amount; // adds the deposited amount to the account balance
		addTransaction(transactions, new Transaction(amount, type)); // adds a deposit transaction to the list of transactions
	}

	//Method to withdraw money from the account
	public boolean withdraw(double amount, TransactionType type) {
		if (amount <= balance && balance > 0) { // if the account has sufficient funds for the withdrawal
			balance -= amount;
			addTransaction(transactions, new Transaction(amount*-1, type)); // adds a deposit transaction to the list of transactions
			return true; // returns true to indicate a successful withdrawal
			
		} else { // if the account does not have enough funds for the withdrawal
			return false; // returns false to indicate a failed withdrawal
		}
	}//addTransaction(-amount);

	//Method to transfer money from this account to another account
	public boolean transfer(double amount, Account accountReceipient) {
		// withdraw funds from senderAccount and deposit in the receiver accounts
		bool tranfered = withdraw(amount, TransactionType.TRANSFER_OUT); //sender
		accountReceipient.deposit(amount, TransactionType.TRANSFER_IN); // receiver 

		if (transfered) {
			return true;


		} else { // if the account does not have enough funds for the transfer
			return false; // returns false to indicate a failed transfer
		}
	}
	*/
}


import java.util.ArrayList;
import java.util.Date;

public class Account {
	private String name; // customer name
	private int accountNumber; // account number
	private AccountType accountType; // account type (e.g., savings, checking, etc.)
	private ArrayList<Transaction> transactions = null; // list of transactions
	private double balance; // account balance
	private static int count = 10000;
	private Date date;


	//Constructor
	public Account(String name, AccountType accountType, double initialDeposit) {
		this.name = name;
		this.accountNumber = count++;
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>(); // creates an empty ArrayList of transactions
		this.balance = initialDeposit; // sets the balance to zero upon account creation
	}

	//Getter to retrieve Customer's name
	public String getName() {
		return name;
	}

	//Getter to retrieve account number
	public int getAccountNumber() {
		return accountNumber;
	}

	//Getter to retrieve account type
	public AccountType getAccountType() {
		return accountType;
	}

	//Getter to retrieve transaction array
	public ArrayList<Transaction> getTransactions(){
		return this.transactions; // returns the list of transactions
	}

	//Getter to retrieve Account balance
	public double getBalance() {
		return balance;
	}

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
			transactionHistory += transactions.get(i).toString();
			
		}
		return transactionHistory;
		
	}
	

	//Method to add a transaction to transaction array    
	public void addTransaction(ArrayList<Transaction> transactions, Transaction transaction) {
		transactions.add(transaction); // adds a transaction (i.e., a deposit or a withdrawal) to the list of transactions
		//balance += amount; // updates the account balance with the amount of the transaction

	}

	//Method to deposit money into the account
	public void deposit(double amount) {
		balance += amount; // adds the deposited amount to the account balance
		addTransaction(transactions, new Transaction(amount, TransactionType.DEPOSIT)); // adds a deposit transaction to the list of transactions
	}

	//Method to withdraw money from the account
	public boolean withdraw(double amount) {
		if (amount <= balance && balance > 0) { // if the account has sufficient funds for the withdrawal
			balance -= amount;
			addTransaction(transactions, new Transaction(amount*-1, TransactionType.WITHDRAW)); // adds a deposit transaction to the list of transactions
			return true;

			//addTransaction(-amount); // adds a withdrawal transaction to the list of transactions (with a negative amount)
			//return true; // returns true to indicate a successful withdrawal
		} else { // if the account does not have enough funds for the withdrawal
			System.out.println("Sorry your account does not have enough funds"); // prints an error message
			return false; // returns false to indicate a failed withdrawal
		}
	}//addTransaction(-amount);

	//Method to transfer money from this account to another account
	public boolean transfer(double amount, Account otherAccount) {
		if (amount <= balance && balance > 0.00) { // if the account has sufficient funds for the transfer
			// adds a transfer transaction to the list of transactions (with a negative amount)
			addTransaction(transactions, new Transaction(amount*-1, TransactionType.TRANSFER_OUT));
			// adds a transfer transaction to the other account's list of transactions (with a positive amount)
			addTransaction(otherAccount.getAllTransactions(), new Transaction(amount, TransactionType.TRANSFER_IN)); 
			return true; // returns true to indicate a successful transfer


		} else { // if the account does not have enough funds for the transfer
			System.out.println("Sorry that account does not exist"); // prints an error message
			return false; // returns false to indicate a failed transfer
		}
	}

	public String toString() {
		return(name + 
				"," + accountNumber + 
				"'," + accountType +
				"," + balance +
				"," + transactions.size());
	}





}


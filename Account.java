import java.util.ArrayList;

public class Account {
	private String name; // customer name
	private int accountNumber; // account number
	private AccountType accountType; // account type (e.g., savings, checking, etc.)
	private ArrayList<Transaction> transactions; // list of transactions
	private double balance; // account balance
	
	//Constructor
	public Account(String name, int accountNumber, AccountType accountType) {
		this.name = name;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>(); // creates an empty ArrayList of transactions
		this.balance = 0.0; // sets the balance to zero upon account creation
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
	public ArrayList<Double> getTransaction(){
		return transactions; // returns the list of transactions
	}
	
	//Getter to retrieve Account balance
	public double getBalance() {
		return balance;
	}
	
	//Method to add a transaction to transaction array
	public void addTransaction(double amount) {
		transactions.add(amount); // adds a transaction (i.e., a deposit or a withdrawal) to the list of transactions
		balance += amount; // updates the account balance with the amount of the transaction
	}
	
	//Method to deposit money into the account
	public void deposit(double amount) {
		balance += amount; // adds the deposited amount to the account balance
		transactions.add(new Transaction(transactions.size() + 1, amount, TransactionType.DEPOSIT)); // adds a deposit transaction to the list of transactions
	}
	
	//Method to withdraw money from the account
	public boolean withdraw(double amount) {
		if (amount <= balance) { // if the account has sufficient funds for the withdrawal
			addTransaction(-amount); // adds a withdrawal transaction to the list of transactions (with a negative amount)
			return true; // returns true to indicate a successful withdrawal
		} else { // if the account does not have enough funds for the withdrawal
			System.out.println("Sorry your account does not have enough funds"); // prints an error message
			return false; // returns false to indicate a failed withdrawal
		}
	}
		
	//Method to transfer money from this account to another account
	public boolean transfer(double amount, Account otherAccount) {
		if (amount <= balance) { // if the account has sufficient funds for the transfer
			addTransaction(-amount); // adds a transfer transaction to the list of transactions (with a negative amount)
			otherAccount.addTransaction(amount); // adds a transfer transaction to the other account's list of transactions (with a positive amount)
			return true; // returns true to indicate a successful transfer
		} else { // if the account does not have enough funds for the transfer
			System.out.println("Sorry that account does not exist"); // prints an error message
			return false; // returns false to indicate a failed transfer
		}
	}
}

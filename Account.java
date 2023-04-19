import java.util.ArrayList;

public class Account {
	private String name;
	private int accountNumber;
	private AccountType accountType;
	private ArrayList<Double> transactions;
	private double balance;
	
	//Constructor
	public Account(String name, int accountNumber, AccountType accountType) {
		this.name = name;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.transactions = new ArrayList<Double>();
		this.balance = 0.0;
		
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
		return transactions;
	}
	
	//Getter to retrieve Account balance
	public double getBalance() {
		return balance;
	}
	
	//Method to add a transaction to transaction array
	public void addTranscation(double amount) {
		transactions.add(amount);
		balance += amount;
	}
	
}

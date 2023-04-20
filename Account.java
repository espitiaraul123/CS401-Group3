import java.util.ArrayList;

public class Account {
	private String name;
	private int accountNumber;
	private AccountType accountType;
	private ArrayList<Transaction> transactions;
	private double balance;
	
	//Constructor
	public Account(String name, int accountNumber, AccountType accountType) {
		this.name = name;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
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
	public void addTransaction(double amount) {
		transactions.add(amount);
		balance += amount;
	}
	
	//Method to deposit money into the account
		public void deposit(double amount) {
			balance += amount;
			transactions.add(new Transaction(transactions.size() + 1, amount, TransactionType.DEPOSIT));
		}
	
	//Method to withdraw money into the account
		public boolean withdraw(double amount) {
			if (amount <= balance) {
				addTransaction(-amount);
				return true;
			} else {
				System.out.println("Sorry your account does not have enough funds");
				return false;
			}
		}
		
	//Method to transfer money from this account to another account
	public boolean transfer(double amount, Account otherAccount) {
		if (amount <= balance) {
			addTransaction(-amount);
			otherAccount.addTransaction(amount);
			return true;
		} else {
			System.out.println("Sorry that account does not exist");
			return false;
		}
	}
}

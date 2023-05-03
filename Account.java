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
	
	
	// Constructor
	Account(int Accountid,AccountType accountType) {
		this.accountID = accountID;
		this.balance = 0.00;
		this.accountType = accountType;
		this.numTransactions = 0;
		this.transactions = new ArrayList<>();
	}
	
	// This constructor is just used for testing.
	Account(int accountID, double balance, AccountType accountType, int numTransactions, List<Transaction> transactions) {
		this.accountID = accountID;
		this.balance = balance;
		this.accountType = accountType;
		this.numTransactions = numTransactions;
		this.transactions = transactions;
	}
	
	
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
	// done
	public void deposit(double amount, int transactionID) {
		balance += amount;
		System.out.println("new balance is "+balance+" inside deposit");
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

	
	
}


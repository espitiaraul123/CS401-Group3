import java.io.*;
import java.util.*;

public class Customer implements Serializable{
	
	private int userID;
	private int PIN;
	private String name;
	private int numAccounts;
	private List<Account> accounts;
	
	// A Customer must be initialized with user input
	
	// done
	// Constructor
	Customer(int userID, int PIN, String name) {
		this.userID = userID;
		this.PIN = PIN;
		this.name = name;
		this.numAccounts = 0;
		this.accounts = new ArrayList<>();
	}
	// done
	// This constructor is just used for testing.
	Customer(int userID, int PIN, String name, int numAccounts, List<Account> accounts) {
		this.userID = userID;
		this.PIN = PIN;
		this.name = name;
		this.numAccounts = numAccounts;
		this.accounts = accounts;
	}
	
	// done
	// Getters
	public int getUserID() {
		return userID;
	}
	public int getPIN() {
		return PIN;
	}
	public String getName() {
		return name;
	}
	public int getNumAccounts() {
		return numAccounts;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	
	// done
	// Setters
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setPin(int PIN) {
		this.PIN = PIN;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void getNumAccounts(int numAccounts) {
		this.numAccounts = numAccounts;	
	}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	// done
	// toString()
	// Used for reading and writing to the database(text file)
	public String toString() {
		String customerAsString = "";
		
		customerAsString = customerAsString.concat(Integer.toString(userID) + ","
													+ Integer.toString(PIN) + ","
													+ name + ","
													+ Integer.toString(numAccounts) + "\n");
		
		// Include all the account information
		for(int i = 0; i < numAccounts; i++) {
			Account account = accounts.get(i);
			customerAsString = customerAsString.concat(account.toString());
		}
		
		return customerAsString;
	}
	public Message lookForAccount(AccountType accountType) {
		Message mess = new Message();
		for (Account acc : accounts) {
    		if (accountType == acc.getAccountType()) {
    			mess.attachedAccount = acc;
    			mess.status = MsgStatus.Success;
    			return mess;
    		}
		}
		mess.setStatus(MsgStatus.Failure);
		return mess;
	}
	// done
	// Returns an account, which is asked for by its ID
	public Account getAccount(AccountType accountType) {
		Message mess = lookForAccount(accountType);
		if (mess.status == MsgStatus.Success) {
			return mess.attachedAccount;
		}
		else
			return null;
		
	}
	
	// done
	// Removes an account
	public void removeAccount(Account account) {
		accounts.remove(account);
		numAccounts--;
	}
	
	// done
	// Add an account to the customer (used for testing)
	public void addAccount(Account newAccount) {
		numAccounts++;
		accounts.add(newAccount);
	}
	
	public List<String> viewAccounts() {
		List<String> allAccounts = new ArrayList<String>();
		// traverses the accounts made for this customer
		for(int i = numAccounts - 1; i >= 0; i--) {
	
			allAccounts.add(accounts.get(i).toString());
			
		}
		
		return transactionHistory;
	}
	

	/* idk yet
	// add a new account to the under the Customer's name
	public Account addAccount(double initialDeposit, AccountType type) {
		Account newAcct = new Account(name, type, initialDeposit);
		accounts.add(newAcct);
		numberOfAccounts++;
		return newAcct;

	}

	public void closeAccount(int accountID) {
		
		Account closeAcct = findAccount(accountID, name);
		accounts.remove(closeAcct);
		numberOfAccounts--;


	}

	public void transferFunds(int acct1ID, int acct2ID, double amount) {

		Account accountFrom = findAccount(acct1ID, name);
		Account accountTo = findAccount(acct2ID, name);
		accountFrom.transfer(amount, accountTo);

	}
	*/
}

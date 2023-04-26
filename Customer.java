import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class Customer {

	private int numberOfAccounts;
	private String name;
	private String UserID;
	private String pin;
	private String address;
	private String email;
	private ArrayList<Account> accounts = null; // list of transactions

	private static int count = 0;
	String name, int numberOfAccounts,

	//Constructor to initialize instance variables
	public Customer(String pin, String name, int numberOfAccounts, String address) {
		//Setting the Customer's attributes
		this.numberOfAccounts = accounts.size();
		this.name = name;
		this.UserID = count++;
		this.pin = pin;
		this.address = address;
	}

	//Setter to modify number of accounts
	public void setNumberOfAccounts(int numberOfAccounts) {
		this.numberOfAccounts = accounts.size();	
	}

	//Setter to modify Customer's name
	public void setName(String name) {
		this.name = name;
	}

	//Setter to modify Customer's UserID
	public void setUserID(String UserID) {
		this.UserID = UserID;
	}

	//Setter to modify Customer's name
	public void setPin(String pin) {
		this.pin = pin;
	}

	//Setter to modify Customer's name
	public void setAddress(String address) {
		this.address = address;
	}

	//setter to set the email address of the customer : contact info
	public void setEmail(String email) {
		this.email = email;

	}


	// get account info in a toString
	public String getAllAccountsInfo(){
		String accts = "";
		for(int i = 0; i < accounts.size(); i++) {

			accts += (accounts.get(i).toString() + "\n");

		}

		return accts;

	}


	// finding the accounts
	public Account findAccount(int acctId, String name) {
		Account acctFound = null;

		for(int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i).getAccountNumber() == acctId && accounts.get(i).getName().equals(name)) {

				acctFound = accounts.get(i);
				break;
			}

		}

		return acctFound;
	}

	// add a new account to the under the Customer's name
	public void addAccount(double initialDeposit, AccountType type) {
		accounts.add(new Account(name, type, initialDeposit));

	}

	public void closeAccount(int accountID) {

		Account closeAcct = findAccount(accountID, name);
		accounts.remove(closeAcct);

	}

	public void transferFunds(int acct1ID, int acct2ID, double amount) {

		Account accountFrom = findAccount(acct1ID, name);
		Account accountTo = findAccount(acct2ID, name);
		accountFrom.transfer(amount, accountTo);

	}


	// This method saves a list of transactions to a file
	public static void save(String fileName) {
		try {
			FileWriter filename = new FileWriter(fileName);
			// Loop through the transactions and write them to the file
			for (Transaction t: transactions) {
				writer.write(t.toString() + "\n");
			}
			filename.close();
		} catch (IOException e) {
			System.out.println("An error occurred while writing the transaction to the file.");
		}
	}



	//Getter to retrieve number of accounts
	public int getnumberOfAccounts() {
		return numberOfAccounts;
	}



	//Getter to retrieve Customer's name
	public String getName() {
		return name;
	}



	//Getter to retrieve Customer's UserID
	public String getUserID() {
		return UserID;
	}



	//Getter to retrieve Customer's name
	public String getPin() {
		return pin;
	}
	
	//getter for email: contact info
	public String getEmail() {
		return email;

	}


	//Getter to retrieve Customer's name
	public String getAddress() {
		return address;
	}
	
	public String toString() {
		String allAcctsInfo = getAllAccountsInfo();
		
		return (name + "," + userID + "," + allAcctsInfo);
	
		
	}


}

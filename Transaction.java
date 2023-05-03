
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;


public class Transaction implements Serializable{
	private int transactionID;
	private double amount;
	private TransactionType transactionType;
	private String date;

	// done
	// Constructor
	Transaction(int transactionID, double amount, TransactionType transactionType, String date) {
		this.transactionID = transactionID;
		this.amount = amount;
		this.transactionType = transactionType;
		this.date = date;
	}
	
	// Getters
	public int getTransactionID() {
		return transactionID;
	}
	public double getAmount() {
		return amount;
	}
	public TransactionType transactionType() {
		return transactionType;
	}
	public String getDate() {
		return date;
	}
	
	// done
	// toString()
	// Used for reading and writing to the database(text file)
	public String toString() {
		String transactionAsString = "";
		
		transactionAsString = transactionAsString.concat(Integer.toString(transactionID) + ","
													+ Double.toString(amount) + ","
													+ transactionType.toString() + ","
													+ date + "\n");
		return transactionAsString;
	}
}



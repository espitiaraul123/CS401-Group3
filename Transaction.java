
import java.io.PrintStream;
import java.util.Date;


public class Transaction {
	private int transactionID;
	private double amount;
	private TransactionType type;
	private static int count = 0;
	private static Date date = null;

	//private String name;

	// Constructor to initialize transactionID, amount and type
	public Transaction(double amount, TransactionType type) {
		this.transactionID = count++;
		this.amount = amount;
		this.type = type;
		this.date = new Date();
	}

	// getter gets date of transaction
	public Date getDate() {
		return date;
	}


	// Getter method to return transactionID
	public int getId() {
		return transactionID;
	}

	// Getter method to return transaction amount
	public double getAmount() {
		return amount;
	}

	// Getter method to return transaction type
	public TransactionType getType() {
		return type;
	}


	/*
// maybe works better in the account class
	// This method saves a list of transactions to a file
	public static void saveTransactions(List<Transaction> transactions, String fileName) {
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
	}*/

	// This method returns a string representation of the transaction
	@Override
	public String toString() {
		String transactionType = "";
		switch (type) {
		case DEPOSIT:
			transactionType = "Deposit";
			break;
		case WITHDRAW:
			transactionType = "Withdraw";
			break;
		case TRANSFER_IN:
			transactionType = "Transfer";
			break;
		}

		String strDate = String.format("%Ta %Tb %Td %TT %TZ %TY", date);

		// return a formatted string with transaction ID, type, and amount
		return String.format("Date: %s \nID: %d, \n%s: %.2f", strDate, transactionID, transactionType, amount);


		//return String.format("ID: %d, %s: %.2f", transactionID, transactionType, amount);
	}
}



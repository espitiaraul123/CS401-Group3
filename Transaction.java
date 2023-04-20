 public class Transaction {
	private int transactionID;
	private double amount;
	private TransactionType type;
	
// Constructor to initialize transactionID, amount and type
public Transaction(int id, double amount, TransactionType type) {
	this.transactionID = transactionID;
	this.amount = amount;
	this.type = type;
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
	
	// This method saves a list of transactions to a file
	public static void saveTransactions(List<Transaction> transactions, Strig fileName) {
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
		
		// return a formatted string with transaction ID, type, and amount
		return String.format("ID: %d, %s: %.2f", id, transactionType, amount);
	 }
}

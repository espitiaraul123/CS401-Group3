public class Transaction {
	private int transactionID;
	private double amount;
	private TransactionType type;
	
	public Transaction(int id, double amount, TransactionType type) {
		this.transactionID = transactionID;
		this.amount = amount;
		this.type = type;
	}
	
	public int getId() {
		return transactionID;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public TransactionType getType() {
		return type;
	}
	
	public static void saveTransactions(List<Transaction> transactions, Strig fileName) {
		try {
			FileWriter filename = new FileWriter(fileName);
			for (Transaction t: transactions) {
				writer.write(t.toString() + "\n");
			}
			filename.close();
		} catch (IOException e) {
			System.out.println("An error occured while writing the transaction to the file.");
		}
	}
	
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
		case TRANSFER:
			transactionType = "Transfer";
			break;
		}
		return String.format("ID: %d, %s: %.2f", id, transactionType, amount);
	}
}

public class Customer {
	
	private int numberOfAccounts;
	private String name;
	private String UserID;
	private String pin;
	private String address;
	
	//Constructor to initialize instance variables
	public Customer(int numberOfAccounts, String name, String UserID, String pin, String address) {
		//Setting the Customer's attributes
		this.numberOfAccounts = numberOfAccounts;
		this.name = name;
		this.UserID = UserID;
		this.pin = pin;
		this.address = address;
	}
	
	//Getter to retrieve number of accounts
	public int getnumberOfAccounts() {
		return numberOfAccounts;
	}
	
	//Setter to modify number of accounts
	public void setNumberOfAccounts(int numberOfAccounts) {
		this.numberOfAccounts = numberOfAccounts;	
	}
	
	//Getter to retrieve Customer's name
	public String getName() {
		return name;
	}
	
	//Setter to modify Customer's name
	public void setName(String name) {
		this.name = name;
	}
	
	//Getter to retrieve Customer's UserID
	public String getUserID() {
		return UserID;
	}
	
	//Setter to modify Customer's UserID
	public void setUserID(String UserID) {
		this.UserID = UserID;
	}
	
	//Getter to retrieve Customer's name
	public String getPin() {
		return pin;
	}
	
	//Setter to modify Customer's name
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	//Getter to retrieve Customer's name
	public String getAddress() {
		return address;
	}
	
	//Setter to modify Customer's name
	public void setAddress(String address) {
		this.address = address;
	}
}

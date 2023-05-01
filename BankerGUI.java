import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BankerGUI extends JFrame implements MouseListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Banker GUI");
    private JButton createCustomerButton, createAccountButton, removeButton, depositButton, withdrawButton, transferButton, transactionButton, logoutButton;
    private JButton logIntoCustomerAccountButton, viewAllAccountsButton;
    private JPanel buttonPanel, labelPanel;
    private JLabel nameLabel, balanceLabel;
    private JList<String> customerList;
    
    //current customer being looked at
    private Customer customer;
    String[] data;
    String fullname;
    int numOfAccounts;
    boolean checkingAccountExists;
    boolean savingsAccountExists;
    boolean businessAccountExists;
    int balanceOfChecking;
    int balanceOfSavings;
    int balanceOfBusiness;
    
    
    public BankerGUI() throws UnknownHostException, IOException {
        //Set a custom Frame size 
    	Socket socket = new Socket("localhost", 1234);
    	frame.setSize(1000, 500);
    	
        //Create Panel
        buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));

        //Create Buttons
        createCustomerButton = new JButton("Create new customer");
        createAccountButton = new JButton("Create Account");
        logIntoCustomerAccountButton = new JButton("Log into customer Account");
        removeButton = new JButton("Remove Account");
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        transferButton = new JButton("Transfer");
        transactionButton = new JButton("View Transactions");
        logoutButton = new JButton("Logout");
        viewAllAccountsButton = new JButton("View all accounts");

        //Add Buttons to Panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,0));
        buttonPanel.add(createCustomerButton);
        buttonPanel.add(createAccountButton);
        buttonPanel.add(logIntoCustomerAccountButton);
        buttonPanel.add(viewAllAccountsButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(transactionButton);
        buttonPanel.add(logoutButton);

        //Set dimension of the button size
        createCustomerButton.setPreferredSize(new Dimension(200, 60));
        createAccountButton.setPreferredSize(new Dimension (200,60));
        logIntoCustomerAccountButton.setPreferredSize(new Dimension (200, 60));
        removeButton.setPreferredSize(new Dimension(200, 60));
        depositButton.setPreferredSize(new Dimension(200, 60));
        withdrawButton.setPreferredSize(new Dimension(200, 60));
        transferButton.setPreferredSize(new Dimension(200, 60));
        transactionButton.setPreferredSize(new Dimension(200, 60));
        logoutButton.setPreferredSize(new Dimension(200, 60));
        viewAllAccountsButton.setPreferredSize(new Dimension(200, 60));


        //Add Label and button panel
        labelPanel = new JPanel(new GridLayout(2,1));
        nameLabel = new JLabel("Customer Name: ");
        balanceLabel = new JLabel("Balance: $");

        //Add JList for customers
        String[] accounts = {"checking account", "savings account", "business account"};
        customerList = new JList<>(accounts);
        customerList.setVisibleRowCount(3);
        customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
       
        
        //Add components to label panel
        labelPanel.add(nameLabel);
        labelPanel.add(balanceLabel);
        labelPanel.add(new JScrollPane(customerList));
        
        /*customerList.addListSelectionListener (
        		new ListSelectionListener() {
        			public void valueChanged(ListSelectionEvent e) {
        			int index = customerList.getSelectedIndex();
        			///get the account
        			///if the number of accounts is less than the number selected,
        			///the account doesn't exist.
        			///look for the account
        			boolean found = false;
        			for (Account current : customer.getAccounts()) {
        				if (current.getAccountType().equals(AccountType.Checking) && index == 0) {
        					///show the account...
        					found = true;
        					JOptionPane.showMessageDialog(frame, "You have $"+current.getBalance()+" in your checking account");
        					
        				}
        				else if (current.getAccountType().equals(AccountType.Savings) && index == 1) {
        					///show the account...
        					found = true;
        					JOptionPane.showMessageDialog(frame, "You have $"+current.getBalance()+" in your savings account");
        					
        				}
        				else if (current.getAccountType().equals(AccountType.Business) && index == 2) {
        					///show the account...
        					found = true;
        					JOptionPane.showMessageDialog(frame, "You have $"+current.getBalance()+" in your business account");
        					
        				}
        				
        			}
        		
        			if (found == false) {
        				///tell the user that the customer doesn't have this banking account, so make one
        				JOptionPane.showMessageDialog(frame, "The account you are looking for has not been made. Please create a bank account of this type.");
        			}
        		}
        	}
        );*/
        
       

        //Add Panel to Frame
        frame.add(labelPanel);
        frame.add(buttonPanel);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set bounds for label panel and button panel
        labelPanel.setBounds(50, 50, 200, 300);
        buttonPanel.setBounds(300, 50, 500, 500);
        
        /*customerList.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() == 2) {
        			int index = customerList.getSelectedIndex();
        		}
        	}
        });*/
        createCustomerButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		System.out.println("creating new customer");
				// TODO Auto-generated method stub
				///build a new Customer message to send to the server
	        	List<String> newCustomerData = new ArrayList<String>();
	        	
	        	String pin = (JOptionPane.showInputDialog(frame, "Please enter your pin"));
	        	newCustomerData.add(pin);

        		String fullname = JOptionPane.showInputDialog(frame, "Please enter your name");
        		newCustomerData.add(fullname);
        		//newCustomerData.add()
	        	//String userID = (JOptionPane.showInputDialog(frame, "please enter your userID"));
	        	//String[] arrayofStrings = {userID, pin, fullname};
	        	
	        	//Message newMessage = new Message(MsgType.Login, MsgStatus.Undefined,arrayofStrings);
	        	
        		Message message = new Message(MsgType.NewCustomer, MsgStatus.Undefined, newCustomerData);
	        	
	        	//send this to the server
	        	// create a ObjectOutputStream so we can write data from it.
		        try {
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					//ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
					oos.writeObject(message);
					Message newMessage = (Message)ois.readObject();
			        //System.out.println("login message fetched back");
			        
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        // create a ObjectInputStream so we can read data from it.
		        //passing message to server
		        if (newMessage.status == MsgStatus.Success) {
		        	JOptionPane.showMessageDialog(frame, "Successfully created and written new customer");
		        }
		        else {
		        	JOptionPane.showMessageDialog(frame, "Customer creation was unsuccessful");
		        }
		        
			}
        	
        	
        });
        logIntoCustomerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(frame, "logging into customer profile");
				
                // Code to execute when createButton is clicked
            	List<String> loginData = new ArrayList<String>();
            	//String fullname = JOptionPane.showInputDialog(frame, "Please enter your name");
	        	String userID = (JOptionPane.showInputDialog(frame, "please enter your userID"));
	        	String pin = (JOptionPane.showInputDialog(frame, "Please enter your pin"));
	        	//list<String> arrayofStrings = {userID, pin, fullname};
	        	
	        	loginData.add(userID);
	        	loginData.add(pin);
	        	
	        	Message newMessage = new Message(MsgType.Login, MsgStatus.Undefined,loginData);
	        	
            	//send the message to the server
            	try {
            		
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
					oos.writeObject(newMessage);
					Message message = (Message)ois.readObject();
					//JOptionPane.showMessageDialog(frame,"got message");
					
			        
			        
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	// create a ObjectInputStream so we can read data from it.
		        //passing message to server
            	if (message.status == MsgStatus.Success) {
		        	JOptionPane.showMessageDialog(frame, "Successfully logged in and fetched customer");
		        	//add data to the array
		        	/*String fullname;
    int numOfAccounts;
    boolean checkingAccountExists;
    boolean savingsAccountExists;
    boolean businessAccountExists;
    int balanceOfChecking;
    int balanceOfSavings;
    int balanceOfBusiness;
    */
		        	
		        	
		        	/*fullname = newMessage.data[0];
		        	numOfAccounts = Integer.parseInt(data[1]);
		        	if (newMessage.data[2].equals("true")) {
		        		checkingAccountExists = true;
		        	}
		        	else {
		        		checkingAccountExists = false;
		        	}
		        	if (newMessage.data[3].equals("true")) {
		        		savingsAccountExists = true;
		        	}
		        	else {
		        		savingsAccountExists = false;
		        	}
		        	if (newMessage.data[3].equals("true")) {
		        		businessAccountExists = true;
		        	}
		        	else {
		        		businessAccountExists = false;
		        	}
		        	
		        	//initialize data
		        	 
		        	nameLabel.setText(newMessage.data[0]);
		        	///start the AtM for the customer
		        	//ATM atm = new ATM(customer);
		        	 */
		        	
		        }
		        else {
		        	JOptionPane.showMessageDialog(frame, "Customer login was unsuccessful");
		        }
           	}
        });
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	List<String> newAccountData = new ArrayList<String>();
            	
            	String userID = JOptionPane.showInputDialog(frame, "Enter the UserID to create an account: ");
            	newAccountData.add(userID);
            	
            	//System.out.println("creating a new account");
                // Code to execute when createButton is clicked
                String accountType = JOptionPane.showInputDialog(frame, "Account Type: ");
                newAccountData.add(accountType);
                
                String initialDeposit = JOptionPane.showInputDialog(frame, "Enter your initiali deposit [Minimum of $25.00]: ");
                newAccountData.add(intitalDeposit);
                
                try {
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					Message message = new Message(MsgType.NewAccount, MsgStatus.Undefined, newAccountData)
					
					oos.writeObject(message);
					
					JOptionPane.showMessageDialog(frame, "New Account Created!");
                	
                	
                }
                
                
                /*if (accountType.equals("Checking")) {
                	checkingAccountExists = true;
                	//numOfAccounts++;
                }
                else if (accountType.equals("Savings")) {
                	savingsAccountExists = true;
                	//numOfAccounts++;
                }
                else if (accountType.equals("Business")) {
                	businessAccountExists = true;
                	//numOfAccounts++;
                }
                String initialDeposit = JOptionPane.showInputDialog(frame, "Initial Deposit: ");
                double newDeposit = Integer.parseInt(initialDeposit);
                Message newMessage = null;
                try {
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
					if(accountType.equals("checking")) {
	                	String []oof = {fullname,"Checking"};
	                	newMessage = new Message(MsgType.NewAccount, MsgStatus.Undefined, oof);
	                	oos.writeObject(newMessage);
						newMessage = (Message)ois.readObject();
						JOptionPane.showMessageDialog(frame,"got message");
	                	
	                }
	                else if(accountType.equals("savings")) {
	                	String []oof = {fullname,"Savings"};
	                	newMessage = new Message(MsgType.NewAccount, MsgStatus.Undefined, oof);
	                	oos.writeObject(newMessage);
						newMessage = (Message)ois.readObject();
						JOptionPane.showMessageDialog(frame,"got message");
	                	
	                }
	                else if(accountType.equals("business")) {
	                	String []oof = {fullname,"Business"};
	                	newMessage = new Message(MsgType.NewAccount, MsgStatus.Undefined, oof);
	                	oos.writeObject(newMessage);
						newMessage = (Message)ois.readObject();
						JOptionPane.showMessageDialog(frame,"got message");
	                	
	                }
	                else {
	                	JOptionPane.showMessageDialog(frame, "invalid account type");
	                    
	                }
					
					
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                if (newMessage.getStatus() == MsgStatus.Success) {
                	JOptionPane.showMessageDialog(frame, "successfully created new account");
         
                }*/
                
               
            }
        });
        
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when createButton is clicked
            	
            	String accountT = JOptionPane.showInputDialog(frame, "What is the account you would like to close?");
            	//int accountID = Integer.parseInt(acctID);
            	AccountType accountType = AccountType.unidentified;
            	if (accountT.equals("checking")) {
            		accountType = AccountType.Checking;
            	}
            	else if (accountT.equals("savings")) {
            		accountType = AccountType.Savings;
            	}
            	else if (accountT.equals("business")) {
            		accountType = AccountType.Business;
            	}
            	
            	boolean found = false;
    			for (Account current : customer.getAccounts()) {
    				if (current.getAccountType().equals(accountType)) {
    					///show the account...
    					found = true;
    					customer.getAccounts().remove(current);
    					JOptionPane.showMessageDialog(frame, "the account has been successfully deleted");
    					
    				}
    			
    			
    		}
    			if (found == false) {
    				///tell the user that the customer doesn't have this banking account, so make one
    				JOptionPane.showMessageDialog(frame, "The account you are looking for has not been made. Please create a bank account of this type.");
    			}
            	
            	balanceLabel.setText("Account was closed!");
            	
            }
        });
        
        
        

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountText = JOptionPane.showInputDialog(frame, "Enter amount to deposit:$");
                String acountType = JOptionPane.showInputDialog(frame, "Enter the account you would like to deposit in");
                
                double amount = Double.parseDouble(amountText);
                AccountType acc = AccountType.unidentified;
                if (acountType.equals("checking")) {
                	acc = AccountType.Checking;
                }
                else if (acountType.equals("savings")) {
                	acc = AccountType.Savings;
                }
                else if (acountType.equals("business")) {
                	acc = AccountType.Business;
                }
                
                ///find the acount
                boolean found = false;
                for (Account current : customer.getAccounts()) {
    				if (current.getAccountType().equals(acountType)) {
    					///show the account...
    					found = true;
    					//current.deposit(amount);
    					JOptionPane.showMessageDialog(frame, "successfully deposited "+amount+" dollars. Your balance is now "+current.getBalance());
    					
    				}
                }
                if (found == false) {
                	JOptionPane.showMessageDialog(frame, "unable to deposit "+amount);
					
                }
                //account.deposit(amount);
                //balanceLabel.setText("Balance: $" + account.getBalance());
            }
        });

        /*withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String amountText = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:$");
                String acountType = JOptionPane.showInputDialog(frame, "Enter the account you would like to deposit in");
                
                double amount = Double.parseDouble(amountText);
                AccountType acc = AccountType.unidentified;
                if (acountType.equals("checking")) {
                	acc = AccountType.Checking;
                }
                else if (acountType.equals("savings")) {
                	acc = AccountType.Savings;
                }
                else if (acountType.equals("business")) {
                	acc = AccountType.Business;
                }
                
                ///find the acount
                boolean found = false;
                for (Account current : customer.getAccounts()) {
    				if (current.getAccountType().equals(acountType)) {
    					///show the account...
    					found = true;
    					boolean success = current.withdraw(amount);
    					JOptionPane.showMessageDialog(frame, "successfully deposited "+amount+" dollars. Your balance is now "+current.getBalance());
    					
    				}
                }
                if (found == false) {
                	JOptionPane.showMessageDialog(frame, "unable to deposit "+amount);
					
                }
                //account.deposit(amount);
                //balanceLabel.setText("Balance: $" + account.getBalance());
            }
            
        });*/

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Get the selected account from the JList
            	String selectAccount = customerList.getSelectedValue();
            	
            	//Ask the user how much they want to transfer, to what account they want to put funds into, 
                String amountText = JOptionPane.showInputDialog(frame, "Enter the amount to transfer: ");
                double amount = Double.parseDouble(amountText);
                String accIDText = JOptionPane.showInputDialog(frame, "Which account would you like to add funds to; ");
                int accID = Integer.parseInt(accIDText);
                
                
            }
        });

        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when createButton is clicked
            	List<String> viewTransactionsText = new ArrayList<String>();
            	
            	Message message = null;

            	String customerID = JOptionPae.showInputDialog(frame, "Enter your userID:");
            	viewTransactionsText.add(customerID);
            	String acctID = JOptionPane.showInputDialog(frame, "Enter the account ID:");
            	viewTransactionsText.add(acctID);
            	
            	try {
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					
					// send the information of the gui to the client
					out.writeObject(new Message(MsgType.ViewTransactions, MsgStatus.Undefined, viewTransactionsText));
					
					// once client has sent a request to the server and recieve a message back
					// display the transactions
					message = (Message) in.readObject();
					List<String> transactionsList = message.getData();
					String transactions = "";
					
					for(int i = 0; i < transactionsList.size(); i++) {
						
						transactions += (transactionsList.get(i) + "\n";
						
						
					}
					
					JOptionPane.showMessageDialog(frame, transactions);

					
            	}
            	

            	
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Goodbye, We hope to see you again!");
                System.exit(0);	// End program
             }
          });


    }


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

    /*public static void main(String[] args) {
        //Create instance of BankerGUI
        new BankerGUI();
    }*/
}

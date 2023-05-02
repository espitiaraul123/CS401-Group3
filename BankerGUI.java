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
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BankerGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Banker GUI");
    private JButton createCustomerButton, createAccountButton, removeButton, depositButton, withdrawButton, transferButton, transactionButton, logoutButton;
    private JButton logIntoCustomerAccountButton, viewAllAccountsButton, viewCheckingAccountBalanceButton, viewSavingsAccountBalanceButton, viewBusinessAccountBalanceButton;
    private JPanel buttonPanel, labelPanel;
    private JLabel nameLabel, balanceLabel;
    private JList<String> customerList;
    
    //current customer being looked at
    Customer customer;
    String[] data;
    String fullname;
    int numOfAccounts;
    boolean checkingAccountExists;
    boolean savingsAccountExists;
    boolean businessAccountExists;
    double balanceOfChecking;
    double balanceOfSavings;
    double balanceOfBusiness;
    List <Account> allAccounts;
    Account checkingAccount;
    Account savingsAccount;
    Account businessAccount;
    
    
    public void setCustomer(Customer customer) {
    	this.customer = customer;
    	this.customer.setTheAccounts();
    	balanceOfChecking = this.customer.checkingAccountBalance;
    	balanceOfSavings = this.customer.savingsAccountBalance;
    	balanceOfBusiness = this.customer.businessAccountBalance;
    }
    public BankerGUI() throws UnknownHostException, IOException {
        //Set a custom Frame size 
    	Socket socket = new Socket("localhost", 1234);
    	frame.setSize(1000, 500);
    	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		//JOptionPane.showMessageDialog(frame, "sending message to server");
		
		ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
		
        //Create Panel
        buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));

        //Create Buttons
        createCustomerButton = new JButton("Create new customer");
        createAccountButton = new JButton("Create Account");
        logIntoCustomerAccountButton = new JButton("Log into customer Account");
        viewCheckingAccountBalanceButton = new JButton("View checking account balance");
        viewSavingsAccountBalanceButton = new JButton("View savings account balance");
        viewBusinessAccountBalanceButton = new JButton("View business account balance");
        
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
        buttonPanel.add(viewCheckingAccountBalanceButton);
        buttonPanel.add(viewSavingsAccountBalanceButton);
        buttonPanel.add(viewBusinessAccountBalanceButton);
        
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
        viewCheckingAccountBalanceButton.setPreferredSize(new Dimension(200, 60));
        viewSavingsAccountBalanceButton.setPreferredSize(new Dimension(200, 60));
        viewBusinessAccountBalanceButton.setPreferredSize(new Dimension(200, 60));
        
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
        

        //Add JList for customers
        String[] accounts = {"checking account", "savings account", "business account"};
        customerList = new JList<>(accounts);
        customerList.setVisibleRowCount(3);
        customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
       
        
        //Add components to label panel
        labelPanel.add(nameLabel);
        
        labelPanel.add(new JScrollPane(customerList));
        
        
        
       

        //Add Panel to Frame
        frame.add(labelPanel);
        frame.add(buttonPanel);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set bounds for label panel and button panel
        labelPanel.setBounds(50, 50, 200, 300);
        buttonPanel.setBounds(300, 50, 500, 500);
        
        
        viewCheckingAccountBalanceButton.addActionListener(new ActionListener () {
        	

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (checkingAccountExists != true) {
        			JOptionPane.showMessageDialog(frame, "This customer has not made a checking accout");
        			
        		}
        		else {
        			JOptionPane.showMessageDialog(frame, "Checking account balance is $"+checkingAccount.getBalance());
        		}
			}
        });
        viewSavingsAccountBalanceButton.addActionListener(new ActionListener () {
        	

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (savingsAccountExists != true) {
        			JOptionPane.showMessageDialog(frame, "This customer has not made a savings accout");
        			
        		}
        		else {
        			JOptionPane.showMessageDialog(frame, "savings account balance is $"+savingsAccount.getBalance());
        		}
			}
        });
        viewBusinessAccountBalanceButton.addActionListener(new ActionListener () {
        	

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (businessAccountExists != true) {
        			JOptionPane.showMessageDialog(frame, "This customer has not made a business accout");
        			
        		}
        		else {
        			JOptionPane.showMessageDialog(frame, "business account balance is $"+businessAccount.getBalance());
        		}
			}
        });
        createCustomerButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		System.out.println("creating new customer");
				// TODO Auto-generated method stub
				///build a new Customer message to send to the server
        		String fullname = JOptionPane.showInputDialog(frame, "Please enter your name");
	        	String userID = (JOptionPane.showInputDialog(frame, "please enter your userID"));
	        	String pin = (JOptionPane.showInputDialog(frame, "Please enter your pin"));
	        	List<String> arrayOfStrings = new ArrayList<String>();
	        	arrayOfStrings.add(fullname);
	        	arrayOfStrings.add(userID);
	        	arrayOfStrings.add(pin);
	        	
	        	Message newMessage = new Message(MsgType.NewCustomer, MsgStatus.Undefined,arrayOfStrings);
	        	JOptionPane.showMessageDialog(frame, "created message");
				
	        	
	        	//send this to the server
	        	// create a ObjectOutputStream so we can write data from it.
		        try {
		        	
					//ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					JOptionPane.showMessageDialog(frame, "sending message to server");
					
					//ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
					oos.writeObject(newMessage);
					newMessage = (Message)ois.readObject();
			        
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
            	
                // Code to execute when createButton is clicked
            	
            	String fullname = JOptionPane.showInputDialog(frame, "Please enter your name");
	        	String userID = (JOptionPane.showInputDialog(frame, "please enter your userID"));
	        	String pin = (JOptionPane.showInputDialog(frame, "Please enter your pin"));
	        	List<String> arrayOfStrings = new ArrayList<String>();
	        	arrayOfStrings.add(fullname);
	        	arrayOfStrings.add(userID);
	        	arrayOfStrings.add(pin);
	        	Message newMessage = new Message(MsgType.Login, MsgStatus.Undefined,arrayOfStrings);
	        	
            	//send the message to the server
            	try {
            		//ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					//ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
					oos.writeObject(newMessage);
					newMessage = (Message)ois.readObject();
					
			        
			        
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
    *
    */
		        	customer = newMessage.attachedCustomer;
		        	fullname = newMessage.attachedCustomer.getName();
		        	numOfAccounts = newMessage.attachedCustomer.getNumAccounts();
		        	//use a loop to match the appropriate account to the variable
		        	for (Account acc:newMessage.attachedCustomer.getAccounts()) {
		        		if (acc.getAccountType() == AccountType.Checking) {
		        			checkingAccount = acc;
		        			checkingAccountExists = true;
		        		}
		        		else if (acc.getAccountType() == AccountType.Savings) {
		        			savingsAccount = acc;
		        			savingsAccountExists = true;
		        		}
		        		else if (acc.getAccountType() == AccountType.Business) {
		        			businessAccount = acc;
		        			businessAccountExists = true;
		        		}
		        	}
		        	
		        	
		        	
		        	//initialize data
		        	 
		        	nameLabel.setText(fullname+"'s profile");
		        	///start the AtM for the customer
		        	//ATM atm = new ATM(customer);
		        	
		        }
		        else {
		        	JOptionPane.showMessageDialog(frame, "Customer login was unsuccessful");
		        }
           	}
        });
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("creating a new account");
                // Code to execute when createButton is clicked
            	List<String> oof = new ArrayList<>();
                String accountType = JOptionPane.showInputDialog(frame, "Account Type: ");
                AccountType acco = AccountType.unidentified;
                if (accountType.equals("Checking")) {
                	checkingAccountExists = true;
                	numOfAccounts++;
                }
                else if (accountType.equals("Savings")) {
                	savingsAccountExists = true;
                	numOfAccounts++;
                }
                else if (accountType.equals("Business")) {
                	businessAccountExists = true;
                	numOfAccounts++;
                }
                String userid = JOptionPane.showInputDialog(frame, "what is the userID?");
                //double new = Integer.parseInt(initialDeposit);
                Message newMessage = new Message();
                try {
                	
					//ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					//ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
					if(accountType.equals("checking")) {
	                	oof.add(userid);
	                	oof.add("Checking");
	                	newMessage = new Message(MsgType.NewAccount, MsgStatus.Undefined, oof);
	                	oos.writeObject(newMessage);
						newMessage = (Message)ois.readObject();
						
	                }
	                else if(accountType.equals("savings")) {
	                	oof.add(userid);
	                	oof.add("Savings");
	                	newMessage = new Message(MsgType.NewAccount, MsgStatus.Undefined, oof);
	                	oos.writeObject(newMessage);
						newMessage = (Message)ois.readObject();
						
	                }
	                else if(accountType.equals("business")) {
	                	oof.add(userid);
	                	oof.add("Business");
	                	newMessage = new Message(MsgType.NewAccount, MsgStatus.Undefined, oof);
	                	oos.writeObject(newMessage);
						newMessage = (Message)ois.readObject();
						
	                }
	                else {
	                	JOptionPane.showMessageDialog(frame, "invalid account type");
	                    
	                }
					
					
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                if (newMessage.getStatus() == MsgStatus.Success) {
                	setCustomer(newMessage.attachedCustomer);
                	JOptionPane.showMessageDialog(frame, "successfully created new account");
         
                }
                
                
               
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
                
                String[] possibleValues = { "Checking", "Savings", "Business" };

                String selectedValue = (String) JOptionPane.showInputDialog(null,
                            "Choose an account you would like to deposit into", "Accounts:",
                            JOptionPane.INFORMATION_MESSAGE, null,
                            possibleValues, possibleValues[0]);
                String ac = "";
                AccountType acc = AccountType.unidentified;
                if (selectedValue.equals("Checking")) {
                	acc = AccountType.Checking;
                	ac = "Checking";
                }
                else if (selectedValue.equals("Savings")) {
                	acc = AccountType.Savings;
                	ac = "Savings";
                }
                else if (selectedValue.equals("Business")) {
                	acc = AccountType.Business;
                	ac = "Business";
                }
                String amount = JOptionPane.showInputDialog(frame, "Enter the amount you would like to deposit");
                
                
                ///find the acount
                
                List<String> data = new ArrayList<>();
                String num = String.valueOf(customer.getUserID());
                data.add(num);
                JOptionPane.showMessageDialog(frame, ac);
                data.add(ac);
                data.add(amount);
                Message newMessage = new Message(MsgType.Deposit,MsgStatus.Undefined,data);
                double newAmount = Double.parseDouble(amount);
                try {
					oos.writeObject(newMessage);
					newMessage = (Message)ois.readObject();
					
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (newMessage.getStatus().equals(MsgStatus.Success)) {
					AccountType re = newMessage.attachedAccount.getAccountType();
					setCustomer(newMessage.attachedCustomer);
					
					if (re == AccountType.Checking) {
						checkingAccount.setBalance(checkingAccount.getBalance()+newAmount);
						customer.checkingAccount = checkingAccount;
						JOptionPane.showMessageDialog(frame, "The new balance in your checking account is $"+checkingAccount.getBalance());
					}
					else if (re == AccountType.Savings) {
						savingsAccount.setBalance(savingsAccount.getBalance()+newAmount);
						customer.savingsAccount = savingsAccount;
						JOptionPane.showMessageDialog(frame, "The new balance in your savings account is $"+savingsAccount.getBalance());
						
					}
					else if (re == AccountType.Business) {
						businessAccount.setBalance(businessAccount.getBalance()+newAmount);
						customer.businessAccount = businessAccount;
						JOptionPane.showMessageDialog(frame, "The new balance in your business account is $"+businessAccount.getBalance());
						
					}
					List<String> oof = new ArrayList<>();
					oof.add(num);
					Message newM = new Message(MsgType.UpdateCustomer, MsgStatus.Undefined, oof);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Unable to deposit $"+amount);
				}
                
            }
        });
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when createButton is clicked
                List<String> viewTransactionsText = new ArrayList<String>();

                Message message = null;

                String customerID = JOptionPane.showInputDialog(frame, "Enter your userID:");
                viewTransactionsText.add(customerID);
                String acctType = JOptionPane.showInputDialog(frame, "Enter the account Type:");
                viewTransactionsText.add(acctType);

                try {
                    //ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    // send the information of the gui to the client
                    oos.writeObject(new Message(MsgType.ViewTransactions, MsgStatus.Undefined, viewTransactionsText));

                    // once client has sent a request to the server and recieve a message back
                    // display the transactions
                    message = (Message) ois.readObject();
                    String transactions = "";

                    if (message.status == MsgStatus.Success) {
                    	List<String> transactionsList = message.getData();
                    	for(int i = 0; i < transactionsList.size(); i++) {

                            transactions += (transactionsList.get(i) + "\n");


                        }
                    	JOptionPane.showMessageDialog(frame, "These are the following transactions made:\n"+transactions);

                    }
                    else {
                    	JOptionPane.showMessageDialog(frame, "unable to retrieve transactions");

                    }
                    
                } catch (IOException |ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
                



            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String[] possibleValues = { "Checking", "Savings", "Business" };

                String selectedValue = (String) JOptionPane.showInputDialog(null,
                            "Choose an account you would like to withdraw from", "Accounts:",
                            JOptionPane.INFORMATION_MESSAGE, null,
                            possibleValues, possibleValues[0]);
                String ac = "";
                AccountType acc = AccountType.unidentified;
                if (selectedValue.equals("Checking")) {
                	acc = AccountType.Checking;
                	ac = "Checking";
                }
                else if (selectedValue.equals("Savings")) {
                	acc = AccountType.Savings;
                	ac = "Savings";
                }
                else if (selectedValue.equals("Business")) {
                	acc = AccountType.Business;
                	ac = "Business";
                }
                String amount = JOptionPane.showInputDialog(frame, "Enter the amount you would like to withdraw");
                
                
                ///find the acount
                
                List<String> data = new ArrayList<>();
                String num = String.valueOf(customer.getUserID());
                data.add(num);
                data.add(ac);
                data.add(amount);
                Message newMessage = new Message(MsgType.Withdraw,MsgStatus.Undefined,data);
                
                try {
					oos.writeObject(newMessage);
					newMessage = (Message)ois.readObject();
					
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (newMessage.getStatus().equals(MsgStatus.Success)) {
					AccountType re = newMessage.attachedAccount.getAccountType();
					setCustomer(newMessage.attachedCustomer);
					
					if (re == AccountType.Checking) {
						
						JOptionPane.showMessageDialog(frame, "The new balance in your checking account is $"+checkingAccount.getBalance());
					}
					else if (re == AccountType.Savings) {
						
						JOptionPane.showMessageDialog(frame, "The new balance in your savings account is $"+savingsAccount.getBalance());
						
					}
					else if (re == AccountType.Business) {
						
						JOptionPane.showMessageDialog(frame, "The new balance in your business account is $"+businessAccount.getBalance());
						
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Unable to withdraw $"+amount);
				}
                
            }
            
        });

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

                String customerID = JOptionPane.showInputDialog(frame, "Enter your userID:");
                viewTransactionsText.add(customerID);
                String acctType = JOptionPane.showInputDialog(frame, "Enter the account Type:");
                viewTransactionsText.add(acctType);

                try {
                    //ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    // send the information of the gui to the client
                    oos.writeObject(new Message(MsgType.ViewTransactions, MsgStatus.Undefined, viewTransactionsText));

                    // once client has sent a request to the server and recieve a message back
                    // display the transactions
                    message = (Message) ois.readObject();
                    List<String> transactionsList = message.getData();
                    String transactions = "";

                    for(int i = 0; i < transactionsList.size(); i++) {

                        transactions += (transactionsList.get(i) + "\n");


                    }

                    JOptionPane.showMessageDialog(frame, transactions);


                } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

}

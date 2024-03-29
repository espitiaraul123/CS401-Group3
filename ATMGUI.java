import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ATMGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Banker GUI");
    private JButton depositButton, withdrawButton, transferButton, transactionButton, logoutButton;
    private JButton viewCheckingAccountBalanceButton, viewSavingsAccountBalanceButton, viewBusinessAccountBalanceButton;
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
    	this.checkingAccount = customer.getAccount(AccountType.Checking);
    	if (checkingAccount != null) {
    		checkingAccountExists = true;
    	}
    	this.savingsAccount = customer.getAccount(AccountType.Savings);
    	if (savingsAccount != null) {
    		savingsAccountExists = true;
    	}
    	this.businessAccount = customer.getAccount(AccountType.Business);
    	if (businessAccount != null) {
    		businessAccountExists = true;
    	}
    }
    
    public ATMGUI(Customer customer) throws UnknownHostException, IOException {
        //Set a custom Frame size 
    	this.customer = customer;
    	
    	fullname = this.customer.getName();
    	numOfAccounts = this.customer.getNumAccounts();
    	//use a loop to match the appropriate account to the variable
    	for (Account acc:customer.getAccounts()) {
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
    	
    	
    	Socket socket = new Socket("localhost", 1234);
    	frame.setSize(1000, 500);
    	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		//JOptionPane.showMessageDialog(frame, "sending message to server");
		
		ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
		
        //Create Panel
        buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        
        //Create Buttons
        viewCheckingAccountBalanceButton = new JButton("View checking account balance");
        viewSavingsAccountBalanceButton = new JButton("View savings account balance");
        viewBusinessAccountBalanceButton = new JButton("View business account balance");
        
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        transferButton = new JButton("Transfer");
        transactionButton = new JButton("View Transactions");
        logoutButton = new JButton("Logout");
        
        //Add Buttons to Panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,0));
        buttonPanel.add(viewCheckingAccountBalanceButton);
        buttonPanel.add(viewSavingsAccountBalanceButton);
        buttonPanel.add(viewBusinessAccountBalanceButton);
        
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(transactionButton);
        buttonPanel.add(logoutButton);

        //Set dimension of the button size
        viewCheckingAccountBalanceButton.setPreferredSize(new Dimension(200, 60));
        viewSavingsAccountBalanceButton.setPreferredSize(new Dimension(200, 60));
        viewBusinessAccountBalanceButton.setPreferredSize(new Dimension(200, 60));
        
        depositButton.setPreferredSize(new Dimension(200, 60));
        withdrawButton.setPreferredSize(new Dimension(200, 60));
        transferButton.setPreferredSize(new Dimension(200, 60));
        transactionButton.setPreferredSize(new Dimension(200, 60));
        logoutButton.setPreferredSize(new Dimension(200, 60));
        

        //Add Label and button panel
        labelPanel = new JPanel(new GridLayout(2,1));
        nameLabel = new JLabel("Customer Name: ");
        nameLabel.setText(customer.getName()+"'s profile");
    	

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
        
        withdrawButton.addActionListener(new ActionListener() {
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
                String amount = JOptionPane.showInputDialog(frame, "Enter the amount you would like to withdraw");
                
                
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
						checkingAccount.setBalance(checkingAccount.getBalance()-newAmount);
						customer.checkingAccount = checkingAccount;
						JOptionPane.showMessageDialog(frame, "The new balance in your checking account is $"+checkingAccount.getBalance());
					}
					else if (re == AccountType.Savings) {
						savingsAccount.setBalance(savingsAccount.getBalance()-newAmount);
						customer.savingsAccount = savingsAccount;
						JOptionPane.showMessageDialog(frame, "The new balance in your savings account is $"+savingsAccount.getBalance());
						
					}
					else if (re == AccountType.Business) {
						businessAccount.setBalance(businessAccount.getBalance()-newAmount);
						customer.businessAccount = businessAccount;
						JOptionPane.showMessageDialog(frame, "The new balance in your business account is $"+businessAccount.getBalance());
						
					}
					List<String> oof = new ArrayList<>();
					oof.add(num);
					Message newM = new Message(MsgType.UpdateCustomer, MsgStatus.Undefined, oof);
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
            	String userID = JOptionPane.showInputDialog(frame, "Enter the user ID");
            	
            	String TransferFrom = JOptionPane.showInputDialog(frame, "Enter the account to transfer from: ");
            	String amount = JOptionPane.showInputDialog(frame, "Enter the amount to transfer: ");
                
            	String transferTo = JOptionPane.showInputDialog(frame, "Enter the account to transfer into: ");
            	List<String> data = new ArrayList<>();
                data.add(TransferFrom);
                data.add(amount);
                data.add(transferTo);
                data.add(userID);
                Message message = new Message(MsgType.Transfer,MsgStatus.Undefined,data);
                try {
                    //ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    // send the information of the gui to the client
                    oos.writeObject(new Message(MsgType.ViewTransactions, MsgStatus.Undefined, data));

                    // once client has sent a request to the server and recieve a message back
                    // display the transactions
                    message = (Message) ois.readObject();
                    
                    if (message.status == MsgStatus.Success) {
                    	
                    	JOptionPane.showMessageDialog(frame, "the operation was a success");

                        
                    }
                    else {
                    	JOptionPane.showMessageDialog(frame, "unable to complete the transfer of funds");

                    }
                    
                } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
                
                String id = String.valueOf(customer.getUserID());
                List<String> list = new ArrayList<>();
                list.add(id);
                Message m = new Message(MsgType.Logout, MsgStatus.Undefined, list);
                customer.checkingAccount = checkingAccount;
                customer.savingsAccount = savingsAccount;
                customer.businessAccount = businessAccount;
                m.attachedCustomer = customer;
                try {
					oos.writeObject(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                System.exit(0);	// End program
             }
          });
    }
}

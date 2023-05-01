import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class BankerGUI {
    private JFrame frame = new JFrame("Banker GUI");
    private JButton createButton, removeButton, depositButton, withdrawButton, transferButton, transactionButton, logoutButton;
    private JPanel buttonPanel, labelPanel;
    private JLabel nameLabel, balanceLabel;
    private JList<String> customerList;
    private Customer customer;
    private Account account;

    public BankerGUI() {

    	//Set a custom Frame size
        frame.setSize(1000, 500);

        //Create Panel
        buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));

        //Create Buttons
        createButton = new JButton("Create Account");
        removeButton = new JButton("Remove Account");
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        transferButton = new JButton("Transfer");
        transactionButton = new JButton("View Transactions");
        logoutButton = new JButton("Logout");

        //Add Buttons to Panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,0));
        buttonPanel.add(createButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(transactionButton);
        buttonPanel.add(logoutButton);

        //Set dimension of the button size
        createButton.setPreferredSize(new Dimension(200, 60));
        removeButton.setPreferredSize(new Dimension(200, 60));
        depositButton.setPreferredSize(new Dimension(200, 60));
        withdrawButton.setPreferredSize(new Dimension(200, 60));
        transferButton.setPreferredSize(new Dimension(200, 60));
        transactionButton.setPreferredSize(new Dimension(200, 60));
        logoutButton.setPreferredSize(new Dimension(200, 60));

        //Add Label and button panel
        labelPanel = new JPanel(new GridLayout(2,1));
        balanceLabel = new JLabel("Balance: $");

        //Add JList for customers
        DefaultListModel <String> listModel = new DefaultListModel<>();
        customerList = new JList<>(listModel);

        //Add components to label panel
        labelPanel.add(balanceLabel);
        labelPanel.add(new JScrollPane(customerList));

        //Add Panel to Frame
        frame.add(labelPanel);
        frame.add(buttonPanel);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Set bounds for label panel and button panel
        labelPanel.setBounds(50, 50, 200, 300);
        buttonPanel.setBounds(300, 50, 500, 500);




        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when createButton is clicked
                String accountType = JOptionPane.showInputDialog(frame, "Account Type: ");
                Double initialDeposit = Double.parseDouble(JOptionPane.showInputDialog(frame, "Initial Deposit: "));
                
                AccountType type;

                if(accountType.equals("Checkings")) {
                	type = AccountType.Checkings;
                } else if(accountType.equals("Savings")) {
                	type = AccountType.Savings;
                } else {
                	JOptionPane.showMessageDialog(frame, "Invalid Account Type");
                	return;
                }
                Account newAccount = new Account(type, initialDeposit);
                customer.addAccount(newAccount);
                balanceLabel.setText("New " + accountType + " Account added.");
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when createButton is clicked
            	String acctID = JOptionPane.showInputDialog(frame, "What is the ID number of the account to close:");
            	customer.closeAccount(acctID);
            	balanceLabel.setText("Account was closed!");
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String accountText = JOptionPane.showInputDialog(frame, "To which account would you like to deposit?");
            	String amountText = JOptionPane.showInputDialog(frame, "Enter amount to deposit");
            	int accountId = Integer.parseInt(accountText);
            	double amount = Double.parseDouble(amountText);
                account.deposit(amount, accountId);
                balanceLabel.setText("Balance: $" + account.getBalance());
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String accountText = JOptionPane.showInputDialog(frame, "Which account would you like to withdraw from?");
                String amountText = JOptionPane.showInputDialog(frame, "Enter the amount to withdraw");
                int accountId = Integer.parseInt(accountText);
                double amount = Double.parseDouble(amountText);
                account.withdraw(amount, accountId);
                balanceLabel.setText("Balance:$ " + account.getBalance());
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
            	// Code to execute when transactionButton is clicked
            	String account1 = JOptionPane.showInputDialog(frame, "Which account would you to transfer funds from");
            	String account2 = JOptionPane.showInputDialog(frame,"Which account would you like to transfer funds to");
            	//
            	int account1Text = Integer.parseInt(account1);
            	int account2Text = Integer.parseInt(account2);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Goodbye, We hope to see you again!");
                System.exit(0);	// End program
             }
          });
    }

    public static void main(String[] args) {
        //Create instance of BankerGUI
        new BankerGUI();
    }
}

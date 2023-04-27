import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BankerGUI {
    private JFrame frame = new JFrame("Banker GUI");
    private JButton createButton, removeButton, depositButton, withdrawButton, transferButton, transactionButton, logoutButton;
    private JPanel buttonPanel, labelPanel;
    private JLabel nameLabel, balanceLabel;
    private JList<String> customerList;
    private Customer customer;

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
        nameLabel = new JLabel("Customer Name: ");
        balanceLabel = new JLabel("Balance: $");

        //Add JList for customers
        String[] accounts = {"Account 1", "Account 2", "Account 3", "Account 4", "Account 5"};
        customerList = new JList<String>(accounts);

        //Add components to label panel
        labelPanel.add(nameLabel);
        labelPanel.add(balanceLabel);
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
        
        //Account account = new Account("John Doe", 12345, AccountType.Checkings);
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to execute when createButton is clicked
                String accountType = JOptionPane.showInputDialog(frame, "Account Type: ");
                Double initialDeposit = JOptionPane.showInputDialog(frame, "Initial Deposit: ");
                Customer customer = null;
                if(accountType.equals("Checkings")) {
                	AccountType type = AccountType.Checkings;
                }else if(accountType.equals("Savings")) {
                	AccountType type = AccountType.Savings;
                }
                
                customer.addAccount(initalDeposit, type);
                balanceLabel.setText("New Account Added!");

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
                String amountText = JOptionPane.showInputDialog(frame, "Enter amount to deposit:$");
                double amount = Double.parseDouble(amountText);
                account.deposit(amount);
                balanceLabel.setText("Balance: $" + account.getBalance());
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountText = JOptionPane.showInputDialog(frame, "Enter the amount to withdraw:$ ");
                double amount = Double.parseDouble(amountText);
                account.withdraw(amount);
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
                // Code to execute when createButton is clicked
            }
        });

        logoutButton.addActionListener(new ActionListener() {
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

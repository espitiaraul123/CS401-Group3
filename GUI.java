
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class GUI {
	private JFrame frame = new JFrame();
	private JButton depositButton, withdrawButton, logoutButton;
	private JPanel panel;
	private JLabel balanceLabel;
	private JList<Account> accountList;
	private DefaultListModel<Account> accountListModel;
	
	
	public GUI() {
		
		//Create a new JFrame
		frame = new JFrame();
		
		//Create buttons for deposit, withdraw, view balance, logoutm and account list
		depositButton = new JButton("Deposit");
		withdrawButton = new JButton("Withdraw");
		//transactionButton = new JButton("View Transaction History");
		logoutButton = new JButton("Logout");
		
		//Create a new JPanel
		panel = new JPanel();
		
		//Create a JLabel for displaying the Customer's balance
		balanceLabel = new JLabel("Balance: $0.00");
		
		//Create a DefaultListModel and JList for displaying the accounts
		accountListModel = new DefaultListModel<>();
		accountList = new JList<>(accountListModel);
		
		//Set border and layout for JPanel
		panel.setBorder(BorderFactory.createEmptyBorder(150,150,150,150));
		panel.setLayout(new GridLayout(0,1));
		
		//Add JButtons to the JPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,1,5,5));
		buttonPanel.add(depositButton);
		buttonPanel.add(withdrawButton);
		buttonPanel.add(logoutButton);
		panel.add(buttonPanel, BorderLayout.EAST);
		
		//Add the balance JLabel and account JList to the JPanel
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,1,5,5));
		infoPanel.add(balanceLabel);
		infoPanel.add(accountList);
		panel.add(buttonPanel, BorderLayout.CENTER);
		
		//Add the JPanel to the JFrame
		frame.add(panel, BorderLayout.CENTER);
		
		//Setting title, size, visibility and default close operation for the JFrame 
		frame.setTitle("Bank System Manager");
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		
		/*Add Action Listerner to the account list button
		accountListButton.addActionListener(e -> {
			ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account("Checking", 1000.0));
            accounts.add(new Account("Savings", 5000.0));
			
			//Create a JFrame to show list of accounts
			JFrame accountFrame = new JFrame();
			JPanel accountPanel = new JPanel();
			accountPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			//Create a JList to display the accounts
			JList<Account> accountList = new JList<>(accounts.toArray(new Account[0]));
			accountPanel.add(accountList);
			
			//Add the account list to the account JFrame
			accountFrame.add(accountPanel);
			
			//Set the title, size, and visibility for the account JFrame
			accountFrame.setTitle("List of Customer Accounts");
			accountFrame.pack();
			accountFrame.setVisible(true);
	});
	*/
	}
	
	public static void main(String[] args) {
		//Create a new instance of the GUI
		new GUI();
	}

}

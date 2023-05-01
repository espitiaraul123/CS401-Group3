import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ATM {
	private Customer customer;
	private Account checkingAccount;
	private Account savingsAccount;
	private Account businessAccount;
	
	public ATM (Customer newCustomer) {	
		customer = newCustomer;
		//use loop to find an
		//Create GUI Components
		JTextField accountNumberField = new JTextField(10);
		JTextField transferAmountField = new JTextField(10);
		JTextField transfertoAccountField = new JTextField(10);
		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton transferButton = new JButton("Transfer");
		JLabel balanceLabel = new JLabel("Balance:$ ");
		
		//Create GUI layout
		JPanel panel = new JPanel(new GridLayout(0,2));
		panel.add(depositButton);
		panel.add(new JLabel(""));
		panel.add(withdrawButton);
		panel.add(new JLabel(""));
		panel.add(transferButton);
		panel.add(new JLabel(""));
		panel.add(balanceLabel);

		
		//Add Action Listeners to the buttons
		
		/*depositButton.addActionListener(e-> {
			JTextField depositAmountField = new JTextField(10);
			JPanel depositPanel = new JPanel();
			depositPanel.add(new JLabel("Enter amount to deposit:"));
            depositPanel.add(depositAmountField);
			int option = JOptionPane.showConfirmDialog(null, depositPanel, "Deposit", JOptionPane.OK_CANCEL_OPTION);
		    if (option == JOptionPane.OK_OPTION) {
		        try {
		            double amount = Double.parseDouble(depositAmountField.getText());
		            account.deposit(amount, TransactionType.DEPOSIT);
		            balanceLabel.setText("Balance: " + account.getBalance());
		            JOptionPane.showMessageDialog(null, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Invalid amount entered!", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
			double amount = Double.parseDouble(depositAmountField.getText());
			account.deposit(amount, TransactionType.DEPOSIT);
			balanceLabel.setText("Balance: " + account.getBalance());
		});*/
		
		/*withdrawButton.addActionListener(e -> {
		    JTextField withdrawAmountField = new JTextField(10);
		    JPanel withdrawPanel = new JPanel();
		    withdrawPanel.add(new JLabel("Enter amount to withdraw:"));
            withdrawPanel.add(withdrawAmountField);
		    int option = JOptionPane.showConfirmDialog(null, withdrawAmountField, "Withdraw", JOptionPane.OK_CANCEL_OPTION);
		    if (option == JOptionPane.OK_OPTION) {
		        try {
		            double amount = Double.parseDouble(withdrawAmountField.getText());
		            account.withdraw(amount, TransactionType.WITHDRAW);
		            balanceLabel.setText("Balance: " + account.getBalance());
		            JOptionPane.showMessageDialog(null, "Withdrawal successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Invalid amount entered!", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});*/

		
		
		
		
		// Create and show the GUI
        JFrame frame = new JFrame("ATM");
        frame.setPreferredSize(new Dimension(700,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
	
	}

}

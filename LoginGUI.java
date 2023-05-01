import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI implements ActionListener {
	private static JLabel password1, label;
	private static JTextField username;
	private static JButton button;
	private static JPasswordField Password;
	private static JPanel accountPanel;
	private JList<Account> accountList;
	
	public LoginGUI() {
	//Create JPanel Class
	JPanel panel = new JPanel();
	panel.setLayout(null);
	
	//JFrame Class
	JFrame frame = new JFrame();
	frame.setTitle("Customer Login");
	frame.setLocation(new Point(500, 300));
	frame.add(panel);
	frame.setSize(new Dimension(400, 200));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//Username Label Constructor
	label = new JLabel("Username");
	label.setBounds(100,8,70,20);
	panel.add(label);
	
	//Username TextField Constructor
	username = new JTextField();
	username.setBounds(100,27,193,20);
	panel.add(username);
	
	//Password Label Constructor
	password1 = new JLabel("Password");
	password1.setBounds(100,55,70,20);
	panel.add(password1);
	
	//Password TextField Constructor
	Password = new JPasswordField();
	Password.setBounds(100,75,193,20);
	panel.add(Password);
	
	
	//Login Button
	JButton loginButton = new JButton("Login");
	loginButton.setBounds(150,110,80,25);
	loginButton.addActionListener(this);
	panel.add(loginButton);
	
	frame.setVisible(true); //Make frame visible
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String enteredUsername = username.getText();
		String enteredPassword = Password.getText();
		
		//Check if username and password are correct
		boolean loginSuccessful = checkLoginCredentials(enteredUsername, enteredPassword);
		
	    if (loginSuccessful) {
	        JOptionPane.showMessageDialog(null, "Login Successful");
	        BankerGUI bankerGUI = new BankerGUI(); //Cerate object of BankerGUI class
	    } else {
	        JOptionPane.showMessageDialog(null, "Incorrect login. Please try again");
	    }
	}
	
	private boolean checkLoginCredentials(String username, String password) {
	    // Replace this with your actual login check logic
	    // For now, just check if the username is "admin" and password is "password"
	    return username.equals("admin") && password.equals("password");
	}
	
	public static void main(String args[]) {
		new LoginGUI();
	}
}

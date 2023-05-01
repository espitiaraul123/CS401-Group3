import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginGUI implements ActionListener {
	private String enteredUsername;
	private String enteredPassword;
	private boolean enteredCredentials = false;
	
	private static JLabel password1, label;
	private static JTextField username;
	private static JButton button;
	private static JTextField Password;
	private static JPanel accountPanel;
	private static JButton loginButton;
	
	private JList<Account> accountList;
	
	public LoginGUI() {
	//Create JPanel Class
	JPanel panel = new JPanel();
	panel.setLayout(null);
	
	//JFrame Class
	JFrame frame = new JFrame();
	frame.setTitle("Golden State Bank Login");
	frame.setLocation(new Point(500, 300));
	frame.add(panel);
	frame.setSize(new Dimension(420, 420));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	frame.getContentPane().setBackground(new Color(123,50,250));
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
	Password = new JTextField();
	Password.setBounds(100,75,193,20);
	panel.add(Password);
	
	
	//Login Button
	loginButton = new JButton("Login");
	loginButton.setBounds(150,110,80,25);
	loginButton.addActionListener(this);
	panel.add(loginButton);
	
	
	
	//frame.pack();
	
	frame.setVisible(true);  //Make frame visible
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==button) {
			username.getText();
			Password.getText();
			loginButton.setEnabled(false);
			//textField.setEditable(false);
			this.enteredUsername = username.getText();
			this.enteredPassword = password1.getText();
			this.enteredCredentials = true;
			System.out.println("hello "+enteredUsername+" "+enteredPassword);
		}
		
		//Check if username and password are correct
		boolean loginSuccessful = checkLoginCredentials(enteredUsername, enteredPassword);
		
	    if (loginSuccessful) {
	        JOptionPane.showMessageDialog(null, "Login Successful");
	    } else {
	        JOptionPane.showMessageDialog(null, "Incorrect username or password");
	    }
	}
	
	
	private boolean checkLoginCredentials(String username, String password) {
	    // Replace this with your actual login check logic
	    // For now, just check if the username is "admin" and password is "password"
	    return username.equals("admin") && password.equals("password");
	}
	public String getEnteredCredentials () {
		String credentials = enteredUsername + " "+enteredPassword;
		return credentials;
	}
	public boolean enteredCredentials() {
		return enteredCredentials;
	}
	
	public static void main(String args[]) {
		new LoginGUI();
	}
}

import java.io.Serializable;

public class Message implements Serializable {
    protected MsgType type;
    protected MsgStatus status;
    protected String text;
    //these variables are to send a new customer
    public Customer newCustomer;
    //these variables are to login
    public String username;
    public String password;
    public String[] data;
    public Message(){
        this.type = MsgType.Undefined;
        this.status = MsgStatus.Undefined;
        this.text = "Undefined";
    }

    public Message(MsgType type, MsgStatus status, String[] text){
        this.type = type;
        this.status = status;
        this.data = text;
    }
    public String[] getData() {
    	return data;
    }
    public void makeLoginMessage(String username, String password) {
    	type = MsgType.Login;
    	this.username = username;
    	System.out.println("setting username to "+username);
    	this.password = password;
    	System.out.println("setting password to "+password);
    	
    }
    
    private void setType(MsgType type){
    	this.type = type;
    }
   
    public void setStatus(MsgStatus status){
    	this.status = status;
    }
    
    public void setText(String text){
    	this.text = text;
    }
    
    public MsgType getType(){
    	return type;
    }

    public MsgStatus getStatus(){
    	return status;
    }

    public String getText(){
    	return text;
    }
}

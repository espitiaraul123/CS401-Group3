import java.io.Serializable;
import java.util.*;

public class Message implements Serializable {
    protected MsgType type;
    protected MsgStatus status;
    protected List<String> data;
    public Customer attachedCustomer;
    
    public Message(){
        this.type = MsgType.Undefined;
        this.status = MsgStatus.Undefined;
        this.data = new ArrayList<>();
    }

    public Message(MsgType type, MsgStatus status, List<String> data){
        this.type = type;
        this.status = status;
        this.data = data;
    }
    
    // Getters
    public MsgType getType(){
    	return type;
    }
    public MsgStatus getStatus(){
    	return status;
    }
    public List<String> getData() {
    	return data;
    }
    
    // Setters
    private void setType(MsgType type){
    	this.type = type;
    }
    public void setStatus(MsgStatus status){
    	this.status = status;
    }
    public void setText(List<String> data){
    	this.data = data;
    }
}

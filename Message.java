import java.io.Serializable;

public class Message implements Serializable {
    protected MsgType type;
    protected MsgStatus status;
    protected String text;

    public Message(){
        this.type = MsgType.Undefined;
        this.status = MsgStatus.Undefined;
        this.text = "Undefined";
    }

    public Message(MsgType type, MsgStatus status, String text){
        this.type = type;
        this.status = status;
        this.text = text;
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

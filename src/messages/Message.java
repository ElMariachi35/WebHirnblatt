package messages;

public class Message {
	private String messageId="";
	private String message1="";
	private String message2="";
	private String message3="";
	private String message4="";
	
	public Message(){
		
	}
	
	public Message(String messageId, String message1){
		this.messageId=messageId;
		this.message1=message1;
	}
	
	public Message(String messageId, String message1, String message2){
		this.messageId=messageId;
		this.message1=message1;
		this.message2=message2;
	}
	
	public Message(String messageId, String message1, String message2, String message3){
		this.messageId=messageId;
		this.message1=message1;
		this.message2=message2;
		this.message3=message3;
	}
	public Message(String messageId, String message1, String message2, String message3, String message4){
		this.messageId=messageId;
		this.message1=message1;
		this.message2=message2;
		this.message3=message3;
		this.message4=message4;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getMessage3() {
		return message3;
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
	}

	public String getMessage4() {
		return message4;
	}

	public void setMessage4(String message4) {
		this.message4 = message4;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}
}

package messages;

public class ServerMessage {
	private String messageId="";


	
	public ServerMessage(String messageId){
		this.messageId=messageId;

	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}



}
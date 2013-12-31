package messages;

public class ServerMessageInit extends ServerMessage{
	private String uId;
	
	public ServerMessageInit(String messageId, String uId) {
		super(messageId);
		this.uId=uId;
		
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

}
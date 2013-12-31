package messages;

import java.util.ArrayList;
import java.util.List;



public class ServerMessageUser extends ServerMessage{
	private List userList;
	private String gameId;
	
	public ServerMessageUser(String messageId, String gameId, List userList){
		super(messageId);
		this.setUserList(userList);
		this.setGameId(gameId);
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	


}




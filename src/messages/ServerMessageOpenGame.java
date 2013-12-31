package messages;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import classes.Game;
import classes.User;

public class ServerMessageOpenGame extends ServerMessage {
	
	private String gId;
	private String[] playerNames;
	private String[] playerId;
	private String userId;
	
	public ServerMessageOpenGame(String messageId, String gId, String[] playerNames, String[] playerId, String userId ) {
		super(messageId);
		this.gId=gId;
		this.setPlayerNames(playerNames);
		this.setPlayerId(playerId);
		this.setUserId(userId);
	}

	public ServerMessageOpenGame(String messageId, String gId, String[] playerNames, String[] playerId ) {
		super(messageId);
		this.gId=gId;
		this.setPlayerNames(playerNames);
		this.setPlayerId(playerId);
	}
	
	public ServerMessageOpenGame(String messageId, String gId) {
		super(messageId);
		this.gId=gId;
	}
	public String getgId() {
		return gId;
	}

	public void setgId(String gId) {
		this.gId = gId;
	}

	public String[] getPlayerNames() {
		return playerNames;
	}

	public void setPlayerNames(String[] playerNames) {
		this.playerNames = playerNames;
	}

	public String[] getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String[] playerId) {
		this.playerId = playerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	

}

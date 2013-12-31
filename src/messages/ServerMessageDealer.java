package messages;

import player.Player;

public class ServerMessageDealer extends ServerMessage {

	private String dealer;
	private String gameId;
	
	public ServerMessageDealer(String messageId, String gameId, String userId) {
		super(messageId);
		this.setDealer(userId);	
		this.setGameId(gameId);
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}



}

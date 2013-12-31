package messages;

import java.util.Map;

public class ServerMessageResult extends ServerMessage {
	private Map<String, Integer> result;
	private int nrOfCards=0;
	private String gameId="";
	
	public ServerMessageResult(String messageId, String gameId, Map<String, Integer> result, int nrOfCards) {
		super(messageId);
		this.result=result;
		this.setNrOfCards(nrOfCards);
		this.setGameId(gameId);
	}

	public Map<String, Integer> getResult() {
		return result;
	}

	public void setResult(Map<String, Integer> result) {
		this.result = result;
	}

	public int getNrOfCards() {
		return nrOfCards;
	}

	public void setNrOfCards(int nrOfCards) {
		this.nrOfCards = nrOfCards;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

}

package messages;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import classes.Card;

public class ServerMessageCards extends ServerMessage{

	private String userId="";
	private ArrayList<Card> cards = null;
	private Map<String, Card> cardsMap = null;
	private String gameId="";

	
	public ServerMessageCards(String messageId,String gameId, String userId, ArrayList<Card> cards) {
		super(messageId);
		this.setUserId(userId);
		this.setCards(cards);
		this.setGameId(gameId);
	}
	
	public ServerMessageCards( String messageId,String gameId, String userId, Map<String,Card> cards) {
		super(messageId);
		this.setUserId(userId);
		this.setCardsMap(cards);
		this.setGameId(gameId);
	}
	
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public Map<String, Card> getCardsMap() {
		return cardsMap;
	}

	public void setCardsMap(Map<String, Card> cardsMap) {
		this.cardsMap = cardsMap;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}

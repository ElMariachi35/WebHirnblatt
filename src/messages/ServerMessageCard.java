package messages;

import classes.Card;

public class ServerMessageCard extends ServerMessage {

	private String gameId;
	private Card card;
	private String userId;
	private String cardId;
	
	
	public ServerMessageCard(String messageId, String gameId, String userId, Card card, String cardId) {
		super(messageId);
		this.setGameId(gameId);
		this.setCard(card);
		this.setUserId(userId);
		this.setCardId(cardId);
	}
	
	public ServerMessageCard(String messageId, String gameId,  String userId, Card card) {
		super(messageId);
		this.setGameId(gameId);
		this.setCard(card);
		this.setUserId(userId);
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}


}

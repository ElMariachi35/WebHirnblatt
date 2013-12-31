package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import classes.Card;

public abstract class Player {
	private String name="";
	private String userId="";
	private int seatPos=0;
	private ArrayList<Card> cardsToPlay=null; 
	private ArrayList<Card> playedCards=new ArrayList<Card>(); 
	private int tricksMelded = 0;
	private int tricksTaken = 0;
	
	
	
	public Player(String name, String userId){
		this.setName(name);
		this.setUserId(userId);
		this.setCardsToPlay(new ArrayList<Card>());
	}

	public void addCardToCardToPlay(Card c){
		this.cardsToPlay.add(c);
	}
	
	public void addTricksTaken(){
		tricksTaken++;
	}
	
	public abstract void meldTricks(int nrOfTricksMelded, int nrOfCards, List<Player> userList);
	
	public abstract Card playCard(int cardPos, int firstSuit, Card highCard);

	public boolean hasCard(Card c){
		for(Card ca : playedCards){
			if(ca.compareTo(c)==0){
				return true;
			}
		}
		return false;
	}
	
	
	public void resetAfterRound(){
		this.setCardsToPlay(new ArrayList<Card>());
		this.setPlayedCards(new ArrayList<Card>());
		this.setTricksMelded(0);
		this.setTricksTaken(0);
	}
	
	/**
	 * Looks in the players cardsToPlay if he has the given suit
	 * @return
	 */
	protected boolean hasCardOfSuit(int suit){
		for(Card c : this.cardsToPlay){
			if(c.getSuit()==suit){
				return true;
			}
		}
		return false;
	}

	public void sortCardsToPlay(){
		Collections.sort(this.cardsToPlay);
	}
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeatPos() {
		return seatPos;
	}

	public void setSeatPos(int seatPos) {
		this.seatPos = seatPos;
	}

	public ArrayList<Card> getCardsToPlay() {
		return cardsToPlay;
	}

	public void setCardsToPlay(ArrayList<Card> cardsToPlay) {
		this.cardsToPlay = cardsToPlay;
	}

	public int getTricksMelded() {
		return tricksMelded;
	}

	public void setTricksMelded(int tricksMelded) {
		this.tricksMelded = tricksMelded;
	}

	public ArrayList<Card> getPlayedCards() {
		return playedCards;
	}

	public void setPlayedCards(ArrayList<Card> playedCards) {
		this.playedCards = playedCards;
	}

	public int getTricksTaken() {
		return tricksTaken;
	}

	public void setTricksTaken(int tricksTaken) {
		this.tricksTaken = tricksTaken;
	}

}

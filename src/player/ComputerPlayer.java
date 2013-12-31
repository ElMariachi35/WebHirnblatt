package player;

import java.util.ArrayList;
import java.util.List;

import classes.Card;

public abstract class ComputerPlayer extends Player {

	public ComputerPlayer(String name, String userId) {
		super(name, userId);
	}
	
	public abstract void meldTricks(int nrOfTricksMelded, int nrOfCards, List<Player> userList);

	@Override
	public abstract Card playCard(int cardPos, int firstSuit, Card highCard);
	
	protected Card getHighCard(List<Card> cards){
		Card retCard=cards.get(0);
		for(Card c : cards){
			if(c.compareTo(retCard)==1){
				retCard = c;
			}
		}
		return retCard;
	}
	
	protected Card getLowCard(List<Card> cards){
		Card retCard=cards.get(0);
		for(Card c : cards){
			if(c.compareTo(retCard)==-1){
				retCard = c;
			}
		}
		return retCard;
	}
	
	protected boolean couldTakeTrick(Card highCard, List<Card> cards){
		if(this.getHighCard(cards).compareTo(highCard)==1){
			return true;
		}else{
			return false;
		}
	}
	
	protected boolean couldAvoidTrick(Card highCard, List<Card> cards){
		if(this.getLowCard(cards).compareTo(highCard)==-1){
			return true;
		}else{
			return false;
		}
	}
	
	protected boolean needsTrick(){
		if(this.getTricksTaken()<this.getTricksMelded()){
			return true;
		}else{
			return false;
		}
	}
	
	protected Card getHighestCardThatAvoidsTrick(Card highCard, List<Card> cards){
		Card retCard=null;
		for(Card c : cards){
			if(retCard ==null){
				if(c.compareTo(highCard)==-1){
					retCard = c;
				}
			}else{
				if(c.compareTo(retCard)==-1 && c.compareTo(highCard)==-1){
					retCard = c;
				}
			}
		}
		if(retCard!=null){
			return retCard;
		}else{
			return null;
		}
	}
	

}

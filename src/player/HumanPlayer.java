package player;

import java.util.List;

import classes.Card;

public class HumanPlayer extends Player {

	
	public HumanPlayer(String name, String userId) {
		super(name, userId);
	}

	@Override
	public void meldTricks(int nrOfTricksMelded, int nrOfCards, List<Player> userList) {
		this.setTricksMelded(nrOfTricksMelded);
		
	}
	
	

	@Override
	public Card playCard(int cardPos, int firstSuit, Card highCard) {
		//TODO check color of card
		Card c=this.getCardsToPlay().get(cardPos);
		System.out.println("Try to play card: "+c.getSuit()+" "+c.getValue());
		if(firstSuit!=0){
			if(this.hasCardOfSuit(firstSuit)){
				if(c.getSuit()!=firstSuit){
					//has to follow suit but doesn't
					c= null;
				}
			}
		}
		//remove card from cards to play
		if(c!=null){
			
			//add "zeroCard" instead of played card
			this.getCardsToPlay().set(this.getCardsToPlay().indexOf(c), new Card(-1,-1)); 
			this.getPlayedCards().add(c);
			//this.getCardsToPlay().remove(c);
			System.out.println(this.getCardsToPlay());
			
		}
		return c;
	}
	




}

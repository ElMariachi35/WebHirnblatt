package player;

import java.util.ArrayList;
import java.util.List;

import classes.Card;

public class ComputerPlayerMathematica extends ComputerPlayer {

	public ComputerPlayerMathematica(String name, String userId) {
		super(name, userId);
	}
	
	private double getProbability(Card card){
		double prob=0;

		
		switch (card.getSuit()){
		case 1:
			switch(card.getValue()){
				case 2:
					prob=0;
					break;
				case 3:
					prob=0;
					break;
				case 4:
					prob=0;
					break;
				case 5:
					prob=0;
					break;
				case 6:
					prob=0;
					break;
				case 7:
					prob=0;
					break;
				case 8:
					prob=0;
					break;
				case 9:
					prob=0;
					break;
				case 10:
					prob=1.19;
					break;
				case 11:
					prob=2.4;
					break;
				case 12:
					prob=5.2;
					break;
				case 13:
					prob=10.4;
					break;
				case 14:
					prob=41.37;	
					break;
			}
			break;
		case 2:
			switch(card.getValue()){
				case 2:
					prob=1.2;
					break;
				case 3:
					prob=1.2;
					break;
				case 4:
					prob=1.2;
					break;
				case 5:
					prob=1.3;
					break;
				case 6:
					prob=1.3;
					break;
				case 7:
					prob=1.5;
					break;
				case 8:
					prob=1.5;
					break;
				case 9:
					prob=2;
					break;
				case 10:
					prob=2.8;
					break;
				case 11:
					prob=3.9;
					break;
				case 12:
					prob=6.4;
					break;
				case 13:
					prob=14;
					break;
				case 14:
					prob=43.88;	
					break;
			}
			break;
		case 3:
			switch(card.getValue()){
				case 2:
					prob=2.4;
					break;
				case 3:
					prob=2.4;
					break;
				case 4:
					prob=2.7;
					break;
				case 5:
					prob=3;
					break;
				case 6:
					prob=3.4;
					break;
				case 7:
					prob=4;
					break;
				case 8:
					prob=4.8;
					break;
				case 9:
					prob=5.9;
					break;
				case 10:
					prob=7.6;
					break;
				case 11:
					prob=12.6;
					break;
				case 12:
					prob=18.6;
					break;
				case 13:
					prob=28.2;
					break;
				case 14:
					prob=54.1;
					break;	
			}	
			break;
		case 4:
			switch(card.getValue()){
				case 2:
					prob=14.4;
					break;
				case 3:
					prob=18.9;
					break;
				case 4:
					prob=24.44;
					break;
				case 5:
					prob=30.65;
					break;
				case 6:
					prob=38.43;
					break;
				case 7:
					prob=46.80;
					break;
				case 8:
					prob=62.08;
					break;
				case 9:
					prob=70.28;
					break;
				case 10:
					prob=76.43;
					break;
				case 11:
					prob=82.76;
					break;
				case 12:
					prob=88.42;
					break;
				case 13:
					prob=93.42;
					break;
				case 14:
					prob=100.0;	
					break;
			}
			break;
		}
		
		return prob;
	}
	
	public void meldTricks(int nrOfTricksMelded, int nrOfCards, List<Player> userList){
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(nrOfCards == 1){
			//highest Card of other players
			Card highCard=null;
			for(Player p : userList){
				if(!p.equals(this)){
					if(highCard==null){
						highCard = p.getCardsToPlay().get(0);
					}else{
						if(p.getCardsToPlay().get(0).compareTo(highCard)==1){
							highCard = p.getCardsToPlay().get(0);
						}
					}
				}
			}
			//if highest card of others is higher than diamond 8 computer melds 0 else 1
			if(highCard.compareTo(new Card(3,8))==1){
				this.setTricksMelded(0);
			}else{
				this.setTricksMelded(1);
			}
		}else{
			double prob = 0;
			for(Card c : this.getCardsToPlay()){
				prob = prob+this.getProbability(c);
			}
		
			this.setTricksMelded((int)Math.round(prob/100));		
		}
	}
	
	@Override
	public Card playCard(int cardPos, int firstSuit, Card highCard) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		Card c;//=null;
		ArrayList<Card> followSuitCards; 
		ArrayList<Card> cardsToPlay=this.getCardsToPlay();
		
		if(firstSuit==0){
			if(this.needsTrick()){
				if(this.getHighCard(cardsToPlay).getValue()>=13){
					c=getHighCard(cardsToPlay);
				}else{
					c=getLowCard(cardsToPlay);
				}
			}else{
				c=getLowCard(cardsToPlay);
			}
		}else{
			followSuitCards = new ArrayList<Card>();
			for(Card ca : this.getCardsToPlay()){
				if(ca.getSuit()==firstSuit){
					followSuitCards.add(ca);
				}
			}
			
			if(followSuitCards.isEmpty()){
				//can play any card
				if(this.needsTrick()){
					if(this.getHighCard(cardsToPlay).getValue()>=13){
						c=getHighCard(cardsToPlay);
					}else{
						c=getLowCard(cardsToPlay);
					}
				}else{
					c=getLowCard(cardsToPlay);
				}
			}else{
				//has to follow suit
				if(this.needsTrick()){
					if(this.couldTakeTrick(highCard,followSuitCards)){
						c=getHighCard(followSuitCards);
					}else{
						c=getLowCard(followSuitCards);
					}
				}else{
					if(this.couldAvoidTrick(highCard,followSuitCards)){
						c=this.getHighestCardThatAvoidsTrick(highCard,followSuitCards);
					}else{
						c=getLowCard(followSuitCards);
					}
				}
			}
		}
		
		this.getCardsToPlay().remove(c);
		this.getPlayedCards().add(c);
		return c;
		
	}
	
	

}

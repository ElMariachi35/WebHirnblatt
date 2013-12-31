package classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;




import messages.Message;
import messages.ServerMessage;
import messages.ServerMessageCard;
import messages.ServerMessageCards;
import player.HumanPlayer;
import player.Player;
import websocket.ClientConnectionManager;

public class Round {
	private List<Player> userList;
	private Player dealer;
	private int nrOfCards;
	private Player activePlayer;
	private int playerCount=0;
	private int miniRoundCounter=0;
	
	private int tricksMeldedHelp=0;
	private Card highCard=null;
	
	private int firstSuit=0;
	
	private String gameId="";
	private ArrayList<User> humanPlayerList=null;
	
	
	public Round(String gameId, ArrayList<User> humanPlayerList, int nrOfCards, List<Player> userList, Player dealer) {
		// TODO: clear table

		this.userList = userList;
		this.nrOfCards = nrOfCards;
		//this.nrOfCards=8;
		this.dealer = dealer;
		this.gameId=gameId;
		this.humanPlayerList=humanPlayerList;


		//the first acitve player is next to the dealer
		this.activePlayer = userList.get((userList.indexOf(dealer)+1)%userList.size());
		dealCards();

	}

	public void dealCards() {
		Deck d = new Deck();
		for (int i = 0; i < userList.size(); i++) {
			// determine player after dealer
			Player p = userList.get((userList.indexOf(dealer) + i)
					% userList.size());
			// deal cards
			for (int j = 0; j < nrOfCards; j++) {
				p.addCardToCardToPlay(d.pop());
			}
			
			p.sortCardsToPlay();
			
			if (p.getClass().isAssignableFrom(HumanPlayer.class)) {
				if (nrOfCards != 1) {
					ServerMessageCards m = new ServerMessageCards("eX", gameId, p.getUserId(), p.getCardsToPlay());
					ClientConnectionManager.sendToUser(p.getUserId(), m);
				}
			}
		}
		if (nrOfCards == 1) {
			for (Player p : userList) {
				Map<String, Card> cardOthers = new TreeMap<String, Card>();
				if (p.getClass().isAssignableFrom(HumanPlayer.class)) {
					for (int i = 0; i < userList.size(); i++) {
						if (!p.getUserId().equals(userList.get(i).getUserId())) {
							cardOthers.put(userList.get(i).getUserId(),
									userList.get(i).getCardsToPlay().get(0));
						}
					}
					ServerMessageCards m = new ServerMessageCards("e1",gameId, p.getUserId(), cardOthers);
					ClientConnectionManager.sendToUser(p.getUserId(), m);
				}
			}
		}
		System.out.println("++++ gameId: "+gameId + " - dealt cards!)");
	}
	
	public void meldTricks(){
		
		if(playerCount==this.userList.size()){
			//reset player count 
			this.playerCount=0;
			playCards();
			return;
		}
		
		//activte user 
		if(playerCount<this.userList.size()){
			Message m = new Message("f", gameId, activePlayer.getUserId(), this.nrOfCards+"");
			if(this.activePlayer.getClass().isAssignableFrom(HumanPlayer.class)){
				//ClientConnection.broadcast(userList, m);
				ClientConnectionManager.broadcastToList(humanPlayerList, m);
			}else{
				System.out.println("Computer players turn");
				
				ClientConnectionManager.broadcastToList(humanPlayerList, m);
				
				//TODO: make checkSynchronize static
				
				this.activePlayer.meldTricks(-1, this.nrOfCards, this.userList);	//calculates tricks in it's class
				
				
				m = new Message ("g", gameId, this.activePlayer.getUserId(), this.activePlayer.getTricksMelded()+"");
				//System.out.println("Tricks: " +this.userList.get(this.userList.indexOf(activePlayer)).getTricksMelded());
				ClientConnectionManager.broadcastToList(humanPlayerList, m);
				
				this.playerCount++;
				this.activePlayer = userList.get((userList.indexOf(this.activePlayer)+1)%userList.size());
			}
		}		
	}
	
	
	public void setTricksMelded(String userId, int nrOfTricksMelded){
		for(Player p : userList){
			if(p.getUserId().equals(userId)){
				p.meldTricks(nrOfTricksMelded, nrOfCards, this.userList);
				Message m = new Message ("g", gameId, p.getUserId(), p.getTricksMelded()+"");
				ClientConnectionManager.broadcastToList(humanPlayerList, m);
				this.playerCount++;
				this.activePlayer = userList.get((userList.indexOf(this.activePlayer)+1)%userList.size());
			}
		}	
	}
	
	public void playCards(){
		System.out.println("PlayerCount: "+playerCount);
		if(playerCount==this.userList.size()){
			for(Player p : userList){
				if(p.hasCard(highCard)){
					//add trick
					p.addTricksTaken();
					
					//set activePlayer
					this.activePlayer=p;
					
					//reset
					this.playerCount=0;
					this.firstSuit=0;
					this.highCard=null;
					this.miniRoundCounter++;
					
					if(miniRoundCounter<this.nrOfCards){
						//this.playCards();
						Message m = new Message("j", gameId, p.getUserId(), p.getTricksTaken()+"");
						
						try {
							Thread.sleep(1200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						ClientConnectionManager.broadcastToList(humanPlayerList, m);
						return;
					}else{
						//last miniRound
						Message m = new Message("k", gameId, p.getUserId(), p.getTricksTaken()+"");
						
						try {
							Thread.sleep(1200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						ClientConnectionManager.broadcastToList(humanPlayerList, m);
						return;
					}
					
				}
			}
			
			return;
		}
		
		if(playerCount<this.userList.size()){
			Message m = new Message("h", gameId, activePlayer.getUserId());
			if(this.activePlayer.getClass().isAssignableFrom(HumanPlayer.class)){
				ClientConnectionManager.broadcastToList(humanPlayerList, m);
				if(nrOfCards == 1){
					try {
						Thread.sleep(750);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Card ca = this.activePlayer.playCard(0, firstSuit, highCard);	//plays a card
					
					if(firstSuit==0){
						firstSuit = ca.getSuit();
					}
					
					//check and set highCard
					if(highCard==null){
						highCard = ca;
					}else{
						if(highCard.compareTo(ca)<0){
							highCard=ca;
						}
					}
					
					ServerMessageCard sm = new ServerMessageCard ("i", gameId, this.activePlayer.getUserId(), ca);
					ClientConnectionManager.broadcastToList(humanPlayerList, sm);
					
					this.playerCount++;
					this.activePlayer = userList.get((userList.indexOf(this.activePlayer)+1)%userList.size());
				}
			}else{
				System.out.println("Computer players turn");
				
				ClientConnectionManager.broadcastToList(humanPlayerList, m);
				
				//TODO: make checkSynchronize static
				
				Card ca = this.activePlayer.playCard(-1, firstSuit, highCard);	//plays a card
				
				if(firstSuit==0){
					firstSuit=ca.getSuit();
				}
				
				//check and set highCard
				if(highCard==null){
					highCard = ca;
				}else{
					if(highCard.compareTo(ca)<0){
						highCard=ca;
					}
				}
				
				ServerMessageCard sm = new ServerMessageCard ("i", gameId, this.activePlayer.getUserId(), ca);
				ClientConnectionManager.broadcastToList(humanPlayerList, sm);
				
				this.playerCount++;
				this.activePlayer = userList.get((userList.indexOf(this.activePlayer)+1)%userList.size());
			}
		}	
	}
	
	
	public void setPlayedCard(String userId, int cardId) {
		cardId = cardId - 1;
		Card ca;

		for (Player p : userList) {
			if (p.getUserId().equals(userId)) {
				ca = p.playCard(cardId, firstSuit, highCard);
				if (ca == null) {
					cardId=-1;
					ServerMessage m = new ServerMessageCard("i", gameId, p.getUserId(), ca, cardId+"");
					ClientConnectionManager.sendToUser(p.getUserId(), m);
				}else{
					if (firstSuit == 0) {
						firstSuit = ca.getSuit();
					}

					// check and set highCard
					if (highCard == null) {
						highCard = ca;
					} else {
						if (highCard.compareTo(ca) < 0) {
							highCard = ca;
						}
					}

					ServerMessage m = new ServerMessageCard("i",gameId, p.getUserId(), ca, (cardId+1)+"");
					ClientConnectionManager.broadcastToList(humanPlayerList, m);
					this.playerCount++;
					this.activePlayer = userList.get((userList.indexOf(this.activePlayer) + 1) % userList.size());
				}
			}
		}
	}
	
	
	public Map endRound(){
		//public Result endRound()
		//calculate Result
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Integer> result = new TreeMap<String, Integer>();
		
		for(Player p : userList){
			if(p.getTricksMelded()==p.getTricksTaken()){
				result.put(p.getUserId(), p.getTricksMelded()+4);
			}else{
				if(p.getTricksMelded()>p.getTricksTaken()){
					result.put(p.getUserId(), (p.getTricksMelded()+4)*-1);
				}else{
					result.put(p.getUserId(), (p.getTricksTaken()+4)*-1);
				}
			}
			
			//reset
			p.resetAfterRound();
			
		}
		
		return result;
	}
}

package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import player.ComputerPlayerMathematica;
import player.HumanPlayer;
import player.Player;
import websocket.ClientConnectionManager;
import messages.Message;
import messages.ServerMessage;
import messages.ServerMessageCard;
import messages.ServerMessageDealer;
import messages.ServerMessageResult;
import messages.ServerMessageUser;


public class Game {

	private final int MAX_MEMBER_COUNT = 4;
	private final int MAX_NR_OF_CARDS = 8;
	
	
	private String gId;
	private String host;
	private boolean isStarted = false;
	private int nrOfCards = 0;
	private int roundNr=0;
	private Player dealer;
	private Round round;
	private Map<String, Integer> gameResult;
	
	//This list can be accessed from outside the gameObject, and contains the whole userObject
	private ArrayList<User> playerList = new ArrayList<User>();
	
	//This list is used intern contains the all userObjects as HumanPlayers and the missing nr. of ComputerPlayer
	private ArrayList<Player> internPlayerList = new ArrayList<Player>();
	
	//This map is used to sync all player after a server message is sent
	private Map<String, Boolean> syncPlayers = new TreeMap<String, Boolean>();
	
	public Game(){
		this.setgId(Math.random()+"");
	}

	
	public void receiveMessage(Message m){
		System.out.println("**** '"+m.getMessageId()+"' GameMessage received (gId: "+m.getMessage1()+"): m1:'"+m.getMessage1()+"', m2:'"+m.getMessage2()+"', m3:'"+m.getMessage3()+"', m4:'"+m.getMessage4()+"'");
		String playerId="";
		switch(m.getMessageId()){
		case "250":
			this.init();
			break;
		case "2": //determine Dealer
			playerId=m.getMessage2();
			
			this.updateSyncList(playerId, true);
			if(checkSynchronized()){
				resetSyncList();
				determineFirstDealer();
			}
			break;
		case "3": //set Dealer
			playerId=m.getMessage2();
			updateSyncList(playerId, true);
			if(checkSynchronized()){
				resetSyncList();
				this.setDealer();
			}
			break;
		case "4": //start Round
			playerId=m.getMessage2();
			updateSyncList(playerId, true);
			if(checkSynchronized()){
				resetSyncList();
				startRound();
			}
			break;
		case "5": //start melding tricks
			playerId=m.getMessage2();
			updateSyncList(playerId, true);
			if(checkSynchronized()){
				resetSyncList();
				round.meldTricks();
			}
			break;

		case "6": //continue melding tricks
			playerId=m.getMessage2();
			updateSyncList(playerId, true);
			if(checkSynchronized()){
				resetSyncList();
				round.meldTricks();
			}
			break;
		case "7": //set melded tricks of humanPlayer
			playerId=m.getMessage2();
			int nrOfTricksMelded = Integer.parseInt(m.getMessage3());
			round.setTricksMelded(playerId, nrOfTricksMelded);
			break;
		case "8": //start playing cards
			playerId=m.getMessage2();
			updateSyncList(playerId, true);
			if(checkSynchronized()){
				resetSyncList();
				round.playCards();
			}
			break;
		case "9": //set played card of humanPlayer
			playerId=m.getMessage2();
			round.setPlayedCard(playerId, Integer.parseInt(m.getMessage3()));
				break;
		case "10": //continue playing cards
			playerId=m.getMessage2();
			updateSyncList(playerId, true);
			if(checkSynchronized()){
				resetSyncList();
				round.playCards();
			}
			break;
		case "11": 
			playerId=m.getMessage2();
			updateSyncList(playerId, true);
			if(checkSynchronized()){
				resetSyncList();
				endRound();
			}
			break;
		case "12": 
				playerId=m.getMessage2();
				updateSyncList(playerId, true);
				if(checkSynchronized()){
					resetSyncList();
					setDealer();
				}
				break;
//			case "60":
//				Message chatMessage = new Message("mm", mObj.getMessage1(), mObj.getMessage2());
//				ClientConnection.broadcast(connectedClients, chatMessage);
//				break;		
		default:
			System.out.println("-- Could not identify messageId: "+m.getMessageId());
		}
	
	}
	
	
	private synchronized void updateSyncList(String playerId, boolean value){
		this.syncPlayers.put(playerId, value);	
	}
	
	private synchronized boolean checkSynchronized(){
		for(Boolean b : this.syncPlayers.values()){
			if(b==false){
				return false;
			}
		}
		System.out.println("++++ gameId: "+this.gId + " - all players are synchronized");
		return true;
	}
	
	private synchronized void resetSyncList(){
		for(User p : playerList){
			this.syncPlayers.put(p.getId(), false);
		}
	}
	
	public void addPlayer(User p){
		this.playerList.add(p);
	}
	
	public void removePlayer(User p){
		this.playerList.remove(p);
	}
	
	private void init(){
		
		//add user as HumanPlayer to internPlayerList
		for(User u : playerList){
			String uId = u.getId();
			String username = u.getUsername();
			internPlayerList.add(new HumanPlayer(username, uId));
		}
		
		
		//count User that are in the game and calculate nr of needed CPU-Player
		int missingNr = this.MAX_MEMBER_COUNT-playerList.size();
		for(int i=0;i<missingNr;i++){
			internPlayerList.add(new ComputerPlayerMathematica("Com "+(i+1), UUID.randomUUID().toString()));
		}
		
		Collections.shuffle(internPlayerList);
		
		for(Player p : internPlayerList){
			int seatPos = internPlayerList.indexOf(p);
			p.setSeatPos(seatPos);
		}
		
		//init syncPlayer Map
		for(User u : playerList){
			this.syncPlayers.put(u.getId(), false);
		}
		
		//broadcast
		ClientConnectionManager.broadcastToList(playerList, new ServerMessageUser("b",this.gId, this.internPlayerList));

		System.out.println("++++ gameId: "+this.gId + " is initialized");
		
	}
	
	private void determineFirstDealer(){
		sleep(1500);
		
		Deck d = new Deck();
		Card highCard=null;
		
		for (Player p : this.internPlayerList){
			Card c = d.pop();
			if(highCard == null){
				highCard = c;
				this.dealer=p;
				//broadcast
				ServerMessage m = new ServerMessageCard("c", this.gId, p.getUserId(), c);
				ClientConnectionManager.broadcastToList(playerList,m);
			}else{
				
				if(c.compareTo(highCard) >0){
					highCard = c;					
					this.dealer=p;
				}
				ServerMessage m = new ServerMessageCard("c", this.gId, p.getUserId(), c);
				ClientConnectionManager.broadcastToList(playerList,m);
			}
			
			//Delays output of cards
			this.sleep(500);
		}
	}
	
	private void setDealer(){
		this.sleep(300);
		System.out.println("++++ gameId: "+this.gId + " - "+dealer.getName()+" is the new dealer!");
		//broadcast userId of dealer
		ServerMessage m = new ServerMessageDealer("d", this.gId, this.dealer.getUserId());
		ClientConnectionManager.broadcastToList(playerList,m);
			
		this.sleep(500);
	}
	
	
	
	private void startRound(){
		if(roundNr < this.MAX_NR_OF_CARDS*2){
			if(roundNr<this.MAX_NR_OF_CARDS){	//nr of cards increase
				this.nrOfCards++;
			}else{
				this.nrOfCards--;
			}
			roundNr++;
			
			if(roundNr==this.MAX_NR_OF_CARDS*2){
				System.out.println("++++ gameId: "+this.gId + " has ended!");
			}else{
				System.out.println("++++ gameId: "+this.gId + " - RoundNr.: "+roundNr+" has started ("+nrOfCards+" Cards)");
				//new Round
				round = new Round(this.gId, this.playerList,this.nrOfCards, this.internPlayerList, this.dealer);
			}
		}		
	}
	
	private void endRound(){
		//print results
		Map roundResult = round.endRound();
		this.addResultToGameResult(roundResult);
		
		ServerMessage m = new ServerMessageResult("l", this.gId, this.gameResult, this.nrOfCards);
		ClientConnectionManager.broadcastToList(playerList,m);
		
		//set Dealer for next Round
		this.dealer=this.internPlayerList.get((this.internPlayerList.indexOf(dealer)+1)%this.MAX_MEMBER_COUNT);
	}
	
	
	private void addResultToGameResult(Map<String, Integer> roundResult) {
		if (this.gameResult == null) {
			this.gameResult = roundResult;
		} else {
			for (String user : this.gameResult.keySet()) {
				int value = this.gameResult.get(user);
				value = value + roundResult.get(user);
				this.gameResult.put(user, value);
			}
		}
	}
	
	
	/*
	 * ### GETTER AND SETTER METHODS
	 */
	
	
	public String getgId() {
		return gId;
	}

	public void setgId(String gId) {
		this.gId = gId;
	}
	
	
	public User getPlayer(String userId){
		for(User u: playerList){
			if(u.getId().equals(userId)){
				return u;
			}
		}
		return null;
	}
	
	public boolean hasPlayer(String userId){
		for(User u: playerList){
			if(u.getId().equals(userId)){
				return true;
			}
		}
		return false;
	}
	

	public ArrayList<User> getPlayerList(){
		return this.playerList;
	}
	
	
	public String[] getPlayerNames(){
		ArrayList<String> n = new ArrayList<String>();
		for(User u : playerList){
			n.add(u.getUsername());
		}
		return n.toArray(new String[n.size()]);
	}

	public String[] getPlayerId(){
		ArrayList<String> n = new ArrayList<String>();
		for(User u : playerList){
			n.add(u.getId());
		}
		return n.toArray(new String[n.size()]);
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	private void sleep(long milisecond){
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

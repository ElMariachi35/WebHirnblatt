package websocket;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import login.UserBean;
import login.UserDAO;
import messages.Message;
import messages.ServerMessageOpenGame;
import messages.ServerMessageUser;

import org.codehaus.jackson.map.ObjectMapper;

import classes.Game;
import classes.User;

public class ClientConnectionManager {

	private static Map<String, ClientConnection> connectedClients = new TreeMap<String, ClientConnection>();
	public static ObjectMapper mapper = new ObjectMapper();
	private static ArrayList<User> userList = new ArrayList<User>();
	public static ArrayList<Game> gameList = new ArrayList<Game>();
	
		
	/*
	 * ### CLIENT - SERVER COMMUNICATION ###
	 */
	/**
	 * Whenever a client receives a message this method gets called. According to the parameter "MessageId"
	 * it executes the corresponding method.
	 * @param c The ClientConnection-object that received the message
	 * @param m	The JSON-String of the message. It is mapped into a Message-Object that includes the attributes:
	 * 			"MessageId", "Message1", "Message2", "Message 3", "Message 4";
	 * 			For retrieving those attributes there are getter Methods.
	 * @throws IOException
	 */
	public static synchronized void onMessage(ClientConnection c, CharBuffer m) throws IOException{
		String mStr = m.toString();
		System.out.println("** Message received: "+mStr);
		Message mObj = mapper.readValue(mStr, Message.class);
		
		
		switch(mObj.getMessageId()){
		case "200":
			authenticateClientConnection(c, mObj);
			break;
		case "210":
			openGame(mObj.getMessage1());
			break;
		case "220":
			joinGame(mObj.getMessage1(), mObj.getMessage2());
			break;
		case "221":
			leaveGame(mObj.getMessage1(), mObj.getMessage2());
			break;
		case "230":
			closeGame(mObj.getMessage1(), mObj.getMessage2());
			break;
		case "250":
			startGame(mObj.getMessage1(), mObj);
			break;
		case "260":
			endInGame(mObj.getMessage1());
		default:
			distributeGameMessage(mObj.getMessage1(), mObj);
		}
	}
	
	/**
	 * A message to all Users in the userList is sent
	 * @param message
	 */
	public static synchronized void broadcastToAllUser(Object message){
		for(User u : userList){
			ClientConnection c = u.getClCon();
			try {
				c.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * A message to all Users in the forwarded list (uList) is sent
	 * @param uList
	 * @param message
	 */
	public static synchronized void broadcastToList(ArrayList<User> uList, Object message){
		for(User u : uList){
			ClientConnection c = u.getClCon();
			try {
				c.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * A message to the user with the given userId is sent
	 * @param userId id of the user to receive the message
	 * @param message
	 */
	public static synchronized void sendToUser(String userId, Object message){
		for(User u : userList){
			if(u.getId().equals(userId)){
				try {
					u.getClCon().sendToClient(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static synchronized void distributeGameMessage(String gameId, Message message){
		Game g = getGame(gameId);
		
		if(g!=null){
			g.receiveMessage(message);
		}else{
			System.out.println("-- gameId: '"+gameId+"' could not be found in gameList");
		}
	}
	
	
	/*
	 * ### LOBBY METHODS ###
	 */

	public static synchronized void authenticateClientConnection(ClientConnection c, Message m){
		String id = m.getMessage1();
		
		//check if user is already logged in
		for(User u : userList){
			if(id.equals(u.getId())){
				try {
					c.sendToClient(new Message("101", "x"));
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		UserBean u = UserDAO.getUserById(id);
		if(u.isValid()){
			User user = new User(u.getId(), u.getUsername(), c);
			addUserToUserList(user);
		}
	}
	
	public static void addUserToUserList (User u){
		userList.add(u);
		ArrayList<String> usernames = new ArrayList<String>();
		for(User us : userList){
			usernames.add(us.getUsername());
		}
		
		//send list of usernames to all user
		broadcastToAllUser(new ServerMessageUser("100", null, usernames));
		
		//send user all currently open games
		if(!gameList.isEmpty()){
			for(Game g : gameList){
				//if game is already started game will not be sent
				if(!g.isStarted()){
					sendToUser(u.getId(), new ServerMessageOpenGame("110", g.getgId(), g.getPlayerNames(), g.getPlayerId()));
				}
			}			
		}
		
		System.out.println("++ User '"+ u.getUsername()+"' has joined the Lobby");
	}
	
	
	public static synchronized void removeClient(ClientConnection c){
		User userToDelete=null;
		
		for(User u : userList){
			if(u.getClCon().equals(c)){
				userToDelete = u;
				break;
			}
		}
		
		if(userToDelete!=null){
			//remove from userList
			userList.remove(userToDelete);
			Game gameToDelete=null;
			//remove from games
			for(Game g : gameList){
				if(g.hasPlayer(userToDelete.getId())){
					if(g.isStarted()){
						endInGame(userToDelete.getId());
					}else{
//						g.removePlayer(userToDelete);
//						System.out.println("++ User '"+ userToDelete.getUsername()+"' has successfully left gameId: '"+g.getgId()+"'");
//						broadcastToAllUser(new ServerMessageOpenGame("115", g.getgId(), g.getPlayerNames(), g.getPlayerId(), userToDelete.getId()));
						
						if(g.getHost().equals(userToDelete.getId())){
							gameToDelete=g;
						}else{
							leaveGame(g.getgId(), userToDelete.getId());
						}
					}
				}
			}
			
			if(gameToDelete!=null){
				closeGame(gameToDelete.getgId(), userToDelete.getId());
			}
			
			ArrayList<String> usernames = new ArrayList<String>();
			for(User us : userList){
				usernames.add(us.getUsername());
			}
			
			//send list of usernames to all user
			broadcastToAllUser(new ServerMessageUser("100", null,  usernames));
			
			System.out.println("++ User '"+userToDelete.getUsername()+"' has successfully left the lobby");
		}
	}
	
	
	/**
	 * When a user clicks on "Neues Spiel" this method gets called. At first there will be checked
	 * if the user participates already in a game, if not a new game will be opened and shown in the gameList
	 * container in the lobby
	 * @param uId The id of the user that wants to start a game
	 */
	public static synchronized void openGame(String uId){
		//check if user participates
		boolean ok=true;
		String gId="-1";
		for(Game ga : gameList){
			if(ga.hasPlayer(uId)){
				ok=false;
				gId = ga.getgId();
				break;
			}
		}
		
		if(ok){		
			//add game to gameList
			Game g = new Game();
			if(getUser(uId)!=null){
				g.addPlayer(getUser(uId));
				g.setHost(uId);
				gameList.add(g);
			}
			
			//notify all connected clients
			System.out.println("++ Player '"+uId+"' has successfully opened gameId: "+g.getgId());
			broadcastToAllUser(new ServerMessageOpenGame("110", g.getgId(), g.getPlayerNames(), g.getPlayerId()));
		}else{
			
			//TODO notify player that it is not possible to open the game, because he already has joined a game
			System.out.println("-- Player '"+ uId + "' trys to open a new game, but he is already participating in gameId: "+ gId);
		}
	}
	
	public static synchronized User getUser	(String uId){
		for(User u : userList){
			if(u.getId().equals(uId)){
				return u;
			}
		}
		return null;
	}
	
	/**
	 * A user tries to join a game. At first there will be checked if user doesn't participate in a game already
	 * and if the game has a free seat
	 * if not the user will be added to the game
	 * @param gameId
	 * @param userId
	 */
	public static synchronized void joinGame(String gameId, String userId){
		//check if user participates in other game
		boolean ok=true;
		String gId="-1";
		for(Game ga : gameList){
			if(ga.hasPlayer(userId)){
				ok=false;
				gId = ga.getgId();
				break;
			}
		}
		
		Game g = getGame(gameId);
		
		//check if user ok and g is available and seat open
		if(ok && g!=null && g.getPlayerList().size()<4 ){
			//add user to game
			g.addPlayer(getUser(userId));
			
			//Server log
			System.out.println("++ User '"+ getUser(userId).getUsername()+"' has successfully joined gameId: '"+gameId+"'");
			
			//notify clients
			broadcastToAllUser(new ServerMessageOpenGame("115", gameId, g.getPlayerNames(), g.getPlayerId(), userId));
		}else{
			//TODO: Notify client that join is not possible
			System.out.println("-- User '"+ getUser(userId).getUsername()+"' could not join gameId: '"+gameId+"'");

		}
	}
	
	/**
	 * This method gets called, if a user has joined a game in the lobby an clicks on "Verlassen", so he tries to leave the game
	 * @param gameId
	 * @param userId
	 */
	public static synchronized void leaveGame(String gameId, String userId){	
		Game g = getGame(gameId);
		
		if(g!=null){
			User u = g.getPlayer(userId);
			if(u!=null){
				if(!g.isStarted()){
					g.removePlayer(u);
					if(getUser(userId)!=null){
						System.out.println("++ User '"+ getUser(userId).getUsername()+"' has successfully left gameId: '"+gameId+"'");
					}
					broadcastToAllUser(new ServerMessageOpenGame("115", gameId, g.getPlayerNames(), g.getPlayerId(), userId));
				}
			}
		}
	}
	
	public static synchronized void closeGame(String gameId, String userId){
		Game g = getGame(gameId);
		
		if(g!=null){
			if(g.hasPlayer(userId)){
				gameList.remove(g);
				if(getUser(userId)!=null){
					System.out.println("++ User '"+ getUser(userId).getUsername()+"' has successfully closed gameId: '"+gameId+"'");	
				}
				broadcastToAllUser(new ServerMessageOpenGame("130", gameId));
			}
		}
		
	}
	
	
	public static synchronized void startGame(String gameId, Message m){
		Game g = getGame(gameId);
		
		if(g!=null && !g.isStarted()){
			g.setStarted(true);
			broadcastToAllUser(new ServerMessageOpenGame("149", g.getgId()));
			
			distributeGameMessage(gameId, m);
		}
	}
	
	
	public static synchronized void endInGame(String userId){
		Game g=null;;
		String username;
		for(Game x : gameList){
			if(x.hasPlayer(userId)){
				g=x;
			}
		}
		
		if(g!=null && g.isStarted()){
			username = g.getPlayer(userId).getUsername();	
			
			gameList.remove(g);
			
			Message m = new Message("160", username);
			ArrayList<User> playerList = g.getPlayerList();
			
			//Remove user that closed the game from the list
			if(playerList!=null && g.getPlayer(userId)!=null){
				playerList.remove(g.getPlayer(userId));	
			}
			
			
			
			broadcastToList(playerList, m);
			
			g=null;
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * ### SETTER AND GETTER METHODS ###
	 */
	public static synchronized Game getGame(String gameId){
		for(Game g: gameList){
			if(g.getgId().equals(gameId)){
				return g;
			}
		}
		return null;
	}
}

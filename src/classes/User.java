package classes;

import websocket.ClientConnection;

public class User {
	private String id;
	private String username;
	private ClientConnection clCon;
	
	public User (String id, String username, ClientConnection clCon){
		this.setId(id);
		this.setUsername(username);
		this.setClCon(clCon);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setClCon(ClientConnection c){
		this.clCon=c;
	}

	public ClientConnection getClCon(){
		return this.clCon;
	}
}

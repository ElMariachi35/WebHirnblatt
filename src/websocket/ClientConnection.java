package websocket;

import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;



import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.codehaus.jackson.map.ObjectMapper;

public class ClientConnection extends MessageInbound {

	WsOutbound ob;
	private String tempID ="";
	
	@Override
	public void onOpen(WsOutbound outbound) {
		this.ob = outbound;
		
		//Generate unique Id for client
		//String uId=UUID.randomUUID().toString();
		//setTempID(uId);
		//ClientConnectionManager.addClient(uId, this);
		//ClientConnectionManager.authenticateClientConnection(this, uId);
		/*
		 * WebSocket authentication
		 * 0. generate temporary uuid and map it to clientconnection
		 * 1. send temporary uuid to client
		 * 2. client sends username and temporary uuid to server
		 * 3. server looks in db for username and retrieves id
		 * 4. server overwrites temporary uuid with id
		 * 5. server sends id to client
		 * 6. client overwrites temporary uuid with id
		 */
		
	}
	
	 @Override
	 public void onClose(int status){
		 ClientConnectionManager.removeClient(this);
	 }
	

	@Override
	protected void onBinaryMessage(ByteBuffer message) throws IOException {
		// this application does not expect binary data
		throw new UnsupportedOperationException("Binary message not supported.");
	}

	@Override
	protected void onTextMessage(CharBuffer m) throws IOException {
		ClientConnectionManager.onMessage(this, m);
	}

	public void sendToClient(Object m) throws IOException {
		if(this!=null){
			ob.writeTextMessage(CharBuffer.wrap(ClientConnectionManager.mapper.writeValueAsString(m)));
			System.out.println("** Sent to client: "+ClientConnectionManager.mapper.writeValueAsString(m));
		}
	}
	
	
//	
//	public static void broadcast(Map<String, ClientConnection> connectedClients, Object m){
//		for(Map.Entry<String, ClientConnection> e : connectedClients.entrySet()){
//			 try {
//				e.getValue().sendToClient(e.getValue(), m);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		 }
//	}

	public String getTempID() {
		return tempID;
	}

	public void setTempID(String tempID) {
		this.tempID = tempID;
	}
	

}

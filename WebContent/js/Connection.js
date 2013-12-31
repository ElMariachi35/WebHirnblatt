var Connection = (function() { //constructor

	//var wsURL = "ws://localhost:8080/LoginExample/connect";
	var m=null;
	
	//url for connecting to cloudbees
	var wsURL = "ws://webhirnblatt.elm35.cloudbees.net/connect";
	
	
	//var wsURL = "ws://10.0.0.5:8080/MultiWebHirnblatt/connect";
	
	var websocket=null;
	
	//### WEBSOCKET FUNCTIONS
	function _connect() {
		websocket = new WebSocket(wsURL);
		websocket.onopen = function(evt) { _onOpen(evt) };
		websocket.onmessage = function(evt) { _onMessage(evt) };
		websocket.onclose = function(evt) { _onClose(evt) };
		
	}
	
	function _onOpen(evt){
		console.log("Client: Connected");
	}
	
	function _onMessage(evt){	
		var mObj = JSON.parse(evt.data);
		console.log('RECEIVED message ('+mObj.messageId+'): '+evt.data); 
		switch(mObj.messageId){
		case "100":
			//new user is online
			$('#playerList').empty();
			var userlist = mObj.userList;
			for(var i=0;i<userlist.length;i++){
				$('#playerList').append("<span class='playerListItem'>"+userlist[i]+"</span><br />");
			}
			
			//create lobby object
			lobby = new Lobby();
			
			
			break;
		case "101":
			alert("Dieser User ist bereits eingeloggt");
			c.redirectToLogin();
			break;
		case "110":
			lobby.receiveOpenGame(mObj.gId, mObj.playerNames, mObj.playerId);
			break;
			
		case "115":
			lobby.receiveUpdatedGame(mObj.gId, mObj.playerNames, mObj.playerId, mObj.userId);
			break;
		case "130":
			lobby.receiveCloseGame(mObj.gId);
			break;
		case "149":
			lobby.receiveCloseGame(mObj.gId);
			break;
		case "160":
			lobby.receiveEndInGame(mObj.message1);
			break;
		case "b":
			m = new Main(mObj.gameId);
			//full Reset of table
			g.fullResetTable(4);
			lobby.showGameWindow();
			m.setPlayerNames(mObj.userList);
			break;
		case "c":
			if(mObj.gameId==m.getGameId()){
				m.playCardDealer(mObj.userId, mObj.card);
				break;
			}
		case "d":
			if(mObj.gameId==m.getGameId()){
				m.setDealer(mObj.dealer);
				break;
			}
		case "e1":
			if(mObj.gameId==m.getGameId()){
				m.receiveCards(mObj.userId, mObj.cardsMap, true);
			}
			break;
		case "eX":
			if(mObj.gameId==m.getGameId()){
				m.receiveCards(mObj.userId, mObj.cards, false);
			}
			break;
		case "f":
			if(mObj.message1==m.getGameId()){
				m.meldTricks(mObj.message2, mObj.message3);
			}
			break;
		case "g":
			if(mObj.message1==m.getGameId()){
				m.updateMeldTricks(mObj.message2, mObj.message3);
			}
			break;
		case "h":
			if(mObj.message1==m.getGameId()){
				m.setActivePlayer(mObj.message2);
			}
			break;
		case "i":
			if(mObj.gameId==m.getGameId()){
				m.playCard(mObj.userId, mObj.card, mObj.cardId);
			}
			break;
		case "j":
			if(mObj.message1==m.getGameId()){
				m.endMiniRound(mObj.message2, mObj.message3);
			}
			break;
		case "k":
			if(mObj.message1==m.getGameId()){
				m.endRound(mObj.message2, mObj.message3);
			}
			break;
		case "l":
			if(mObj.gameId==m.getGameId()){
				m.printResult(mObj.result, mObj.nrOfCards);
			}
			break;
//		case "z":
//			m.endGame(mObj.message1);
//			break;
//		case "mm":
//			m.receiveChatMessage(mObj.message1, mObj.message2);
//			break;
//			
		}
	}
	
	function _onClose(evt){
		
	}
	
	//### OTHER FUNCTIONS
	_connect();
	
	
	return {
		sendMessage : function(message){
			if(websocket.readyState==1){
				websocket.send(JSON.stringify(message));
				console.log("SENT to Server: "+JSON.stringify(message));
			}
		},
		

		meldTricks : function(cardId){
			m.sendMeldedTricksToServer(cardId);
		},
		
		playCard : function(cardId){
			m.sendCardToServer(cardId);
		},
		
		redirectToLogin : function(){
			window.location="http://webhirnblatt.elm35.cloudbees.net/";
		},
		
	};
});

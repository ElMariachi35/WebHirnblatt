
var Lobby = (function() { //constructor
	
	function _toggleJoinButton(gameId){
		var jb = document.getElementById(gameId+'joinButton');
		if(jb.getAttribute('value')=="Beitreten"){
			jb.setAttribute('value', 'Verlassen');
		}else{
			jb.setAttribute('value', 'Beitreten');
		}
	}
	
	
	return {
		logout : function(){
			c.redirectToLogin();
		},
		
		openGame : function(){
			c.sendMessage({messageId: "210", message1: UID});
		},
		
		
		receiveOpenGame : function(gameId, playerNames, playerId){
			var player =new Array("Spieler1", "Spieler2", "Spieler3", "Spieler4");
			
			
			for(var i=0;i<playerNames.length;i++){
				player[i]=playerNames[i];
			}
			
			var gameListElementHost = $('	<div id="'+gameId+'" class="gameListElement">\
											<div class="gameListElementHeader">\
												<div id="'+gameId+'Name" class="gameListElementName">'+playerNames[0]+'s Hirnblattspiel</div>\
												<div id="'+gameId+'Id" class="gameListElementId">'+gameId+'</div>\
											</div>\
											<div class="gameListElementPlayer">\
												<div id="'+gameId+'p1" class="gameListElementPlayerElement1">'+player[0]+'</div>\
												<div id="'+gameId+'p2" class="gameListElementPlayerElement2">'+player[1]+'</div>\
												<div id="'+gameId+'p3" class="gameListElementPlayerElement3">'+player[2]+'</div>\
												<div id="'+gameId+'p4" class="gameListElementPlayerElement4">'+player[3]+'</div>\
											</div>\
											<input type="button" class="gameListElementStartButton" value="Start" onClick="lobby.startGame('+gameId+')"></input>\
											<input type="button" class="gameListElementEndButton" value="Beenden" onClick="lobby.closeGame('+gameId+')"></input>\
										</div>');
			
			
			var gameListElementNoHost = $('	<div id="'+gameId+'" class="gameListElement">\
					<div class="gameListElementHeader">\
						<div id="'+gameId+'Name" class="gameListElementName">'+playerNames[0]+'s Hirnblattspiel</div>\
						<div id="'+gameId+'Id" class="gameListElementId">'+gameId+'</div>\
					</div>\
					<div class="gameListElementPlayer">\
						<div id="'+gameId+'p1" class="gameListElementPlayerElement1">'+player[0]+'</div>\
						<div id="'+gameId+'p2" class="gameListElementPlayerElement2">'+player[1]+'</div>\
						<div id="'+gameId+'p3" class="gameListElementPlayerElement3">'+player[2]+'</div>\
						<div id="'+gameId+'p4" class="gameListElementPlayerElement4">'+player[3]+'</div>\
					</div>\
					<input id="'+gameId+'joinButton" type="button" class="gameListElementJoinButton" value="Beitreten" onclick="lobby.joinGame('+gameId+')"></input>\
				</div>');
			
			//check if host or not
			if(UID == playerId[0]){
				gameListElementHost.appendTo('#gameList');
			}else{
				gameListElementNoHost.appendTo('#gameList');
			}	
		},
		
		joinGame : function(gameId){
			var j = document.getElementById(gameId+'joinButton');
			if(j.getAttribute('value')=="Beitreten"){
				c.sendMessage({messageId: "220", message1: gameId, message2: UID});		
			}else{
				c.sendMessage({messageId: "221", message1: gameId, message2: UID});	
			}
		},
		
		/*
		 * userId: is the player that changed
		 */
		receiveUpdatedGame : function(gameId, playerNames, playerId, userId){
			var player =new Array("Spieler1", "Spieler2", "Spieler3", "Spieler4");
			
			
			for(var i=0;i<playerNames.length;i++){
				player[i]=playerNames[i];
			}
			
			for(var i=0;i<player.length;i++){
				document.getElementById(gameId+'p'+[i+1]).innerHTML=player[i];
				
			}
			
			//change "Beitreten"-button to "Verlassen"-button
			
			if(UID!=playerId[0] & userId==UID){
				_toggleJoinButton(gameId);
			}
		},
		
		closeGame : function(gameId){
			c.sendMessage({messageId: "230", message1: gameId, message2: UID});		
		},
		
		receiveCloseGame : function(gameId){
			//console.log($('#'+gameId));
			var gameDiv = document.getElementById(gameId);
			gameDiv.parentNode.removeChild(gameDiv);
			
		},
		
		startGame : function(gameId){
			//g.emptyResult();
			c.sendMessage({messageId: "250", message1: gameId, message2: UID});
		},
		
		showGameWindow: function(){
			g.fullResetTable(4);
			g.emptyResult();
			$('.overlay').fadeIn(300);
			$('#gameMain').fadeIn(300);
		},
		
		endInGame : function(){
			c.sendMessage({messageId: "260", message1:UID});
			$('#gameMain').fadeOut(300);
			$('.overlay').fadeOut(300);
		},
		
		receiveEndInGame : function(username){
			alert("Das Spiel muss leider beendet werden. Spieler "+username+" hat das Spiel veralssen");
			$('#gameMain').fadeOut(300);
			$('.overlay').fadeOut(300);
		}
	};
});

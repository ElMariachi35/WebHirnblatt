var Main =  (function(gameId) { //constructor
	var _user;
	var _userId;
	var _gameId=gameId;
	var _seatPos;//=parseInt(seatPos);
	
	const MAX_MEMBER_COUNT=4;
	
	var _userList=[];
	
	var _recCardCounter=0;
	
	var active=false;
	var checkMeldTricks = false;

	
	function init(){
		_userId = UID;
	}
	
	init();
	
	function getSeatPosForUser(userId){
		for(var i=0;i<MAX_MEMBER_COUNT;i++){
			if(userId==_userList[i].userId){
				return parseInt(_userList[i].seatPos);
			}
		}
		return -1;
	}
	
	function getNameFromId(userId){
		console.log(_userList);
		for(var i=0;i<MAX_MEMBER_COUNT;i++){
			var user = _userList[i];
			if(user.userId == userId){
				return user.username;
			}
		}
		console.log(userId);
		return -1;
		
	}
	
	
	return {
		setPlayerNames : function(userList){
			console.log(userList);

			for(var i=0;i<MAX_MEMBER_COUNT;i++){
				if(userList[i].userId == _userId){
					_userList[0] = {username: userList[(i)%MAX_MEMBER_COUNT].name, userId: userList[(i)%MAX_MEMBER_COUNT].userId, seatPos:1 };
					_userList[1] = {username: userList[(i+1)%MAX_MEMBER_COUNT].name, userId: userList[(i+1)%MAX_MEMBER_COUNT].userId, seatPos:2};
					_userList[2] = {username: userList[(i+2)%MAX_MEMBER_COUNT].name, userId: userList[(i+2)%MAX_MEMBER_COUNT].userId, seatPos:3};
					_userList[3] = {username: userList[(i+3)%MAX_MEMBER_COUNT].name, userId: userList[(i+3)%MAX_MEMBER_COUNT].userId, seatPos:4};
				}
			}
			
			for(var i=0;i<MAX_MEMBER_COUNT;i++){
				g.outputSetPlayerName("p"+(i+1)+"Name", _userList[i].username);
			}
			
			//TODO createResults
			g.outputCreateResultTable(_userList);
			console.log(_userList);
			
			//Game has startet, deactivate startButton for every Player
			//g.outputDeactivateStartButton();
			
			c.sendMessage({messageId:"2", message1:_gameId, message2:_userId});
		},
		
		playCardDealer : function (userId, card){
			g.outputCard(getSeatPosForUser(userId), card);
			_recCardCounter++;
			//all 4 cards received
			if(_recCardCounter==4){
				c.sendMessage({messageId: "3", message1:_gameId, message2: _userId});
				_recCardCounter=0;
			}
		
		},
		
		setDealer : function(userId){
			for(var i=0;i<MAX_MEMBER_COUNT;i++){
				g.outputClearDealerButton(i+1);
			}
			
			
			var sPos = getSeatPosForUser(userId);
			g.outputSetDealerButton(sPos);
			c.sendMessage({messageId: "4", message1: _gameId, message2: _userId});

		},
		
		receiveCards : function(userId, cards, checkOneCard){
			for(var i=0;i<MAX_MEMBER_COUNT;i++){
				g.outputClearCenter(i+1);
			}
			
			var cardArr=[];
			if(checkOneCard){
				//special case for one
				for(var key in cards){
					if(cards.hasOwnProperty(key)){
						ca = new Card(cards[key].suit, cards[key].value);
						cardArr.push(ca);
						g.outputCard(getSeatPosForUser(key), ca);
					}
				}
			}else{
				for (var key in cards) {
					if (cards.hasOwnProperty(key)) {
						var ca = new Card(cards[key].suit, cards[key].value);
						cardArr.push(ca);
					}
				}
				g.outputDealCards(cardArr);
			}
			c.sendMessage({messageId: "5", message1: _gameId ,message2: _userId});
		},
		
		meldTricks : function(userId, nrOfCards){
			if(userId == _userId){
				//its my turn
				//g.outputAddGlowingBorder(getSeatPosForUser(userId));
				this.setActivePlayer(userId);
			
				checkMeldTricks = true;
				
				for(var i=0;i<=nrOfCards;i++){
					g.outputShowMeldTricksButton(i);	
				}
			
			}else{
				//someone elses turn
				//g.outputAddGlowingBorder(getSeatPosForUser(userId));
				this.setActivePlayer(userId);
			}
		},
		
		updateMeldTricks : function(userId, nrOfTricks){
			g.outputSetTricksMelded(getSeatPosForUser(userId), nrOfTricks);
			c.sendMessage({messageId: "6", message1: gameId, message2: _userId});
			g.outputRemoveGlowingBorder(getSeatPosForUser(userId));
		},
		

		sendMeldedTricksToServer : function(nrOfTricks) {
			if (active) {

				for ( var i = 0; i < 9; i++) {
					g.outputHideMeldTricksButton(i);
				}
				
				checkMeldTricks=false;
				active=false;
				c.sendMessage({
					messageId : "7",
					message1 : gameId,
					message2 : _userId,
					message3 : nrOfTricks
				});
			}
		},
		
		setActivePlayer : function(userId){
			g.outputAddGlowingBorder(getSeatPosForUser(userId));
			
			if(userId == _userId){
				active=true;

				if(hasFocus==false){
					$.titleAlert("Du bist dran!", {
					    requireBlur:false,
					    stopOnFocus:true,
					    interval:700
					});	
				}
				
			}
		},
		
		playCard : function(userId, card, cardId){
			
			//cardId = -1: user didn't follow suit
			if(userId==_userId && cardId==-1){
				alert("Farbzwang");
				this.setActivePlayer(userId);
			}else{	
				g.outputCard(getSeatPosForUser(userId), card);
				g.outputRemoveGlowingBorder(getSeatPosForUser(userId));
				if(userId==_userId){
					g.outputHideCard("p1c"+cardId);
				}
				if(active && userId==_userId){

					active=false;
				}
				
				c.sendMessage({messageId: "8", message1: gameId, message2: _userId});	
			}
		},
		
		sendCardToServer : function(cardId){
			if(active && !checkMeldTricks){
					//g.outputHideCard("p1c"+cardId);
					c.sendMessage({messageId: "9", message1: gameId, message2: _userId, message3:cardId});
					active=false;
			}
		},
		
		endMiniRound : function(userId, tricksTaken){
			g.outputSetTricksTaken(getSeatPosForUser(userId), tricksTaken);
			for(var i=0;i<MAX_MEMBER_COUNT;i++){
				g.outputClearCenter(i+1);
			}
			c.sendMessage({messageId: "10", message1: gameId, message2: _userId});
		},
		
		endRound : function(userId, tricksTaken){
			g.outputSetTricksTaken(getSeatPosForUser(userId), tricksTaken);
			for(var i=0;i<MAX_MEMBER_COUNT;i++){
				g.outputClearCenter(i+1);
			}
			c.sendMessage({messageId: "11", message1: gameId, message2: _userId});
		},
		
		printResult : function(result, nrOfCards){
			console.log(result);
			g.outputAddResultRow(result, nrOfCards, _userList);
			//TODO
			c.sendMessage({messageId: "12", message1: gameId, message2: _userId});
			
			//reset Table
			g.fullResetTable(MAX_MEMBER_COUNT);
			
			
		},
		
		
		startGame : function(){
			$('#chatContainer').show();
			c.sendMessage({messageId: "1.5"});
		},
		
		/**
		 * param reason is the userId of the player who caused the termination of the game
		 */
		endGame : function(reason){
			var username
			for(var i=0;i<MAX_MEMBER_COUNT;i++){
				if(_userList[i].userId==reason){
					username = _userList[i].username;
				}
			}
			
			alert("Das Spiel wurde beendet.\nGrund: "+username + " hat das Spiel verlassen.\nBitte melden Sie sich erneut an" );
			
			setTimeout(function(){
				window.location.reload(true);
			}, 500);
			
			//g.fullResetTable(MAX_MEMBER_COUNT);
		},
		
		updateClientList : function(clients){
//			console.log(clients);
//			$.each(clients, function(userId, name) {   
//			     $('#connections')
//			         .append($("<option></option>")
//			         .attr("value",userId)
//			         .text(name)); 
//			});
			$('select option').remove();
			
			$.each(clients, function (i, client) {
			
				
			    $('#connections').append($('<option>', { 
			        value: client.userId,
			        text : client.name 
			    }));
			});
		},
		
		sendChatMessage : function(){
			var message = $("#chatTextField").val();
			$('#chatTextField').val("");
			
			if(message!=null || message!=""){
				c.sendMessage({messageId: "60", message1: _userId, message2: message});
			}
		},
		
		receiveChatMessage: function(userId, message){
			var username = getNameFromId(userId);
			g.outputAddChatMessage(username, message);
		},
		
		getGameId : function(){
			return _gameId;
		}
		

		
		
		
		
		
	};
});




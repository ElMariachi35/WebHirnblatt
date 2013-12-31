var Global = (function() {//constructor
		
	return {
		getCardPath : function(card) {
			var s, v;

			switch(card.suit) {
				case 1:
					s = 'c';
					break;
				case 2:
					s = 's';
					break;
				case 3:
					s = 'd';
					break;
				case 4:
					s = 'h';
					break;
			}

			switch(card.value) {
				case 14:
					v = '1';
					break;
				case 2:
					v = '2';
					break;
				case 3:
					v = '3';
					break;
				case 4:
					v = '4';
					break;
				case 5:
					v = '5';
					break;
				case 6:
					v = '6';
					break;
				case 7:
					v = '7';
					break;
				case 8:
					v = '8';
					break;
				case 9:
					v = '9';
					break;
				case 10:
					v = 't';
					break;
				case 11:
					v = 'j';
					break;
				case 12:
					v = 'q';
					break;
				case 13:
					v = 'k';
					break;
			}
			return "hb_images/" + s + v + ".png";
		},
		
		//#####################
		//### SET FUNCTIONS ###
		//#####################
		outputSetPlayerName: function(id, name){
			$('#'+id).html(name);
		},
		
		outputCard : function(id, card){
			var cardPath = g.getCardPath(card);
			$('#p'+id+"CenterCard").attr("src", cardPath);
			
		},
		
		outputSetDealerButton : function(id){
			$('#p'+id+'DealerButton').attr("src","hb_images/dealerButton.png");
		},
		
		outputDealCards : function(cards){
			for(var i=0;i<cards.length;i++){
				var c = cards[i], 
				imgId = "#p1c" + (i + 1),
				cardPath = g.getCardPath(c);
				
				$(imgId).attr("src", cardPath);
				$(imgId).show();
			}
		},
		
		outputAddGlowingBorder : function(id){
//			if(id==1){
//				$('#p'+id+'Controls').addClass("glowing-border");
//			}else{
//				$('#player'+id).addClass("glowing-border");	
//			}
			
			switch(id){
			case 1:
				$('#p'+id+'Controls').addClass("glowing-border-player1");
				break;
			case 2:
				$('#player'+id).addClass("glowing-border-player2");	
				break;
			case 3:
				$('#player'+id).addClass("glowing-border-player3");	
				break;
			case 4:
				$('#player'+id).addClass("glowing-border-player4");	
				break;
			}
		
		},
		
		outputShowMeldTricksButton : function(buttonId){
			$('#meldTricksImg'+buttonId).show();
		},
		
		outputSetTricksMelded : function(id, nrOfTricks){
			$('#p'+id+'TricksMelded').html(nrOfTricks);
		},
		
		outputSetTricksTaken : function(id, nrOfTricks){
			$('#p'+id+'TricksMade').html(nrOfTricks);
		},
		
		outputCreateResultTable:function(member){
			// create table
		    var $table = $('<table id="tblResults">');
		    // caption
		    $table.append('<caption><b>Ergebnis</b></caption>')
		    // thead
		    .append('<thead>').children('thead')
			.append('<tr />').children('tr').append('<th>K</th><th>'+member[0].username+'</th><th>'+member[1].username+'</th><th>'+member[2].username+'</th><th>'+member[3].username+'</th>')
		    		
		    //tbody
		    var $tbody = $table.append('<tbody />').children('tbody');

		    // add table to dom
		    $table.appendTo('#results');
		},
		
		outputAddResultRow: function(result, nrOfCards, userList){
			for(var i=0;i<userList.length;i++){
				for(var j=0;j<result.length;j++){
					//if(userList[i].username == result.)
				}
			}
			$('#tblResults tr:last').after('<tr><td>'+nrOfCards+'</td><td>'+result[userList[0].userId]+'</td><td>'+result[userList[1].userId]+'</td><td>'+result[userList[2].userId]+'</td><td>'+result[userList[3].userId]+'</td></tr>');
		},
		
		outputAddChatMessage: function(username, message){
			$('#chat').append('<span><b>'+username+'</b>: '+ message+'</span><br />');
		},
		
		//#####################
		//###CLEAR FUNCTIONS###
		//#####################
		outputClearDealerButton : function(id){
			$('#p'+id+'DealerButton').attr("src","hb_images/transparentDealerButton.png");
		},
		
		outputClearCenter : function(id){
			//$('#p'+id+'CenterCard').attr("src", null);
			$('#p'+id+'CenterCard').attr("src", "hb_images/transparentCard.png");
			
		},
		
		outputClearTricksMelded : function(id){
			$('#p'+id+'TricksMelded').html(" ");
		},
		
		outputClearTricksTaken : function(id){
			$('#p'+id+'TricksMade').html(" ");
		},
		
		outputHideMeldTricksButton : function(buttonId){
			$('#meldTricksImg'+buttonId).hide();
		},
		
		outputHideCard : function(cardId){
			$('#'+cardId).hide();
		},
		
		outputRemoveGlowingBorder : function(id){
//			if(id==1){
//				$('#p'+id+'Controls').removeClass("glowing-border");
//			}else{
//				$('#player'+id).removeClass("glowing-border");		
//			}
			
			switch(id){
			case 1:
				$('#p'+id+'Controls').removeClass("glowing-border-player1");
				break;
			case 2:
				$('#player'+id).removeClass("glowing-border-player2");	
				break;
			case 3:
				$('#player'+id).removeClass("glowing-border-player3");	
				break;
			case 4:
				$('#player'+id).removeClass("glowing-border-player4");	
				break;
			}
	
		},
		
		fullResetTable : function(nrOfPlayer){
			for(var i=1;i<nrOfPlayer+1;i++){
				this.outputClearDealerButton(i);
				this.outputClearCenter(i);
				this.outputClearTricksMelded(i);
				this.outputClearTricksTaken(i);
				this.outputRemoveGlowingBorder(i);
			}
			
			for(var i=0;i<9;i++){
				this.outputHideMeldTricksButton(i);
				this.outputHideCard('p1c'+i);
			}
			
			
		},
		
		emptyResult: function(){
			$('#results').empty();
		},
		
		outputMeldTricksUser: function(nrOfTricks){
			$('#tricksMelded').html(nrOfTricks);
		},
		
		outputDeactivateStartButton : function(){
			$('#startGameButton').attr('disabled', 'disabled');
		},
		
		//output
//		outputDealCards : function(members,oneCard) {
//			//only cards of player 1 are shown;
//			
//			if(oneCard){
//				// $("#p1c1").attr("src", "hb_images/b1fv.png");
//				// $("#p1c1").show();
//// 				
//				// for(var i=0;i<members.length;i++){
//					// if(!members[i].getPlayerType()){
//// 						
//					// }
//				// }//
//				
//			}else{
//				var m = members[0], cards = m.getCardsToPlay();
//				for (var i = 0; i < cards.length; i++) {
//				
//	
//					var c = cards[i], 
//					imgId = "#p1c" + (i + 1),
//					cardPath = g.getCardPath(c);
//					
//					$(imgId).attr("src", cardPath);
//					$(imgId).show();
//				}
//			}
//		},
		
//		outputPlayCard : function(playerPos, player,card, cardPos){
//			if(!player.getPlayerType()){
//				//computer
//				//p2CenterCard
//				var cardPath = g.getCardPath(card);
//				$('#p'+(playerPos+1)+"CenterCard").attr("src", cardPath);
//			}else{
//				//user
//				var cardPath = g.getCardPath(card);
//				$('#p'+(playerPos+1)+"CenterCard").attr("src", cardPath);
//				
//				//karte wegschmeißen
//				$('#p'+(playerPos+1)+cardPos).hide();	
//			}
//		},
		
		outputDetermineDealerCard : function(card, id){
			var cardPath = g.getCardPath(card);
			
			$('#'+id+'CenterCard').attr("src", cardPath); 
			
		},
		

		

		


		

		

		/**
		*@param cardId : id attribut of the img-object, e.g. 'p1c1'
		*/

	
		
		idToNr : function(id){
			return parseInt(id.charAt(1));
		},
		

		
		
		compare: function(a, b){
			if (a.suit < b.suit)
				return -1;
			else if (a.suit > b.suit)
			   return 1;
			else{
				if(a.value < b.value){
					return -1;
				}else if(a.value>b.value){
					return 1;
				}else{
					return 0;
				}			}
		}
	}
});

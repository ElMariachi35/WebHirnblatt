<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>

	<link rel="stylesheet" type="text/css" href="styles/lobbystyles.css">
	<link rel="stylesheet" type="text/css" href="styles/globalstyles.css">
		<link rel="stylesheet" type="text/css" href="styles/gamestyles.css">
	<script type="text/javascript" src="js/Connection.js"></script>
	<script type="text/javascript" src="js/Lobby.js"></script>	
	<script type="text/javascript" src="js/Main.js"></script>
	<script type="text/javascript" src="js/Global.js"></script>
	<script type="text/javascript" src="js/Card.js"></script>
	
	
	
	<script src="js/plugins/css_browser_selector.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/plugins/jquery.titlealert.js"></script>
	
	<script>
		var g = new Global();
		
		var hasFocus=true;
		
		function onBlur() {
			hasFocus=false;
		};
		
		function onFocus(){
			hasFocus=true;
		};

		if (/*@cc_on!@*/false) { // check for Internet Explorer
			document.onfocusin = onFocus;
			document.onfocusout = onBlur;
		} else {
			window.onfocus = onFocus;
			window.onblur = onBlur;
		}
		
	</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hirnblatt - Lobby</title>
</head>
<body>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>

	<div class="overlay"></div>
	<div class="lobbyBorder bgLightBlue">
		<div id="lobbyMain" class="lobbyMain bgLightBlue">
			<div id="lobbyLogo"></div>
			
			<div id="header">
				<div id="buttonHeader">
					<input type="button" value="Logout" id="logoutButton" onclick="lobby.logout()"></input>
					<div id="usernameHeader">Benutzer: ${username }</div>
					<br />
					<input type="button" value="Neues Spiel" id="newGameButton" onclick="lobby.openGame()"></input>
				</div>
			</div>
			
			<div id="playerListContainer">
				<span>Online:</span>
				<div id="playerList" class="lobbyElement">
				</div>
			</div>
			
			
			
			
			<div id="gameListContainer">
				<span id="marginGameListHeader">Spiele:</span>
				<div id="gameList" class="lobbyElement">
	
				</div>
			</div>
			<div id="lobbyChatContainer">
				<div id="lobbyChat" class="lobbyElement">
					Auﬂer Betrieb
				</div>
				<input type="text" id="lobbyChatInput"></input>
				<input type="button" id="lobbyChatButton" value="Senden"></input>
			</div>
		</div>
	</div>
	
	
	<div id="gameMain" class="bgLightBlue">
				<div id="endGameButton" class="lobbyElement" onClick="lobby.endInGame()">X</div>
				<div id="results"></div>
				<div id="player1" class="player1">
					<div id="p1Controls" class="lobbyElement">
						<div class="controls-name" id="p1Name">Spieler 1</div>
						<div class="controls-text">gesagt:</div>
						<div id="p1TricksMelded" class="controls-output-text-fields">0</div>
						<div class="controls-text">gemacht:</div>
						<div id="p1TricksMade" class="controls-output-text-fields">0</div>
						
					</div>	
					<div id="p1Cards">
						<img src="hb_images/b1fv.png" id="p1c1" onclick="c.playCard('1')">
						<img src="hb_images/b1fv.png" id="p1c2" onclick="c.playCard('2')">
						<img src="hb_images/b1fv.png" id="p1c3" onclick="c.playCard('3')">
						<img src="hb_images/b1fv.png" id="p1c4" onclick="c.playCard('4')">
						<img src="hb_images/b1fv.png" id="p1c5" onclick="c.playCard('5')">
						<img src="hb_images/b1fv.png" id="p1c6" onclick="c.playCard('6')">
						<img src="hb_images/b1fv.png" id="p1c7" onclick="c.playCard('7')">
						<img src="hb_images/b1fv.png" id="p1c8" onclick="c.playCard('8')">
					</div>
				</div>
				<div id="player2" class="lobbyElement">
					<div id="p2Controls" class="controls-computerPlayer">
						<div class="controls-name" id="p2Name">Spieler 2</div>
						<div class="controls-text">gesagt:</div>
						<div id="p2TricksMelded" class="controls-output-text-fields">0</div>
						<div class="controls-text">gemacht:</div>
						<div id="p2TricksMade" class="controls-output-text-fields">0</div>
					</div>
				</div>
				<div id="player3" class="lobbyElement">
					<div id="p3Controls" class="controls-computerPlayer">
						<div class="controls-name" id="p3Name">Spieler 3</div>
						<div class="controls-text">gesagt:</div>
						<div id="p3TricksMelded" class="controls-output-text-fields">0</div>
						<div class="controls-text">gemacht:</div>
						<div id="p3TricksMade" class="controls-output-text-fields">0</div>
					</div>
				</div>
				<div id="player4" class="lobbyElement">
					<div id="p4Controls" class="controls-computerPlayer">
						<div class="controls-name" id="p4Name">Spieler 4</div>
						<div class="controls-text">gesagt:</div>
						<div id="p4TricksMelded" class="controls-output-text-fields">0</div>
						<div class="controls-text">gemacht:</div>
						<div id="p4TricksMade" class="controls-output-text-fields">0</div>
					</div>
				</div>
				<div id="center">
					<img src="hb_images/b1fv.png" id="p3CenterCard">
					<img src="hb_images/b1fv.png" id="p2CenterCard">
					<img src="hb_images/b1fv.png" id="p4CenterCard">
					<img src="hb_images/b1fv.png" id="p1CenterCard">

				</div>
				
				<img id="p1DealerButton" src="hb_images/dealerButton.png">
				<img id="p2DealerButton" src="hb_images/dealerButton.png">
				<img id="p3DealerButton" src="hb_images/dealerButton.png">
				<img id="p4DealerButton" src="hb_images/dealerButton.png">
				
				<div id="meldTricksDiv">
					<img id="meldTricksImg0" class="meldTricksImg" src="hb_images/0.png" onclick="c.meldTricks(0)">
					<img id="meldTricksImg1" class="meldTricksImg" src="hb_images/1.png" onclick="c.meldTricks(1)">
					<img id="meldTricksImg2" class="meldTricksImg" src="hb_images/2.png" onclick="c.meldTricks(2)">
					<img id="meldTricksImg3" class="meldTricksImg" src="hb_images/3.png" onclick="c.meldTricks(3)">
					<img id="meldTricksImg4" class="meldTricksImg" src="hb_images/4.png" onclick="c.meldTricks(4)">
					<img id="meldTricksImg5" class="meldTricksImg" src="hb_images/5.png" onclick="c.meldTricks(5)">
					<img id="meldTricksImg6" class="meldTricksImg" src="hb_images/6.png" onclick="c.meldTricks(6)">
					<img id="meldTricksImg7" class="meldTricksImg" src="hb_images/7.png" onclick="c.meldTricks(7)">
					<img id="meldTricksImg8" class="meldTricksImg" src="hb_images/8.png" onclick="c.meldTricks(8)">
				</div>
				
				<div id="chatContainer">
			    	<div id="chatWindow">
			    		<div id="chat">
				    	</div>
			    	</div>
			    	
			    	<div id="chatInput">
			    		<input id="chatTextField" type="text"></input>
			    		<input id="chatButton" type="button" value="Senden" onclick="m.sendChatMessage()"></input>
			    	</div>
	    		</div>
				
			</div>
	
	
	
	<p id="id">${id}</p>
	
	
	<script>
		//hide gameWindow
		$('#gameMain').hide();
		$('.overlay').hide();
	
		var c = new Connection();
		
		
		var UID = $('#id').text();
		
		if(UID=="" || UID==null){
			alert("Sie sind nicht eingeloggt");
			c.redirectToLogin();
		}
		
		setTimeout(function(){
			c.sendMessage({messageId: "200", message1: UID});		
		},1000);
	
	</script>
	<c:remove var="username" scope="session" /> 
	<c:remove var="id" scope="session" /> 
</body>
</html>
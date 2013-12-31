<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
	<link rel="stylesheet" type="text/css" href="styles/indexstyles.css">

	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/plugins/sha256-min.js"></script>	
	<script src="js/plugins/css_browser_selector.js" type="text/javascript"></script>
	<script type="text/javascript" src="js/index.js"></script>
	
	
	<head>
		<title>Hirnblatt</title>
	</head>
		<body>
			<body>
				<div id="frontMain" class="bgLightBlue frontMain">
					<img id="logo" class="logoBig" src="img/logo_big.png">
					
<!-- 					<a href="single/single.html">
						<img id="singleButton" class="singleGameButton" src="img/single.png">
					</a> -->
						<img id="multiButton" class="multiGameButton" src="img/spielen.png" onClick="showLogin()">
					<form id="login" class="login" onsubmit="hashLoginPassword()" action="LoginServlet">
						<span class="loginText">Benutzername:</span>
						<input type="text" id="loginUsername" name="loginUsername"></input><br />
						<span class="loginText">Passwort:</span>
						<input type="password" id="loginPassword" name="loginPassword"></input>
						<a href="#" class="newUserText" onclick="showRegisterForm()">Neuer Benutzer?</a>
						<input type ="submit" value="Login" class="loginButton"></button>
					</form>
				</div>	
				<div class="overlay">
				</div>
				<form id="registerForm" class="login" onsubmit="return submitRegisterForm()" action="RegisterServlet">
						<span class="loginText">Benutzername:</span>
						<input type="text" id="regUser" name="regUser"></input>
						<span class="loginText">E-Mail:</span>
						<input type="text" id="regEmail" name="regEmail"></input>
						<span class="loginText">Passwort:</span>
						<input type="password" id="regPassword" name="regPassword"></input>
						<span class="loginText">Passwort:</span>
						<input type="password" id="regPassword1" name="regPassword1"></input>
					
					
					<input type ="submit" value="Registrieren" class="registerSubmit loginButton" id="submitRegister"></input>
					<input type ="button" value="Abbrechen" class="registerAbort loginButton" id="abortRegister" onClick="abortRegisterForm()"></input>
				</form>
				<div id="messageBox" class="messageBox">
					<div id="messageBoxHeader">Meldung</div>
					<div id="messageBoxInput">${message}</div>
					<div id="messageBoxButtonContainer">
						<input id="messageBoxButton" type="button" value="OK" onclick="closeMessageBox()"></input>
					</div>
				</div>
				
				<script type="text/javascript">
					$("#login").show();
					$(".overlay").show();
					$("#registerForm").hide();
					$("#messageBox").show();
				</script>
		</body>


</html>

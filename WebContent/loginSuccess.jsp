<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
	<link rel="stylesheet" type="text/css" href="styles/indexstyles.css">

	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
	<script src="js/plugins/css_browser_selector.js" type="text/javascript"></script>
	<script type="text/javascript" src="js/index.js"></script>
	
	<head>
		<title>Hirnblatt</title>
	</head>
		<body>
		<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
			<body>
				<div id="frontMain" class="bgLightBlue frontMain">
					<img id="logo" class="logoBig" src="img/logo_big.png">
					
					<a href="single/single.html">
						<img id="singleButton" class="singleGameButton" src="img/single.png">
					</a>
						<img id="singleButton" class="singleGameButton" src="img/multi.png" onClick="showLogin()">
					<form id="login" class="login" action="LoginServlet">
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
				<form id="registerForm" class="login">
					<span class="loginText">Benutzername:</span>
					<input type="text" id="registerUsername"></input>
					<span class="loginText">E-Mail:</span>
					<input type="text" id="email"></input>
					<span class="loginText">Passwort:</span>
					<input type="password" id="password1"></input>
					<span class="loginText">Passwort wiederholen:</span>
					<input type="password" id="password2"></input>
					<input type ="submit" value="Registrieren" class="registerSubmit loginButton" id="submitRegister" onClick="submitRegisterForm()"></input>
					<input type ="button" value="Abbrechen" class="registerAbort loginButton" id="abortRegister" onClick="abortRegisterForm()"></input>
				</form>
				<div id="messageBox" class="messageBox">
					<div id="messageBoxHeader"></div>
					<div id="messageBoxInput"></div>
					<div id="messageBoxButtonContainer">
						<input id="messageBoxButton" type="button" value="OK" onclick="closeMessageBox()"></input>
					</div>
				</div>
				
				<script type="text/javascript">
					$("#login").hide();
					//$(".overlay").hide();
					$("#registerForm").hide();
					$("#messageBox").hide();
					showLogin();
					showMessageBox("Login erfolgreich", "Sie werden nun weitergeleitet!");
					setTimeout(function(){
						window.location.href="http://localhost:8080/LoginExample/lobby.jsp";
					},500);
					
					
					$("#login").hide();
					//$(".overlay").hide();
					$("#registerForm").hide();
					$("#messageBox").hide();
					showLogin();
					showMessageBox("Login fehlgeschlagen", "Der Benutzername oder das Passwort ist ungültig!");
					
					
				</script>
		</body>


</html>

function showLogin(){
	//alert("Show login!");
	$("#login").fadeIn(300);
	$("#loginUsername").focus();
}



function showRegisterForm(){
	$("#regUser").val("");
	$("#regEmail").val("");
	$("#regPassword").val("");
	$("#regPassword1").val("");
	
	$(".overlay").fadeIn(300);
	$("#registerForm").fadeIn(300);
	$("#regUser").focus();
}

function abortRegisterForm(){
		$("#registerForm").hide();
		$(".overlay").hide();
		$("#registerUsername").val("");
		$("#email").val("");
		$("#password1").val("");
		$("#password2").val("");
}

function submitRegisterForm(){
		var pw1 = $("#regPassword").val();
		var pw2 = $("#regPassword1").val();
		var username = $("#regUser").val();
		var email = $("#regEmail").val();
		
		if((pw1 != pw2) || username == "" || email=="" || pw1==""){
			alert("Es sind nicht alle Felder korrekt ausgefüllt");
			$("#regUser").val("");
			$("#regEmail").val("");
			$("#regPassword").val("");
			$("#regPassword1").val("");
			return false;
		}else{
			
			hashRegisterPassword();
			
			$("#registerForm").hide();
			$(".overlay").hide();

			return true;
		}		
}

function showMessageBox(header, message){
	$("#messageBoxHeader").text(header);
	$("#messageBoxInput").text(message);
	$(".overlay").show();
	$("#messageBox").show();
	
}

function closeMessageBox(){
	$("#messageBoxHeader").text("");
	$("#messageBoxInput").text("");
	$(".overlay").hide();
	$("#messageBox").hide();
}

function hashLoginPassword(){
	$("#loginPassword").val((hex_sha256($("#loginPassword").val())));
}

function hashRegisterPassword(){
	$("#regPassword").val((hex_sha256($("#regPassword").val())));
	$("#regPassword1").val((hex_sha256($("#regPassword1").val())));
}
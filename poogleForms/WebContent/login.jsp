<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="Login" method="post">

		Username: <input id="usernameInputID" type="text" name="username">
		<br> Password: <input id="passwordInputID" type="password"
			name="password"> <br> <input id="submitButton"
			type="submit" value="Login">
	</form>
	<a href="UserSignUp">Sign Up?</a>
</body>

<script>
	$(document).ready(function() {
		$("#submitButton").click(function(e) {
			var username = $("#usernameInputID").val();
			var password = $("#passwordInputID").val();
			if(username==null || username==""){
				e.preventDefault();
				alert("Username mandotary");
			}
			else if(password==null || password==""){
				e.preventDefault();
				alert("password mandotary");
			}
			else if (!checkForUsernameAndPasswordPair(username, password)) {
				e.preventDefault();
				alert("Wrong username and password pair");
			} else {
				//e.preventDefault();
				alert("Login Sucessful");
			}
		});
	});
</script>
<script>
	function checkForUsernameAndPasswordPair(username, password) {
		var check = false;
		var data = $.ajax({
			async:false,
			type: 'POST',
			  url: "UsernameCheck",
			  data: {
					command : "checkForUsernameAndPasswordPair",
					username : username,
					password : password
				},	
			dataType : "text",
			success: function(postResultData){
				console.log(postResultData);
				if (postResultData == "false") {
					check = false;
					//alert("ugabuga2");
				} else if (postResultData == "true") {
					check = true;
					//alert("ugabuga1");
				} else {
					check = true;
					//alert("ugabuga");
				}
				console.log(check);
			},
			error: function(){
				alert("Some error, Try again");
			}
			
		});
		console.log(check);
		return check;
		
	}
</script>
</html>
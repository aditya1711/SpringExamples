<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<button onclick="goToDashBoard()">Take A Survey</button>
<button id="level2ClientButton" onclick="goToAdminPage()">BY
	THE POWER OF THEE</button>
<button onclick="goToAnswersPage()">View Answered Forms</button>
<script>
	$(document).ready(function(){
		var x= "${client.loginCredentials.type}";
		console.log(x);
		if(x != "LEVEL2"){
			$("#level2ClientButton").hide();
		};
	});
</script>
<script>
	function goToAnswersPage(){
		window.location.assign("ViewAnsweredForms");
	}
	function goToAdminPage(){
		window.location.assign("level2page.jsp");
	}
	function goToDashBoard(){
		window.location.assign("Dashboard");
	}
</script>
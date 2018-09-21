<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
Hi, ${client.firstName} ${client.lastName} Username:
${client.loginCredentials.username} Level:
${client.loginCredentials.type}
<form action="Logout" method="post">
	<!-- <button onclick="logout()" name="confirm" value="ture">Logout</button> -->
	<button name="confirm" value="true">Logout</button>
</form>

<script>
	function logout(){
		$.post("Logout",{confirm:"true"}, function(){window.location.assign("Checkout.jsp")});
	}
</script>
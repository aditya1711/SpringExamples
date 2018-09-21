<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/ui-darkness/jquery-ui.css"
	rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<title>createQ</title>
</head>
<body>
	<br>
	<div id="dialog">
		<form method="post" action="${callingPage}">
			<label>Question Prompt:</label> <input id="questionPrompt"
				name="questionPrompt" type="text">

			<p id="optionsDiv"></p>
			<button type="button" onclick="addOption('optionsDiv')">add
				option</button>
			<br> <input id="submit" type="submit" value="Submit">
		</form>
	</div>


</body>

<script>
	$(document).ready(function(){
		if('${currQuestion}' == ("textTypeQuestion")){
			$("options").hide();
		}
	});
	
	var optionNumber =0;
	function addOption(id){
		document.getElementById(id).append(document.createElement("br"));
		var i = document.createElement("INPUT");
		i.setAttribute("type", "text");
		i.setAttribute("name" , "option@"  + optionNumber++);
		document.getElementById(id).append(i);
	}
	
</script>

<script>
	$(document).ready(function() {
		$(function() {
			$("#dialog").dialog({
					autoOpen : false,
					modal:true,
					width:500,
					height:500
			});
			$("#button").on("click", function() {
				$("#dialog").dialog("open");
			});
		});
	});
</script>

</html>
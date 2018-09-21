<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="questionDiv${currQuestion.ID }">
	<p>Question: ${currQuestion.prompt}</p>

	<p>Answer:</p>
	<input type="text" name="${currQuestion.ID}" /> <BR>

	<c:if test="${not empty currAnswer }">
				Previous Answer: ${currAnswer.answers}
		</c:if>
	<br>
	<c:if
		test="${not empty callingPage && (client.loginCredentials.username == form.adminUsername || client.loginCredentials.username == formAdminUsername) && (callingPage == 'CreateForm_jsp' || callingPage == 'CreateQuestionServlet') }">
		<button id="deleteQuestionButton"
			onclick="deleteQuestion('${currQuestion.ID}' , 'questionDiv${currQuestion.ID }')">DELETE
			THIS QUESTION</button>
	</c:if>
</div>

<script>
	function deleteQuestion(questionID, questionDivID) {
		//console.log($("#questionDiv1001017").html());
		console.log($("#" + questionDivID).text());
		console.log("#" + questionDivID);
		//$("#" + questionDivID).hide();
		$.ajax({
			type:'POST',
			url: "CreateQuestion",
			data: {
				command : "deleteQuestion",
				questionID : questionID
			},
			error: function(xhr, status, error){
				alert(xhr.responseText);
			},
			success: function(data){
				$("#" + questionDivID).html(data);
				$("#" + questionDivID).hide(2000);
			}
		});
	}
</script>

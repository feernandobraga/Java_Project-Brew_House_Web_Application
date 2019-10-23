<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="ISO-8859-1">
		<title>New Post</title>
		
		<% /*import jstl*/ %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
		
		<% /*import tinymce*/ %>
		<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
		<script type="text/javascript" src="js/tinymce/tinymce.min.js"></script>

		<% /*tinymice config script*/ %>
		<script type="text/javascript">
		tinymce.init({
			selector: '#mytextarea'
		});


		</script>
	</head>
	<body>
		<h1>new Post</h1>
		<form name="insertPost" method="post">
			<h2>Title</h2> <input type="text" placeholder="Title" name="title"><br>
			<textarea id="mytextarea"></textarea>
	  		<p>Visible</p> <input type ="checkbox" name ="postVariability" checked = "checked"/>
		<input type="submit" value="submit">
	  </form>
	</body>

</html>
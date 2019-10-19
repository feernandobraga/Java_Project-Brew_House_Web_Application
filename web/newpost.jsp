<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>New Post</title>
	

	<% /*scripts*/ %>
		<script type="text/javascript" src="JS/JSLibraries/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="JS/JSLibraries/tinymce/js/tinymce/jquery.tinymce.min.js"></script>
	<script type="text/javascript" src="JS/JSLibraries/tinymce/js/tinymce/tinymce.min.js"></script>

	<script>
		tinymce.init({
		selector: '#mytextarea'
		});
	</script>	
	<% /*stylesheets*/ %>


	<% /*java imports*/ %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
	
</head>
<body>
<%/*menu*/%>


	<h1>new Post</h1>
	<form class="submitPost" action="newPost" method="post">
		<h2>Title</h2> <input type="text" placeholder="Title" name="title"><br>
		
		
		<%/*TODO implament catagories and get array from db*/ %>
		Select a Category:&nbsp;
		<select>
			<c:forEach items="${listCategory}" var="category">
        		<option value="${category.getCatagoryID()}">${category.getCategoryTitle()}</option>
    		</c:forEach>
		</select>
		<br/>
		
		<%/* tinymce overides mytextarea */%>
		<textarea id="mytextarea" name="mytextarea"></textarea><br>
		
		<input type="submit" value="submit">
	</form>


	
</body>
</html>
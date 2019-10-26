<%@ page import="blogpackage.model.dao.CategoryDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">

	<!-- <script src="/js/bootstrap.min.js"></script> -->
	<script src="https://kit.fontawesome.com/b4abea9736.js" crossorigin="anonymous"></script>
	<%----%>

	<% /* import tiny mce */ %>
	<script type="text/javascript" src="js/tinymce/jquery.tinymce.min.js"></script>
	<script type="text/javascript" src="js/tinymce/tinymce.min.js"></script>

	<script>
		tinymce.init({
			selector: 'textarea#default'
		});
	</script>
	<%----%>

	<%/*import JSTL*/%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%----%>

</head>
<body>
	<%--Header imported from header.jsp--%>
	<jsp:include page="header.jsp" />
	<%----%>

	<form action="/BlogServlet" method="post">
		<input type="hidden" name="action" value="insertPost">
		Post Title<input type="text" name="title" /></p>
		post visible?<input type="checkbox" name="ticked" value="checked"/> <br/>


		<% /* load data from db to dropdown menu*/
		CategoryDAO catDAO = new CategoryDAO();
		request.setAttribute("AllCategories", catDAO.SelectAllCategories());

        %>

		<%request.getParameter("selectAllCategories");%>

		<% //TODO why did it crash? %>
		<select name="category">
			<c:forEach var="tempCat" items="${AllCategories}">
				<option value="${tempCat.categoryID}">${tempCat.categoryTitle}</option>
			</c:forEach>
		</select>


		<% /* use tinymce */ %>
		<textarea id="default" name="content"></textarea>
		<%----%>
		<input type="submit" value="insert post">
	</form>
	<%--footer imported from footer.jsp--%>

	<jsp:include page="footer.jsp" />
</body>
</html>
<%@ page import="blogpackage.model.dao.CategoryDAO" %>
<%@ page import="blogpackage.model.dao.BlogPostDAO" %>
<%@ page import="blogpackage.model.bean.BlogPost" %>
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




	<div class="container">
		<div class="page-header">
			<h1>UPDATE A POST</h1>
		</div>

		<% /*getting the post*/
			if(session.getAttribute("postID")==null) {
				response.sendRedirect("admin.jsp");
			}
			BlogPostDAO blogPostDAO = new BlogPostDAO();
			BlogPost blogpost = blogPostDAO.selectPost((Integer) session.getAttribute("postID"));
			request.setAttribute("title", blogpost.getPostTitle());
			request.setAttribute("content", blogpost.getPostContent());
		%>


		<form action="/BlogServlet" method="post">
			<input type="hidden" name="action" value="updatePost"> <% /*post*/ %>

			<div class="form-group row">
				<label class="col-sm-2 col-form-label">Post Title </label>
				<div class="col-sm-1">
					<input type="text" name="title" class="form-control" placeholder="title"/>
				</div>
			</div>

			<div class="row">
				<div class="col-1">
					Set post visible <input type="checkbox" name="ticked" value="checked" />
				</div>


					<% /* load all categories from db to dropdown menu*/
					CategoryDAO catDAO = new CategoryDAO();
					request.setAttribute("AllCategories", catDAO.SelectAllCategories());
					%>

				<div class="col-8">
					<% //input all categories into dropdown list %>
					Select category
					<select name="category">
						<c:forEach var="tempCat" items="${AllCategories}">
							<option value="${tempCat.categoryID}">${tempCat.categoryTitle}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<% /* use tinymce */ %>
			<textarea id="default" name="content"></textarea>
			<%----%>
			<input type="submit" value="insert post">
		</form>
		<%--footer imported from footer.jsp--%>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
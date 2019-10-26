<%--
  admin console page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <title>Brew House Blog</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
        <!-- <script src="/js/bootstrap.min.js"></script> -->
        <script src="https://kit.fontawesome.com/b4abea9736.js" crossorigin="anonymous"></script>
    </head>

    <body>
    <%--Header imported from header.jsp--%>
    <jsp:include page="header.jsp" />
    <%----%>



    <!-- Lucy temp buttons for testing -->

    <!-- form action for edit category and about us-->
    <form name="editCatAbout" action="category.jsp" method="get">
        <h3> <input type="submit" name="addCat" value="Add category"/></a> </h3>
        <h3> <input type="submit" name="about" value="Edit about us"/></a> </h3>

    </form>

    <!-- test button for about us page-->
    <h3> <input type="submit" name="" value="About Us" onclick="window.location='showAbout'"/></h3>

    <!-- test button for show posts page-->

    <h3> <input type="submit"  name="action" value="Posts"/>
        <% System.out.println(request.getContextPath() + "posts button clicked from admin.jsp");%></h3>

    <form action="/BlogServlet" method="post">
        <%--<input type="hidden" name="action" value="openPosts">--%>
        <input type="submit" name="action" value="Show all Posts"/>
        <input type="submit" name="action" value="openPosts">
        <input type="submit" name="action" value="test">

        <% /*added by Isaac*/%>
        <!-- THIS IS FOR EDITING THE POST, put this in the for loop-->
        <% /*get the post id*/ %>
    </form>
    <form action="/BlogServlet" method="post">
        <input type="hidden" name="action" value="createNewPost">
        <input type="submit" name="action" value="create post">
    </form>


    <!-- Lucy temp buttons for testing end-->


    <!--import footer from footer.jsp-->
    <jsp:include page="footer.jsp" />

    </body>
</html>

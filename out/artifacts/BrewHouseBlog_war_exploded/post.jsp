<%--
 display individual post
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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



    <%-- category name div --%>
    <div class="category_name" align="center" style="margin: 50px;">
        <p style="display: inline; background: black; color: white; font-size: 30px; letter-spacing: 8px;">
            <c:out value="${displayPost.getCategoryTitle()}"/></p>
    </div>

    <%-- post container div --%>
    <div style="background-color: azure; width:85%; height: 85%; margin: auto;" align="center">

        <%-- author and posted time div --%>
        <div class="author" align="left" style="margin: 20px 0 20px 20px; font-size: 14px;">
            <p><c:out value="${displayPost.getPostAuthor()}"/></p>
            <p><c:out value="${displayPost.getPostDate()}"/></p>
        </div>

        <%-- post content div --%>
        <div class="content" style="margin: 20px 0 20px 20px;">
            <h2 align="left" style="font-weight: bold;"><c:out value="${displayPost.getPostTitle()}"/></h2>
            <article><c:out value="${displayPost.getPostContent()}"/></article>
        </div>

    </div>
    <!--import footer from WEB-INF/main-->
    <jsp:include page="footer.jsp"/>

    </body>

</html>

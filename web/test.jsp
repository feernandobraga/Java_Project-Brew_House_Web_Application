
<%--
  Created by IntelliJ IDEA.
  User: IsaacPC
  Date: 25/10/2019
  Time: 8:15 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>

    <form action="insertPost">
        Post Title
        <input type="text" name="title" /></p>

        post visible?
        <input type="checkbox" name="ticked" /> <br/>

        <% /* use tinymce */ %>
        <textarea id="default" name="content"></textarea> <br/>
        <%----%>
        <input type="submit" value="insert post">
    </form>

</body>
</html>

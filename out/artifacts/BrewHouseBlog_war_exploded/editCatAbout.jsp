<%--
  Created by IntelliJ IDEA.
  User: lucy
  Date: 16/10/19
  Time: 8:59 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Beer Blog</title>
</head>
<body>
<div align="center">
        <%
        if (request.getParameter("addCat") != null) { %>
        <form action="insert" method="post">
            <h1>ADD CATEGORY</h1>
            <h3>Add New Category</h3>
            <input type="text" name="category" size="50" required />
            <input type = "submit" value="Save" />
        </form>
        <%
        }
        else { %>
        <form action="editAbout" method="post">
            <h1>Edit About Us</h1>
            <h3>Edit About Us</h3>
            <input type="text" name="description" width="300" height="100" required />
            <input type = "submit" value="Save" />
        </form>
        <%
        }
        %>
</div>
</body>
</html>

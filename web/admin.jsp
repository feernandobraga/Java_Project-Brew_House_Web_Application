<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <script src="js/scripts.js"></script>
    </head>

    <body class="bg-light">
    <%--Header imported from header.jsp--%>
    <jsp:include page="header.jsp" />
    <%----%>


    <c:choose>

        <c:when test="${sessionScope.username != null}">

            <%--If there's a session, display admin console--%>

            <div class="container">

                <div class="row mt-5">

                    <p class="h1 text-monospace font-weight-bolder">
                        ADMIN CONSOLE
                    </p>
                </div>


                <div class="row justify-content-between mt-5">

                    <div class="col">
                        <!-- BLOG POSTS -->

                        <!-- FOR EACH POST -->
                        <c:forEach var="post" items="${showPost}">
                            <div class="row justify-content-start">

                                <div class="col-4 d-flex mb-4 justify-content-center">


                                    <div class="card  mb-3" style="max-width: 18rem; min-width: 18rem;">
                                        <div class="card-header">
                                            <c:out value="${post.getCategoryTitle()}" />
                                        </div>
                                        <div class="card-body text-secondary">
                                            <h5 class="card-title">

                                                <a href="BlogServlet?action=post&id=<c:out value='${post.getPostID()}'/>">
                                                    <c:out value="${post.getPostTitle()}" />
                                                </a>

                                            </h5>
                                            <p class="card-text">
                                                <c:out value="${post.getPostSummary()}" />...</p>
                                            <p class="">Date Posted:
                                                <c:out value="${post.getPostDate()}" />
                                            </p>
                                        </div>
                                    </div>

                                </div> <!-- End of each card-->


                                <div class="col-3 text-monospace">
                                    <a href="BlogServlet?action=edit&id=<c:out value="${post.getPostID()}"></c:out>" class="text-dark text-decoration-none">EDIT</a>
                                    <span>|</span>
                                    <a href="BlogServlet?action=delete&id=<c:out value="${post.getPostID()}"></c:out>" class="text-dark text-decoration-none" onclick="return confirmPostDeletion();">DELETE</a>
                                </div>


                            </div> <!-- End of ForEach-->
                        </c:forEach>


                    </div> <!--end end of posts col-->

                    <!-- Menu col -->
                    <div class="col-3 mt-n5 ml-n5">
                        <a href="BlogServlet?action=createNewPost" class="btn btn-lg btn-info text-monospace mb-3 btn-block text-left"><i class="far fa-paper-plane mr-4"></i>NEW POST</a>
                        <a href="BlogServlet?action=" class="btn btn-lg btn-info text-monospace mb-3 btn-block text-left"><i class="far fa-file mr-4"></i>NEW CATEGORY</a>
                        <a href="BlogServlet?action=" class="btn btn-lg btn-info text-monospace mb-3 btn-block text-left"><i class="far fa-edit mr-3"></i>EDIT ABOUT</a>
                        <a href="BlogServlet?action=logout" class="btn btn-lg btn-info text-monospace mb-3 btn-block text-left"><i class="fas fa-sign-out-alt mr-3"></i>LOGOUT</a>
                    </div>


                </div> <!-- END of row-->

            </div> <!-- END of CONTAINER-->

        </c:when>

        <c:otherwise> <%--When there is no session, display the login page--%>

            <div class="text-center mt-5">

                <form class="w-25 mx-auto" action="/BlogServlet" method="POST">

                        <%--@declare id="username"--%><%--@declare id="password"--%><span style="font-size: 13em;">
                          <i class="fas fa-beer ml-5"></i>
                        </span>


                    <h1 class="h3 mb-3 bt-5 font-weight-normal mb-4">Please sign in</h1>

                    <label for="username" class="sr-only">
                        Enter username
                    </label>

                    <input class="form-control" type="text" placeholder="Enter Username" name="username" required autofocus>

                    <label for="password" class="sr-only">
                        Password:
                    </label>

                    <input class="form-control" type="password" placeholder="Password" name="password" required>

                    <br>

                    <input type="hidden" name="action" value="login">
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Login <i class="fas fa-sign-in-alt pl-2"></i></button>
                </form>

                <c:if test="${(userAdmin != null) && (userAdmin.getAdminID() == -1) }">
                    <p class="text-danger">You have entered the wrong credentials</p>
                </c:if>

            </div>

    </c:otherwise>

    </c:choose>


    </body>

</html>

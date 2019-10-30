package blogpackage.controller;

import blogpackage.model.bean.*;
import blogpackage.model.dao.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Calendar;


@WebServlet(name = "BlogServlet", value = "/BlogServlet")
public class BlogServlet extends HttpServlet {
    private  static final long serialVersionUID =1L;
    private CategoryDAO catDAO;
    private BlogPostDAO postDAO;
    private AdminDAO adminDAO;
    private CommentDAO cmmtDAO;
    private AboutUsDAO aboutDAO;
    private static boolean isSession = false;


    public BlogServlet(){
        adminDAO = new AdminDAO();
        catDAO = new CategoryDAO();
        postDAO = new BlogPostDAO();
        cmmtDAO = new CommentDAO();
        aboutDAO = new AboutUsDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet - Running doPost()");

        //String action = request.getServletPath();

        // instead of getting the ServletPath we will be
        // getting the parameter action that is passed through by the form
        String action = request.getParameter("action");
        System.out.println("Servlet - action passed is: " + action);

        // if no parameter is passed through to the servlet,
        // it displays the main.jsp with posts
        if (action == null) {
            System.out.println("running if statement for action = null");
            try {
                showVisiblePosts(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } // endif action == null

        try {
            switch (action) {
                case "addCat":
                    insertCategory(request, response);
                    System.out.println("BlogServlet - switch: addCat executed");
                    break;

                    //edit about us
                case "editAbout":
                    editAboutUs(request, response);
                    System.out.println("BlogServlet - switch: editAbout executed");
                    break;

                //load about us page
                case "showAbout":
                    showAboutUs(request, response);
                    System.out.println("BlogServlet - switch: showAbout executed");
                    break;

                //load list of posts
                case "openPosts":
                    System.out.println("ive ran");
                    showVisiblePosts(request, response);
                    break;

                //load individual post
                case "post":
                    System.out.println("BlogServlet - switch: post() executed");
                    loadPost(request, response);
                    break;

                //button search on header
                case "search":
                    System.out.println("Servlet - Search()");
                    searchQuery(request, response);
                    break;

                case "login":
                    System.out.println("Servlet - login()");
                    userLogin(request, response);
                    break;

                //logout from admin console
                case "logout":
                    System.out.println("Servlet - logout()");
                    userLogout(request, response);
                    break;

                // delete post
                case "delete":
                    System.out.println("Servlet - delete()");
                    deletePost(request, response);
                    break;

                //show category added
                case "showCategories":
                    showCategory(request, response);
                    System.out.println("BlogServlet - switch: showCategory executed");
                    break;

                //add comment added
                case "addCmmt":
                    insertComment(request, response);
                    System.out.println("BlogServlet - switch: addCmmt executed");
                    break;

                //delete comment added
                case "delCmmt":
                    delComment(request, response);
                    System.out.println("BlogServlet - switch: delCmmt executed");
                    break;

                //open edit about us added
                case "openEditAbout":
                    showEditAboutUs(request, response);
                    System.out.println("BlogServlet - switch: openEditAbout executed");
                    break;

                // method to create new post
                case "createNewPost":
                    openNewPost(request, response);
                    System.out.println("\ncreate post - servlet\n");
                    break;

                // method called when Post button is pressed on new post
                case "insertPost":
                    System.out.println("\ninsert post - servlet\n");
                    insertPost(request,response);
                    break;

                case "edit": // edit post sends the user to the edit post with the post ID
                    System.out.println("\nupdatePost  - servlet\n");
                    editPost(request, response);
                    break;

                // method called when Post button is pressed on new post
                case "updatePost":
                    System.out.println("Servlet - Update Post()");
                    updatePost(request,response);
                    break;

                case "editCat":
                    editShowCategory(request,response);
                    System.out.println("BlogServlet - switch: editCat executed");
                    break;

                //update category added
                case "updateCat":
                    updateCategory(request, response);
                    System.out.println("BlogServlet - switch: updateCat executed");
                    break;

                default:
                    System.out.println("running the default from Servlet - switch(action)");
                    showVisiblePosts(request, response);
                    break;
            } // switch end


        } catch (SQLException ex) {
            throw new ServletException(ex);
        } // end try
    } // end doPost

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("running doGet");
        doPost(request, response);
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String ctitle = request.getParameter("category");
        Category c = new Category(ctitle.trim());
        catDAO.insertCategory(c);
        response.sendRedirect("/BlogServlet?action=showCategories");
    }

    private void showEditAboutUs(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = 1;
        AboutUs showAbout = aboutDAO.showAboutUs(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        request.setAttribute("aboutus", showAbout);
        dispatcher.forward(request, response);
    }

    //used AboutUsDAO, update this method
    private void editAboutUs(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String desc = request.getParameter("aboutDesc");
        AboutUs a = new AboutUs(desc);
        aboutDAO.insertAboutUs(a);
        response.sendRedirect("/BlogServlet?action=showAbout");
    }


    //used AboutUsDAO, update this method
    private void showAboutUs(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = 1;
        AboutUs showAbout = aboutDAO.showAboutUs(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("about.jsp");
        request.setAttribute("aboutus", showAbout);
        dispatcher.forward(request, response);
    }


    private void showPosts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        List <BlogPost> post = postDAO.selectAllPosts();
        request.setAttribute("showPost", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
        dispatcher.forward(request, response);
    }

    //only returns visible posts
    private void showVisiblePosts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        List <BlogPost> post = postDAO.selectAllVisiblePosts();
        request.setAttribute("showPost", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
        dispatcher.forward(request, response);
    }

    // fetches an individual post and category
    private void loadPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt((request.getParameter("id")));
        BlogPost existingPost = postDAO.selectPost(id);
        List<Comment> cmmt = cmmtDAO.selectAllComments(id);
        System.out.println("Comment excecuted: " + cmmt.size());
        request.setAttribute("displayPost", existingPost);
        request.setAttribute("displayComment", cmmt);
        RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
        System.out.println("BlogServlet - loadPost executed");
        dispatcher.forward(request, response);
    }

    //method that retrieves posts based on a user query
    private void searchQuery(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        String userQuery = request.getParameter("query");
        System.out.println("Servlet The user search for: " + userQuery);
        List <BlogPost> fetchedPosts = postDAO.selectAllPostsWhere(userQuery);
        System.out.println("Servlet - searchQuery() fetchedPosts length: " + fetchedPosts.size());
        request.setAttribute("fetchedPosts", fetchedPosts);
        request.setAttribute("query", userQuery);
        RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");
        dispatcher.forward(request, response);
        System.out.println("Servlet - end of searchQuery");
    }

    // method called when user tries to login on Admin Console
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        System.out.println("Servlet - userLogin()");

        // store the parameter "username" that was passed through the form
        String username = request.getParameter("username");
        System.out.println("Servlet - username passed: " + username);

        // store the parameter "password" that was passed through the form
        String password = request.getParameter("password");
        System.out.println("Servlet - password passed: " + password);

        Admin userAdmin = new Admin();
        System.out.println("Servlet - userAdmin object created");

        userAdmin = adminDAO.checkCredentials(username, password);
        System.out.println("Servlet - calling the AdminDAO method");

        if (userAdmin.getAuthenticated()) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            isSession = true;
        }

        if (isSession) {
            List <BlogPost> post = postDAO.selectAllPosts();
            request.setAttribute("showPost", post);
        }

        request.setAttribute("userAdmin", userAdmin);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
        System.out.println("Servlet - end of userLogin");

    } // end of userLogin()


    // method called when user logout from admin console
    private void userLogout(HttpServletRequest request, HttpServletResponse response) throws  ServletException, SQLException, IOException {
        // this method retrieves the session from the user and removes it while redirecting to admin.jsp
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.invalidate();
        response.sendRedirect("admin.jsp");
        isSession = false;
    }

    // method that delete posts from the admin console
    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, SQLException, IOException{
        System.out.println("Servlet - deletePost()");

        int postID = Integer.parseInt(request.getParameter("id"));
        boolean isPostDeleted = postDAO.deletePost(postID);
        System.out.println("Servlet - post: " + postID + " deleted? " + isPostDeleted);
        response.sendRedirect("BlogServlet?action=login");
    }

    // method to display categories
    private void showCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Category> cat = catDAO.selectCategory();
        request.setAttribute("showCategories", cat);
        System.out.println("BlogServlet - showCategory executed");
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    //insertComment function added.
    private void insertComment(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String cOwner = request.getParameter("cmmtOwner");
        String cmmt = request.getParameter("cmmt");
        int pId = Integer.parseInt(request.getParameter("id"));
        Comment c = new Comment(cOwner.trim(), cmmt.trim(), pId);
        cmmtDAO.insertComment(c);
        System.out.println("BlogServlet - loadPost executed" + c);
        response.sendRedirect("/BlogServlet?action=post&id=" + pId);
    }


    //deleteComment function added
    private void delComment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int pId = Integer.parseInt(request.getParameter("Pid"));
        cmmtDAO.deleteComment(id);
        System.out.println("deleted comment id = " + id + "post id = " + pId);
        response.sendRedirect("/BlogServlet?action=post&id=" + pId);
    }


    //Isaac
    private void insertPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        System.out.println("Servlet - insertPost()");
        //create bean object
        BlogPost post = new BlogPost();

        System.out.print("getting title ");
        post.setPostTitle(request.getParameter("title"));
        System.out.println("- got title ");

        System.out.print("getting date ");
        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateAsString = formatter.format(currentDate);
        System.out.println("Servlet - The dateAsString is: " + dateAsString);
        post.setPostDate(dateAsString);/*insert the date*/
        System.out.println("- got date");

        System.out.print("getting author ");
        HttpSession session = request.getSession(false);

        String luser = (String) session.getAttribute("username");
        System.out.println("Username retrieve is: " + luser);
        post.setPostAuthor(luser);
        System.out.println("- got author");

        System.out.print("getting content ");
        post.setPostContent(request.getParameter("content"));
        System.out.println("- got content");

        //check if the check box is ticked
        System.out.print("getting visibility - is");
        String isBoxTicked = request.getParameter("ticked");
        System.out.println("isBoxTicked: " + isBoxTicked);
        if (isBoxTicked == null || isBoxTicked.length() == 0 || isBoxTicked.isBlank()) {
            System.out.println("setting the post to true");
            post.setPostVisible(true);
        }else if(isBoxTicked.equals("checked")){
            System.out.println("setting the post to false");
            post.setPostVisible(false);
        }

        System.out.println("- got visibility " + post.getPostVisible());

        System.out.print("getting category ");
        int categoryId = 1;
        categoryId = Integer.parseInt(request.getParameter("category"));
        post.category.setCategoryID(categoryId);
        System.out.println("- got categoryID\n");

        post.displayPost();

        //insert into database - DAO works, check jUnit
        System.out.println("\n\ninserting the post to database");
        BlogPostDAO blogPDAO = new BlogPostDAO();
        blogPDAO.InsertPost(post);

        //redirect to admin page
        System.out.println("redirecting the user to admin console else home");

        try {
            response.sendRedirect("BlogServlet?action=login");
        }catch (Exception e) {
            response.sendRedirect("BlogServlet");
        }
    } // END insert post

    // method called to editPosts
    private void editPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        System.out.println("Servlet - editPost()");

        int postID = Integer.parseInt(request.getParameter("postID"));
        HttpSession session = request.getSession();

        BlogPost existingPost = postDAO.selectPost(postID);

        RequestDispatcher dispatcher = request.getRequestDispatcher("newpost.jsp");

        request.setAttribute("existingPost", existingPost);
        dispatcher.forward(request, response);
    }

    //method called to update posts
    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        System.out.println("Servlet - inside updatePost() method");

        int postID = Integer.parseInt(request.getParameter("postID"));
        System.out.println("Retrieved postID " + postID);

        String postTitle = request.getParameter("title");
        System.out.println("Retrieved title " + postTitle);

        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateAsString = formatter.format(currentDate);
        System.out.println("updated date " + dateAsString);

        HttpSession session = request.getSession(false);
        String loggedUser = (String) session.getAttribute("username");
        System.out.println("retrieved username:" + loggedUser);

        String postContent = request.getParameter("content");
        System.out.println("retrieved content " + postContent);

        boolean isPostVisible = false;
        String isBoxTicked = request.getParameter("ticked");
        if (isBoxTicked == null || isBoxTicked.length() == 0 || isBoxTicked.isBlank()) {
            System.out.println("setting the post to true");
            isPostVisible = true;
        }else if(isBoxTicked.equals("checked")){
            System.out.println("setting the post to false");
            isPostVisible = false;
        }
        System.out.println("sorted out visibility: " + isPostVisible);

        int categoryId = Integer.parseInt(request.getParameter("category"));
        System.out.println("retrieved category" + categoryId);

        BlogPost modifiedPost = new BlogPost(postID, postTitle, dateAsString, loggedUser, postContent, isPostVisible, categoryId);

        System.out.println("created a new BlogPost Bean");

        postDAO.updatePost(modifiedPost);
        /*boolean isPostUpdated = postDAO.updatePost(updatedPost);
        System.out.println("Servlet - post: " + postID + " updated? " + isPostUpdated);*/
        response.sendRedirect("BlogServlet?action=login");

    } //end updatePost


    private void editShowCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category currentCat = catDAO.selectCategory(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/BlogServlet?action=showCategories");
        request.setAttribute("currentCat", currentCat);
        dispatcher.forward(request, response);
        System.out.println("selected categoryId = " + id);
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String Cname = request.getParameter("category").trim();
        Category c = new Category(id, Cname);
        catDAO.updateCategory(c);
        System.out.println("updated category Id = " + id);
        response.sendRedirect("/BlogServlet?action=showCategories");
    }


    private void openNewPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        response.sendRedirect(request.getContextPath() + "/newpost.jsp");
    }

} //end servlet

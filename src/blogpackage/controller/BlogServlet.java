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
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "BlogServlet", value = "/BlogServlet")
public class BlogServlet extends HttpServlet {
    private  static final long serialVersionUID =1L;

    //DAO objects
    private CommentDAO cmmtDAO;
    private CategoryDAO catDAO;
    private BlogPostDAO postDAO;
    private AdminDAO adminDAO;
    private AboutUsDAO aboutDAO;

    private static boolean isSession = false;
   private String authorName = "annon";
    private int postID; // used for edit
///BrewHouseBlog_war_exploded

    public BlogServlet(){
        adminDAO = new AdminDAO();
        catDAO = new CategoryDAO();
        postDAO = new BlogPostDAO();

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

        System.out.print("event triggered");
        try {
            switch (action) {
                //add category
                case "addCat":
                    insertCategory(request, response);
                    System.out.println("BlogServlet - switch: addCat executed");
                    break;

                case "delCat":
                    delCategory(request, response);
                    System.out.println("BlogServlet - switch: delCat executed");


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
                    loadPost(request, response);
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


                //button search on header
                case "search":
                    System.out.println("Servlet - Search()");
                    searchQuery(request, response);
                    break;

                case "login":
                    System.out.println("Servlet - login()");
                    userLogin(request, response);
                    break;

                case "logout":
                    System.out.println("Servlet - logout()");
                    userLogout(request, response);
                    break;

                case "delete":
                    System.out.println("Servlet - delete()");
                    deletePost(request, response);
                    break;

                case "insertPost":
                    System.out.println("\ninsert post - servlet\n");
                    insertPost(request,response);
                    break;

                case "edit": // edit post sends the user to the edit post with the post ID
                    System.out.println("\nupdatePost  - servlet\n");
                    editPost(request, response);
                    break;

                case "updatePost": // user click event on edit.jsp page
                    updatePost(request, response);
                    break;

                case "createNewPost":
                    openNewPost(request, response);
                    System.out.println("\ncreate post - servlet\n");
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

    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BlogPostDAO bpdao = new BlogPostDAO();
        BlogPost post = new BlogPost();

        //TODO Add stuff from editPost
        // get values from jsp and put into the bean
        System.out.print("getting title ");
        post.setPostTitle(request.getParameter("title"));
        System.out.println("- got title ");

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
        post.setCategoryId(categoryId);
        System.out.println("- got categoryID\n");

        post.displayPost();

        //4
        bpdao.updatePost(postID, post);
        response.sendRedirect("admin.jsp");
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String ctitle = request.getParameter("category");
        Category c = new Category(ctitle);
        catDAO.insertCategory(c);
        response.sendRedirect("main.jsp");
    }

    private void editAboutUs(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
        String desc = request.getParameter("description");
        AboutUs a = new AboutUs(desc);
        catDAO.insertAboutUs(a);
        response.sendRedirect("showAbout");
    }

    private void showAboutUs(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        List <AboutUs> about = catDAO.selectAboutUs();
        request.setAttribute("showDesc", about);
        RequestDispatcher dispatcher = request.getRequestDispatcher("about.jsp");
        dispatcher.forward(request, response);
    }

    private void showPosts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        List <BlogPost> post = postDAO.selectAllPosts();
        request.setAttribute("showPost", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
        dispatcher.forward(request, response);
    }

    private void showVisiblePosts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        List <BlogPost> post = postDAO.selectAllVisiblePosts();
        request.setAttribute("showPost", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
        dispatcher.forward(request, response);
    }

    private void openNewPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        response.sendRedirect(request.getContextPath() + "/newpost.jsp");
    }

    private void loadPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        int id = Integer.parseInt((request.getParameter("id")));
        BlogPost existingPost = postDAO.selectPost(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
        request.setAttribute("displayPost", existingPost);
        dispatcher.forward(request, response);
    }

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
        this.authorName = username;
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

    private void insertPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        //create bean object
        BlogPost post = new BlogPost();
        try {


        // get values from jsp and put into the bean
        System.out.println("\n" + "\u001B[32m" + "insert post - collecting values ");
        post.setPostTitle(request.getParameter("title"));
        System.out.println("title:" + post.getPostAuthor());

        post.setPostDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        post.setPostAuthor(authorName); // changed, post is now associated with user
        System.out.println("author name: " + post.getPostAuthor());

        post.setPostContent(request.getParameter("content"));
        System.out.println("contents: " + post.getPostContent());

        String isBoxTicked = request.getParameter("ticked");
        System.out.println("isBoxTicked: " + isBoxTicked);
        if (isBoxTicked == null || isBoxTicked.length() == 0 || isBoxTicked.isBlank()) {
            post.setPostVisible(true);
        }else if(isBoxTicked.equals("checked")){
            post.setPostVisible(false);
        }
        System.out.println("visibility: " + post.getPostVisible());

        int categoryId = 1;
        categoryId = Integer.parseInt(request.getParameter("category"));
        post.setCategoryId(categoryId);
        System.out.println("categoryID: " + post.getCategoryId());
        System.out.println("insert post - finished collecting data from jsp" + "\u001B[0m");
        } catch (NullPointerException e) {

            try {
                response.sendRedirect("admin.jsp");
            }catch (Exception ee) {
                response.sendRedirect("home.jsp");
                ee.printStackTrace();
            }
        }
        //display post values
        post.displayPost();

        BlogPostDAO blogPDAO = new BlogPostDAO();
        blogPDAO.InsertPost(post);

        //redirect to admin page
        System.out.println("redirecting the user to admin console else home");

        try {
            response.sendRedirect("admin.jsp");
        }catch (Exception e) {
            response.sendRedirect("home.jsp");
        }

    } // END insert post

    private void editPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        this.postID = Integer.parseInt(request.getParameter("PostID"));
        HttpSession session = request.getSession();
        session.setAttribute("postID", this.postID);
        response.sendRedirect("editpost.jsp");

    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, SQLException, IOException{
        System.out.println("Servlet - deletePost()");

        int postID = Integer.parseInt(request.getParameter("id"));
        boolean isPostDeleted = postDAO.deletePost(postID);
        System.out.println("Servlet - post: " + postID + " deleted? " + isPostDeleted);
        response.sendRedirect("BlogServlet?action=login");
    }

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

    private void delCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        catDAO.deleteCategory(id);
        System.out.println("deleted comment id = " + id);
        response.sendRedirect("/BlogServlet?action=showCategories");
    }

    private void showCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Category> cat = catDAO.selectCategory();
        request.setAttribute("showCategories", cat);
        System.out.println("BlogServlet - showCategory executed");
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditAboutUs(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = 1;
        AboutUs showAbout = aboutDAO.showAboutUs(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        request.setAttribute("aboutus", showAbout);
        dispatcher.forward(request, response);
    }
} //end servlet

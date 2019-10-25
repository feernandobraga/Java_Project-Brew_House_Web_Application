package blogpackage.controller;

import blogpackage.model.bean.AboutUs;
import blogpackage.model.bean.BlogPost;
import blogpackage.model.bean.Category;
import blogpackage.model.dao.BlogPostDAO;
import blogpackage.model.dao.CategoryDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.spi.FileSystemProvider;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "BlogServlet", value = "/BlogServlet")
public class BlogServlet extends HttpServlet {
    private  static final long serialVersionUID =1L;
    private CategoryDAO catDAO;
    private BlogPostDAO postDAO;



///BrewHouseBlog_war_exploded

    public BlogServlet(){
        catDAO = new CategoryDAO();
        postDAO = new BlogPostDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet - Running doPost()");


        // instead of getting the ServletPath we will be
        // getting the parameter action that is passed through by the form
        String action = request.getParameter("action");


        try {
            switch (action) {
                //add category
                case "/addCat":
                    insertCategory(request, response);
                    break;

                //edit about us
                case "/editAbout":
                    editAboutUs(request,response);
                    break;

                //load about us page
                case "/showAbout":
                    showAboutUs(request, response);
                    break;

                //load list of posts
                case "openPosts":
                    System.out.println("ive ran");
                    showPosts(request, response);
                    break;

                //load individual post
                case "/post":
                    loadPost(request, response);
                    break;

                //action test
                case "search":
                    System.out.println("Servlet - Search()");
                    break;

                //action test2
                case "test2":
                    System.out.println("running from /test2");
                    break;

                case "insertPost":
                    System.out.println("insert post - servlet");
                    insertPost(request,response);
                    break;


                case "selectAllCategories":
                    System.out.println("Servlet - selectAllCategories");
                    getAllCategories(request,response);
                    break;



                default:
                    System.out.println("running the default from Servlet - switch(action)");
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } // end try
    } // end doPost

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("running doGet");
        doPost(request, response);
    }

    //Isaac
    private void insertPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        //create bean object
        BlogPost post = new BlogPost();

        System.out.println(1);

        //TODO get values from jsp and put into the bean
        post.setPostTitle(request.getParameter("title"));
        post.setPostDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));/*insert the date*/
        post.setPostAuthor(request.getParameter("author"));
        post.setPostContent(request.getParameter("content"));

        System.out.println(2);
        //check if the check box is ticked
        boolean ticked = true;
        if (request.getParameter("ticked").equals("checked")) { ticked = false; } else { ticked = true; }
        post.setPostVisable(ticked);
        System.out.println(3);

        try {
            System.out.println(Integer.parseInt(request.getParameter("category")));
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        int categoryId = 1;
        categoryId = Integer.parseInt(request.getParameter("category"));
        
        request.setAttribute("selectedCatId", categoryId);



        post.displayPost();

        //insert into database - DAO works, check jUnit
        System.out.println("inserting the post to database");
        BlogPostDAO blogPDAO = new BlogPostDAO();
        blogPDAO.InsertPost(post);

        //redirect to admin page
        System.out.println("redirecting the user to admin console");

        try {
            response.sendRedirect("admin.jsp");
        }catch (Exception e) {
            response.sendRedirect("home.jsp");
        }
    } // END insert post

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String ctitle = request.getParameter("category");
        Category c = new Category(ctitle);
        catDAO.insertCategory(c);
        response.sendRedirect("main.jsp");
    }

    //Isaac
    //TODO not currently using
    public void getAllCategories(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        CategoryDAO catDAO = new CategoryDAO();
        request.setAttribute("displayCategories", catDAO.SelectAllCategories());
        RequestDispatcher dispatcher = request.getRequestDispatcher("newpost.jsp");
        dispatcher.forward(request, response);
        FileSystemProvider pageContext = null;
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

    private void loadPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        int id = Integer.parseInt((request.getParameter("id")));
        BlogPost existingPost = postDAO.selectPost(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
        request.setAttribute("displayPost", existingPost);
        dispatcher.forward(request, response);
    }
} //end servlet

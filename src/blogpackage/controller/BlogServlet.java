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
import java.sql.SQLException;
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

        //String action = request.getServletPath();

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
    //TODO test
    private void insertPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        //create bean object
        BlogPost post = new BlogPost();

        //get values from jsp and put into the bean
        post.setPostTitle(request.getParameter("title"));
        LocalDate snapDate = LocalDate.now( ZoneId.of( "Australia/Melbourne" ) );
        post.setPostDate(snapDate);//insert the date
        post.setPostAuthor(request.getParameter("author"));
        post.setPostContent(request.getParameter("content"));

        //check if the check box is ticked
        boolean ticked = true;
        if (request.getParameter("accidentalCover").equals("checked")) {
            ticked = true;
        }
        else {
            ticked = false;
        }

        // insert bool "ticked into bean"
        post.setPostVisible(ticked);
        post.setCategoryID(request.getParameter("category"));

        //insert into database - DAO works, check jUnit
        BlogPostDAO blogPDAO = new BlogPostDAO();
        blogPDAO.InsertPost(post);

        //redirect to admin page
        response.sendRedirect("adminconsole.jsp");
    } // END insert category

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String ctitle = request.getParameter("category");
        Category c = new Category(ctitle);
        catDAO.insertCategory(c);
        response.sendRedirect("main.jsp");
    }

    //Isaac
    public void getAllCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        //TODO
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

    private void showCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{

    }

} //end servlet

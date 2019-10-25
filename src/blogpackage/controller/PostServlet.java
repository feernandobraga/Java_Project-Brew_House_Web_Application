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

@WebServlet("/insertPost")
public class PostServlet extends HttpServlet {
    private  static final long serialVersionUID =1L;
    private CategoryDAO catDAO;
    private BlogPostDAO postDAO;



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost called");
    } // end doPost

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("running doGet");
        doPost(request, response);
    } // end doGet
} //end servlet

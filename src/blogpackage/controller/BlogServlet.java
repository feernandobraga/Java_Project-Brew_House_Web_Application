package blogpackage.controller;

import blogpackage.model.bean.AboutUs;
import blogpackage.model.bean.Category;
import blogpackage.model.dao.CategoryDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "/", value = "/")
public class BlogServlet extends HttpServlet {
    private  static final long serialVersionUID =1L;
    private CategoryDAO catDAO;

    public BlogServlet(){
        catDAO = new CategoryDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/insert":
                    insertCategory(request, response);
                    break;
                default:
                    editAboutUs(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    private void showNewEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("employeeform.jsp");
        dispatcher.forward(request, response);
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String ctitle = request.getParameter("category");
        Category c = new Category(ctitle);
        catDAO.insertCategory(c);
        response.sendRedirect("test.jsp");
    }

    private void editAboutUs(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
        String desc = request.getParameter("description");
        AboutUs a = new AboutUs(desc);
        catDAO.insertAboutUs(a);
        response.sendRedirect("test.jsp");
    }
}

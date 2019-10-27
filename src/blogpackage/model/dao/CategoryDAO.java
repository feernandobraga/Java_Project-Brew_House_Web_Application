package blogpackage.model.dao;
import blogpackage.model.bean.AboutUs;
import blogpackage.model.bean.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {


    //defining instance variables

    //Define instance variables for category and aboutUs
    private String DBURL = "jdbc:mysql://localhost:3306/BlogDB?serverTimezone=Australia/Sydney";
    private String DBUsername = "root";
    private String DBPassword = "mysql123";
    private String INSERTCATSQL = "INSERT INTO category (categoryTitle) VALUES " + " (?);";
    private String SELECTALLCATS = "SELECT * FROM category;";
    private String UPDATEABOUTSQL = "UPDATE aboutUs SET description=" + " (?) WHERE descId=1;";
    private String SELECTDESCSQL = "SELECT * FROM aboutUs;";

    //constructor
    public CategoryDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);
        } catch (SQLException e) {
            //Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    //insert category sql connection and statement
    public void insertCategory(Category cat) throws SQLException {
        System.out.println(INSERTCATSQL);
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // try-with-resource statement will auto close the connection.
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERTCATSQL);
            preparedStatement.setString(1, cat.getCategoryTitle());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            finallySQLException(connection,preparedStatement,null);
        }
    }

    //display all category list
    public List <Category> showCategory() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        List <Category> cat = new ArrayList<>();

        // using try-with-resources to avoid closing resources (boilerplate code)
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECTALLCATS);
            System.out.println(preparedStatement);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                int catId = rs.getInt("categoryId");
                String catTitle = rs.getString("categoryTitle");
                cat.add(new Category(catId, catTitle));
            }
        }
        catch (SQLException e) {
            printSQLException(e);
        }
        finally {
            finallySQLException(connection,preparedStatement,rs);
        }
        return  cat;
    }

    public List<Category> SelectAllCategories() {
        System.out.println("selecting all categories");
        // vars
        List<Category> allCat = new ArrayList<Category>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM category";

        try {
            connection = getConnection(); // connect to db

            // commit select * statement
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            // transfer results to bean
            System.out.println("retreiving results from query");
            while(resultSet.next()) {
                //
                Category catagory = new Category();
                catagory.setCategoryID(resultSet.getInt("categoryID"));
                catagory.setCategoryTitle(resultSet.getString("categoryTitle"));

                //add bean to list
                allCat.add(catagory);
            }


            finallySQLException(connection, preparedStatement, resultSet);
            return allCat;
        } catch (SQLException e) {
            finallySQLException(connection, preparedStatement, resultSet);
            e.printStackTrace();
            return null;
        }
    } // end SelectAllCatagories

    //insert about us sql connection and statement
    public void insertAboutUs(AboutUs about) throws SQLException {
        System.out.println(UPDATEABOUTSQL);
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // try-with-resource statement will auto close the connection.
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATEABOUTSQL);
            preparedStatement.setString(1, about.getDesc());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            finallySQLException(connection,preparedStatement,null);
        }
    }

    public List <AboutUs> selectAboutUs() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        List <AboutUs> about = new ArrayList<>();
        // using try-with-resources to avoid closing resources (boilerplate code)

        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECTDESCSQL);
            //statement will retrieve desc from descId=1
            System.out.println(preparedStatement);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                int descId = rs.getInt("descId");
                String desc = rs.getString("description");
                about.add(new AboutUs(descId, desc));
            }
        }
        catch (SQLException e) {
            printSQLException(e);
        }
        finally {
            finallySQLException(connection,preparedStatement,rs);
        }
        return  about;
    }

    //catch sql message when error occurs
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException)
                        e).getSQLState());
                System.err.println("Error Code: " + ((SQLException)
                        e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    //close sql statement and connection
    private void finallySQLException(Connection c, PreparedStatement p, ResultSet r){
        if (r != null) {
            try {
                r.close();
            } catch (Exception e) {}
            r = null;
        }
        if (p != null) {
            try {
                p.close();
            } catch (Exception e) {}
            p = null;
        }
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                c = null;
            }
        }
    }
}

package blogpackage.model.dao;
import blogpackage.model.bean.AboutUs;
import blogpackage.model.bean.Category;

import java.sql.*;

public class CategoryDAO {

    //Define instance variables for category and aboutUs
    private String DBURL = "jdbc:mysql://localhost:3306/BlogDB?serverTimezone=Australia/Sydney";
    private String DBUsername = "root";
    private String DBPassword = "mysql123";
    private String INSERTCATSQL = "INSERT INTO category (categoryTitle) VALUES " + " (?);";
    private String UPDATEABOUTSQL = "UPDATE aboutUs SET description " + " (?) WHERE descId=1;";

    //constructor
    public CategoryDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
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
            preparedStatement.setString(1, cat.getCname());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            finallySQLException(connection,preparedStatement,null);
        }
    }

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


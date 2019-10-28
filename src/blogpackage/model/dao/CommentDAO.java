package blogpackage.model.dao;

import  blogpackage.model.bean.Comment;
import java.sql.*;
import java.util.*;

public class CommentDAO {

    //defining instance variables
    private String DBURL = "jdbc:mysql://localhost:3306/BlogDB?serverTimezone=Australia/Sydney";
    private String DBUsername = "root";
    private String DBPassword = "mysql123";
    private String DELETECMMTPSQL = "DELETE from comment WHERE cmmtId=?;";
    private String INSERTCMMTSQL = "INSERT INTO comment (cmmtOwner, comment, postId) VALUES " + " (?, ?, ?);";
    // constructor
    public CommentDAO() {
    }

    // Creates a connection to the database
    protected Connection getConnection(){
        Connection connection = null;
        System.out.println("DAO CommentDAO - getConnection()"); //debugging

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return connection;
    } // end getConnection

    public void insertComment(Comment cmmt) throws SQLException {
        System.out.println(INSERTCMMTSQL);
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // try-with-resource statement will auto close the connection.
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERTCMMTSQL);
            preparedStatement.setString(1, cmmt.getCommentOwner());
            preparedStatement.setString(2, cmmt.getCommentContent());
            preparedStatement.setInt(3, cmmt.getCommentID());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            finallySQLException(connection, preparedStatement, null);
        }
    }

    public boolean deleteComment(int id) throws SQLException {
        boolean cmmtDeleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement =
                    connection.prepareStatement(DELETECMMTPSQL);
            preparedStatement.setInt(1, id);
            cmmtDeleted = preparedStatement.executeUpdate() > 0 ?
                    true : false;
        } finally {
            finallySQLException(connection, preparedStatement, null);
        }
        return cmmtDeleted;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
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

    //method to close DB connection
    private void finallySQLException(Connection c, PreparedStatement p, ResultSet r) {
        System.out.println("DAO Comment - closing connections");
        if (r != null) {
            try {
                r.close();
            } catch (SQLException e) {
                r = null;
            }
        } // end if resultset is not null

        if (p != null) {
            try {
                p.close();
            } catch (SQLException e) {
                p = null;
            }
        } // end if prepared statement is not null

        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                c = null;
            }
        } // end if connection is not null

    } // end finallySQLException


}

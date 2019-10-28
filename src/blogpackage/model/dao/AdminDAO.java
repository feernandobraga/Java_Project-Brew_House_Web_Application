package blogpackage.model.dao;

import blogpackage.model.bean.Admin;
import java.sql.*;
import java.util.*;

public class AdminDAO {


    //--------------------------
    //      SQL QUERIES
    //--------------------------
    private String SELECTUSERNAMEPASSWORD = "Select * from admin where username = ? and password = ?;";

    //defining instance variables
    private String DBURL = "jdbc:mysql://localhost:3306/BlogDB?serverTimezone=Australia/Sydney";
    private String DBUsername = "root";
    private String DBPassword = "mysql123";

    // constructor
    public AdminDAO() {
    }

    // Creates a connection to the database
    protected Connection getConnection(){
        Connection connection = null;
        System.out.println("DAO AdminDAO- getConnection()"); //debugging

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

    public Admin checkCredentials(String username, String password){

        System.out.println("AdminDAO - checkCredentials()");

        Admin admin = null;

        //initialize DB variables
        Connection connection = null;
        PreparedStatement preparedStatement =  null;
        ResultSet rs = null;
        System.out.println("AdminDAO - connection prepStatement and rs created");


        try {
            //stablishing a connection
            connection = getConnection();

            //set prepared statement
            preparedStatement = connection.prepareStatement(SELECTUSERNAMEPASSWORD);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);


            rs = preparedStatement.executeQuery();
            System.out.println("AdminDAO - Query executed: " + preparedStatement);

            // rs.next() returns true if a user is found
            if (rs.next()) {
                int adminID = rs.getInt("adminId");
                String usrname = rs.getString("username");
                String pwd = rs.getString("password");

                admin = new Admin(adminID, usrname, pwd, true);
                System.out.println("AdminDAO - checkCredentials() - new authenticated admin Bean: " +
                        adminID + " " + username + " " + pwd + " true");

            } else {
                String usrname = username;
                String pwd = password;
                admin = new Admin(-1, usrname, pwd, false);
                System.out.println("AdminDAO - checkCredentials() - new failed admin Bean: " +
                        -1 + " " + username + " " + pwd + " false");
            }

            if (username == null) {
                admin = new Admin(-2, username, password, false);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin;

    }



    //method to close DB connection
    private void finallySQLException(Connection c, PreparedStatement p, ResultSet r) {
        System.out.println("DAO Admin - closing connections");
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

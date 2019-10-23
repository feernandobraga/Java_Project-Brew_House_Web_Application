package blogpackage.model.dao;

import blogpackage.model.bean.BlogPost;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.*;

public class BlogPostDAOTest {

    @Test
    public void insertPost() {
        // temp details
        BlogPostDAO postdao = new BlogPostDAO();
        BlogPost post = new BlogPost();

        System.out.println("attempting to insert data into post Bean: ");
        //insert values to beak
        post.setPostTitle("Test");
        post.setPostDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));//insert the date
        post.setPostAuthor("T_Test");
        post.setPostContent("<p>Hello World</p>");
        post.setPostVisable(true);
        post.category.setCategoryID(1);

        //display all inputs
        post.displayPost();

        //test Insert
        System.out.println("\n" + "inserting the post:");
        BlogPostDAO blogPDAO = new BlogPostDAO();
        blogPDAO.InsertPost(post);
    }
}
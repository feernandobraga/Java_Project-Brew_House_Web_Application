package blogpackage.model.bean;

public class BlogPost {
    private int postID;
    private String postTitle;
    private java.sql.Date postDate;
    private String postAuthor;
    private String postContent;
    private boolean isPostVisible;

    // OLD "added categoryId and category Title by Lucy"
    // refactored to Category (allows to reuse already created code)
    public Category category = new Category();

    public BlogPost(){

    }

    //constructor for selectAllPosts in PostDAO

    public BlogPost(int postID, String postTitle, java.sql.Date postDate, String postAuthor, String postContent, boolean isPostVisible, int categoryId, String categoryTitle) {
        this.postID = postID;
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.postAuthor = postAuthor;
        this.postContent = postContent;
        this.isPostVisible = isPostVisible;

        // set catergoy object
        this.category.setCategoryID(categoryId);
        this.category.setCategoryTitle(categoryTitle);
    }

    public int getPostID() {
        return postID;
    }
    public void setPostID(int postID) {
        this.postID = postID;
    }
    public String getPostTitle() {
        return postTitle;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public java.sql.Date getPostDate() {
        return postDate;
    }

    //Isaac updated setPostDate
    public void setPostDate(java.sql.Date snapDate) {
        this.postDate = snapDate;
    }

    public String getPostAuthor() {
        return postAuthor;
    }
    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }
    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
    public boolean getPostVisible() {
        return isPostVisible;
    }

    public void setPostVisible(boolean isTicked) {
        this.isPostVisible = isPostVisible;
    }

    //displayALLToTerminal
    public void displayPost() {

        System.out.println("\n" + "displaypost");
        System.out.println("Title: "+ getPostTitle());
        System.out.println("date: "+ getPostDate());
        System.out.println("author: "+ getPostAuthor());
        System.out.println("content: "+ getPostContent());
        System.out.println("visibility: "+ getPostVisible());
        System.out.println("category ID: "+ category.getCategoryID());
        System.out.println();
    }
}

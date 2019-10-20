package blogpackage.model.bean;

public class Comment {

    protected int commentID;
    protected String commentOwner;
    protected String commentContent;

    // constructor
    public Comment(int commentID, String commentOwner, String commentContent) {
        this.commentID = commentID;
        this.commentOwner = commentOwner;
        this.commentContent = commentContent;
    }

    // getters and setters
    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(String commentOwner) {
        this.commentOwner = commentOwner;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}

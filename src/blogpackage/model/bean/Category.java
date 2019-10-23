package blogpackage.model.bean;

public class Category {
    protected int categoryID;
    protected String categoryTitle;

    // add more constructors as you need
    public Category() {
    }

    public Category(int categoryID, String categoryTitle) {
        this.categoryID = categoryID;
        this.categoryTitle = categoryTitle;
    }

    // getters and setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

}

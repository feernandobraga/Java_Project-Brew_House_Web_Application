package blogpackage.model.bean;

public class Category {
    protected int Cid;
    protected String Cname;

    public Category() {

    }
    //Category getter and setter
    public Category(String Cname) {
        this.Cname = Cname;
    }

    public Category(int Cid, String Cname) {
        this.Cid = Cid;
        this.Cname = Cname;
    }

    public int getCid() {
        return Cid;
    }
    public void setCid(int Cid) {
        this.Cid = Cid;
    }

    public String getCname() {
        return Cname;
    }
    public void setCname(String Cname) {
        this.Cname = Cname;
    }

}

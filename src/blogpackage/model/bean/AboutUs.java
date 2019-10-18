package blogpackage.model.bean;

public class AboutUs {
    protected int Did;
    protected String Desc;

    public AboutUs(){}

    public AboutUs (String Desc){
        this.Desc = Desc;
    }

    public AboutUs(int Did, String Desc){
        this.Did = Did;
        this.Desc = Desc;
    }

    public int getDid(){
        return Did;
    }
    public void setDid(int Did){
        this.Did = Did;
    }

    public String getDesc(){
        return Desc;
    }
    public void setDesc(String Desc){
        this.Desc = Desc;
    }
}

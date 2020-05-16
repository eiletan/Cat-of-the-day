package Model;

public class Cat {
    private String caturl;
    private String desc;
    private String breedID;
    private String name;


    public Cat(String breedID, String desc, String name, String caturl){
        this.breedID = breedID;
        this.desc = desc;
        this.name = name;
        this.caturl = caturl;
    }


    public String getCaturl(){
        return caturl;
    }

    public String getDesc(){
        return desc;
    }

    public String getName(){
        return name;
    }

}

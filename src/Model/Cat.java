package Model;

import org.json.simple.JSONObject;

import java.io.IOException;

public class Cat {
    private String caturl;
    private int imagewidth;
    private int imageheight;
    private String desc;
    private String breedID;
    private String name;
    private Parser catparser;

    public Cat(String breedID)throws IOException{
        this.breedID = breedID;
        this.catparser = new Parser();
        cathelper();
    }


    // API code adapted from CPSC 210 edx page, which in turn was from http://zetcode.com/articles/javareadwebpage/
    // EFFECTS: Initializes Cat object fields with values taken from the API data
    private void cathelper() {
        Object response = caller();
        JSONObject descobj = catparser.recursor(response,"description");
        JSONObject nameobj = catparser.recursor(response,"name");
        JSONObject urlobj = catparser.recursor(response,"url");

        this.desc = descobj.get("description").toString();
        this.name = nameobj.get("name").toString();
        this.caturl = urlobj.get("url").toString();

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

    public int getImagewidth(){
        return imagewidth;
    }

    public int getImageheight(){
        return imageheight;
    }

    private Object caller() {
        try {
            String temp = catparser.callCATAPI("https://api.thecatapi.com/v1/images/search?breed_ids=" + this.breedID);
            return catparser.parseResponse(temp);
        } catch (IOException io) {
            System.out.println("Failed to call the cat api");
        }
        return null;
    }
}

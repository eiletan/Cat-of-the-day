package Model;

import java.io.IOException;
import java.net.URL;

public class Cat {
    private URL caturl;
    private int imagewidth;
    private int imageheight;
    private String desc;
    private String breedID;
    private String name;
    private Parser catparser;

    public Cat(String breedID)throws IOException{
        this.breedID = breedID;
        this.catparser = new Parser();
        System.out.println("Cat object constructed!");
        cathelper();
    }


    // API code adapted from CPSC 210 edx page, which in turn was from http://zetcode.com/articles/javareadwebpage/
    // EFFECTS: Initializes Cat object fields with values taken from the API data
    private void cathelper() {
        try {
            catparser.callCATAPI("https://api.thecatapi.com/v1/images/search?breed_ids=" + this.breedID);
            catparser.parseCat(this.breedID);
        } catch (IOException io) {
            System.out.println("IOException caught");
        }
    }

    public URL getCaturl(){
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
}

package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Cat {
    private URL caturl;
    private int imagewidth;
    private int imageheight;
    private String desc;
    private String breedID;
    private String name;

    public Cat(String breedID)throws IOException{
        this.breedID = breedID;
        System.out.println("Cat object constructed!");
        cathelper();
    }


    // API code adapted from CPSC 210 edx page, which in turn was from http://zetcode.com/articles/javareadwebpage/
    // EFFECTS: Initializes Cat object fields with values taken from the API data
    private void cathelper() throws IOException{

        BufferedReader br = null;

        try {
            String apikey = "4654f6ca-0eb3-4f4d-b314-6e8623019cf6";
            String catapi1 = "https://api.thecatapi.com/v1/images/search?breed_ids=";
            String catapi2 = breedID+"&api_key=";
            String theURL=catapi1+catapi2+apikey;
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
//
                sb.append(line);
                sb.append(System.lineSeparator());


            }


            JSONParser parser = new JSONParser();

            try {
                Object obj = parser.parse(sb.toString());
                JSONArray array = (JSONArray) obj;




                JSONObject obj2 = (JSONObject) array.get(0);

                String stringforurl = obj2.get("url").toString();
                this.caturl = new URL(stringforurl);



                String height = obj2.get("height").toString();
                this.imageheight = Integer.parseInt(height);

                String width = obj2.get("width").toString();
                this.imagewidth = Integer.parseInt(width);

                JSONArray arraydouble = (JSONArray) obj2.get("breeds");
                JSONObject obj3 = (JSONObject) arraydouble.get(0);
                this.desc = obj3.get("description").toString();

                this.name = obj3.get("name").toString();



            } catch (ParseException pe) {

                System.out.println("position: " + pe.getPosition());
                System.out.println(pe);
            }


        } finally {

            if (br != null) {
                br.close();
            }
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

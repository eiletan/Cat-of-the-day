package Model;

import Exceptions.RestartException;
import org.json.simple.JSONObject;

public class CatInitializer extends CatFactory {

    public CatInitializer(String breedID) {
        super(breedID);
    }

    // API code adapted from CPSC 210 edx page, which in turn was from http://zetcode.com/articles/javareadwebpage/
    // EFFECTS: Initializes Cat object fields with values taken from the API data
    @Override
    public Cat initializeCat() throws RestartException {
        Parser catparser = new Parser();
        Object response = catparser.caller("https://api.thecatapi.com/v1/images/search?breed_ids=" + super.getBreedID());
        JSONObject descobj = catparser.recursor(response,"description");
        JSONObject nameobj = catparser.recursor(response,"name");
        JSONObject urlobj = catparser.recursor(response,"url");

        String desc = descobj.get("description").toString();
        String name = nameobj.get("name").toString();
        String caturl = urlobj.get("url").toString();

        return new Cat(super.getBreedID(),desc,name,caturl);
    }
}

package Model;

import Exceptions.RestartException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class CatHandler {
    private ArrayList<String> breedIDs;
    private Parser catparser;

    public CatHandler() throws RestartException {
        this.breedIDs = new ArrayList<>();
        this.catparser = new Parser();
        this.initializeBreedList();
    }

    private void initializeBreedList() throws RestartException {
        JSONArray response = (JSONArray) catparser.caller("https://api.thecatapi.com/v1/breeds?");
        for (int i = 0; i < response.size(); i++) {
            Object catjson = response.get(i);
            JSONObject foundjson = catparser.recursor(catjson,"id");
            String id = foundjson.get("id").toString();
            breedIDs.add(id);
        }
    }

    public String selectBreed(){
        Random rng = new Random();
        int no = rng.nextInt(breedIDs.size());
        return breedIDs.get(no);
    }
}

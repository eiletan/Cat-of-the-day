package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Parser {

    public Parser() {

    }

    // Makes the call to the API
    public void parseCat(String breedID) throws IOException {
        BufferedReader br = null;

        try {
            String apikey = "4654f6ca-0eb3-4f4d-b314-6e8623019cf6";
            String apiurl = "https://api.thecatapi.com/v1/images/search?breed_ids=" + breedID + "&api_key=";
            String theURL=apiurl+apikey;
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
                JSONObject res = recursor(obj2,"description");
//                Collection<JSONObject> values = obj2.values();
//                Object gotvalues = values.toArray()[3];
//                System.out.println(gotvalues instanceof JSONArray);
//                this.caturl = new URL(stringforurl);



                String height = obj2.get("height").toString();
//                this.imageheight = Integer.parseInt(height);

                String width = obj2.get("width").toString();
//                this.imagewidth = Integer.parseInt(width);

                JSONArray arraydouble = (JSONArray) obj2.get("breeds");
                JSONObject obj3 = (JSONObject) arraydouble.get(0);
//                this.desc = obj3.get("description").toString();

//                this.name = obj3.get("name").toString();



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


    // returns the json object in which the desired selector is found
    // Using get, check if the specified target value is present in the current JSONObject, if not, get all its values and recurse for each JSONObject present
    // switch on type code smell here, however I am using an imported JSON library so I cannot fix this
    private JSONObject recursor(Object jsobj,String target) {
        if (jsobj instanceof JSONObject) {
            JSONObject search = (JSONObject) jsobj;
            if (search.get(target) != null) {
                return search;
            }
            else {
                Object[] jsonvalues = search.values().toArray();
                for(int i = 0; i < jsonvalues.length; i++) {
                    if (jsonvalues[i] instanceof JSONObject || jsonvalues[i] instanceof JSONArray) {
                        JSONObject retval = recursor((Object) jsonvalues[i], target);
                        if (retval != null) {
                            return retval;
                        }
                    }
                }
            }
        }
        if (jsobj instanceof JSONArray) {
            JSONArray search = (JSONArray) jsobj;
            for(int i = 0; i < search.size(); i++) {
                if (search.get(i) instanceof JSONObject || search.get(i) instanceof JSONArray) {
                    JSONObject retval = recursor((Object) search.get(i), target);
                    if (retval != null) {
                        return retval;
                    }
                }
            }
        }
        return null;
    }

}

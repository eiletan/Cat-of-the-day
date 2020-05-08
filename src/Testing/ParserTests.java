package Testing;


import Model.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;


public class ParserTests {
    private Parser psr;
    private Object jsonret;

    @Before
    public void setup() {
        psr = new Parser();
        try{
            String jsonretstr = psr.callCATAPI("https://api.thecatapi.com/v1/images/search?breed_ids=sfol");
            jsonret = psr.parseResponse(jsonretstr);
        } catch (IOException io) {
            fail("Failed to call API");
        }
    }

    @Test
    public void testNested() {
        JSONObject resjson = psr.recursor(jsonret,"intelligence");
        long result =  (long) resjson.get("intelligence");
        int real = (int) result;
        assertTrue(real == 3);
    }

    @Test
    public void testNesteddesc() {
        JSONObject resjson = psr.recursor(jsonret,"description");
        String expected =  "The Scottish Fold is a sweet, charming breed. She is an easy cat to live with and to care for. " +
                "She is affectionate and is comfortable with all members of her family. Her tail should be handled gently. " +
                "Folds are known for sleeping on their backs, and for sitting with their legs stretched out and their paws on their belly. " +
                "This is called the \"Buddha Position\".";
        String result = resjson.get("description").toString();
        assertTrue(expected.equals(result));
    }

    @Test
    public void testGetList() {
        try {
            String temp1 = psr.callCATAPI("https://api.thecatapi.com/v1/breeds?");
            JSONArray temp2 = (JSONArray) psr.parseResponse(temp1);
            assertTrue(temp2.size() == 67);
        }catch (IOException io) {
            fail();
        }

    }

}

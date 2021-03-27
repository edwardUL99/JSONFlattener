package edward.json.flattener;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONReaderTest {
    /**
     * The JSON we will use for our test
     */
    private static final String JSON_INPUT = "{\"a\": 1, \"b\": true, \"c\": { \"d\": 3, \"e\": \"test\"}}";
    /**
     * The object to compare to
     */
    private static final JSONObject JSON_OBJECT = new JSONObject();

    static {
        JSON_OBJECT.put("a", 1);
        JSON_OBJECT.put("b", true);
        JSONObject nested = new JSONObject();
        nested.put("d", 3);
        nested.put("e", "test");
        JSON_OBJECT.put("c", nested);
    }

    @Test
    public void shouldReadFromSystemIn() throws JSONFlattenerException, JSONException {
        System.setIn(new ByteArrayInputStream(JSON_INPUT.getBytes()));
        JSONReader reader = new JSONReader();
        JSONObject json = reader.readJSON();
        JSONAssert.assertEquals(JSON_OBJECT.toJSONString(), json.toJSONString(), JSONCompareMode.LENIENT);
    }
}
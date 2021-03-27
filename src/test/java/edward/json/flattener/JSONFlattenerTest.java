package edward.json.flattener;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.ByteArrayInputStream;

/**
 * Unit test for simple JSONFlattener.
 */
public class JSONFlattenerTest
{
    /**
     * The first JSON we will use for our test with 1 nested object
     */
    private static final String JSON_INPUT1 = "{\"a\": 1, \"b\": true, \"c\": { \"d\": 3, \"e\": \"test\"}}";
    /**
     * The first expected JSON output
     */
    private static final String JSON_OUTPUT1 = "{\"a\": 1, \"b\": true, \"c.d\": 3, \"c.e\": \"test\"}";
    /**
     * A JSON input with no nested JSON
     */
    private static final String JSON_INPUT2 = "{\"a\": 1, \"b\": true, \"c\": 3, \"d\": 4, \"e\": \"test\"}";
    /**
     * The expected output, a JSON that's already flattened should be the same on output
     */
    private static final String JSON_OUTPUT2 = "{\"a\": 1, \"b\": true, \"c\": 3, \"d\": 4, \"e\": \"test\"}";
    /**
     * This input has 2 levels of nesting, flattening should be able to handle it
     */
    private static final String JSON_INPUT3 = "{\"a\": 1, \"b\": true, \"c\": { \"d\": 3, \"e\": { \"f\": 4, \"g\": 5}, \"h\": 6}}";
    /**
     * This is the expected output with 2 levels of nesting, so some keys have 3 digits to it e.g. c.e.f
     */
    private static final String JSON_OUTPUT3 = "{\"a\": 1, \"b\": true, \"c.d\": 3, \"c.e.f\": 4, \"c.e.g\": 5, \"c.h\": 6}";
    /**
     * This input has 3 levels of nesting, flattening should be able to handle it
     */
    private static final String JSON_INPUT4 = "{\"a\": 1, \"b\": true, \"c\": { \"d\": 3, \"e\": { \"f\": { \"g\": 5, \"h\": 6}}, \"i\": 7}}";
    /**
     * This is the expected output with 3 levels of nesting, so some keys have 4 digits to it e.g. c.e.f.g
     */
    private static final String JSON_OUTPUT4 = "{\"a\": 1, \"b\": true, \"c.d\": 3, \"c.e.f.g\": 5, \"c.e.f.h\": 6, \"c.i\": 7}";

    /**
     * Sets the system input stream to the provided JSON
     * @param json the JSON to convert to a stream
     */
    private void setSystemIn(String json) {
        System.setIn(new ByteArrayInputStream(json.getBytes()));
    }

    /**
     * Parses the expected output into suitable output to compare
     * @param jsonOutput the output to compare
     * @return the expected output parsed as a comparable JSON string
     * @throws JSONFlattenerException if an error occurs parsing it
     */
    private String getOutputJSON(String jsonOutput) throws JSONFlattenerException {
        JSONReader reader = new JSONReader(new ByteArrayInputStream(jsonOutput.getBytes()));
        return reader.readJSON().toJSONString();
    }

    /**
     * Tests JSON_INPUT1 and JSON_OUTPUT1 as this input only has 1 level of nesting
     */
    @Test
    public void shouldFlattenOneNestedLevel() throws JSONFlattenerException, JSONException {
        setSystemIn(JSON_INPUT1);
        JSONFlattener flattener = new JSONFlattener(new JSONReader());
        JSONObject flattened = flattener.flatten();
        JSONAssert.assertEquals(getOutputJSON(JSON_OUTPUT1), flattened.toJSONString(), JSONCompareMode.LENIENT);
    }

    /**
     * Tests JSON_INPUT2 and JSON_OUTPUT2 as this input has no nesting and should have the same input
     */
    @Test
    public void shouldHaveSameOutputIfAlreadyFlat() throws JSONFlattenerException, JSONException {
        setSystemIn(JSON_INPUT2);
        JSONFlattener flattener = new JSONFlattener(new JSONReader());
        JSONObject flattened = flattener.flatten();
        JSONAssert.assertEquals(getOutputJSON(JSON_OUTPUT2), flattened.toJSONString(), JSONCompareMode.LENIENT);
    }

    /**
     * Tests JSON_INPUT3 and JSON_OUTPUT3 as the input has 2 levels of nesting
     */
    @Test
    public void shouldFlattenWith2NestingLevels() throws JSONFlattenerException, JSONException {
        setSystemIn(JSON_INPUT3);
        JSONFlattener flattener = new JSONFlattener(new JSONReader());
        JSONObject flattened = flattener.flatten();
        JSONAssert.assertEquals(getOutputJSON(JSON_OUTPUT3), flattened.toJSONString(), JSONCompareMode.LENIENT);
    }

    /**
     * This final test tests input with 3 nesting levels. If it works with 3 nesting levels, it can be assumed to work for n nesting levels
     */
    @Test
    public void shouldFlattenWith3NestingLevels() throws JSONFlattenerException, JSONException {
        setSystemIn(JSON_INPUT4);
        JSONFlattener flattener = new JSONFlattener(new JSONReader());
        JSONObject flattened = flattener.flatten();
        JSONAssert.assertEquals(getOutputJSON(JSON_OUTPUT4), flattened.toJSONString(), JSONCompareMode.LENIENT);
    }
}

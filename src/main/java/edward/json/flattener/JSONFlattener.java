package edward.json.flattener;

import org.json.simple.JSONObject;

import java.io.File;


/**
 * The class which performs the flattening of JSON. The procedure for flattening a JSON file is the following.
 * <code>
 *     JSONReader reader = new JSONReader();
 *     JSONFlattener flattener = new JSONFlattener(reader);
 *     JSONObject flattened = flattener.flatten();
 *     JSONFlattener.printFlattened(flattened);
 * </code>
 */
public class JSONFlattener {
    /**
     * The parsed JSON object
     */
    private final JSONObject jsonObject;

    /**
     * Constructs a JSONFlattener with the provided JSONReader
     *
     * @param jsonReader The reader to parse our JSON with.
     * @throws NullPointerException   if the provided reader is null
     * @throws JSONFlattenerException if an error occurs parsing the JSON
     */
    public JSONFlattener(JSONReader jsonReader) throws JSONFlattenerException {
        if (jsonReader == null)
            throw new NullPointerException("The provided JSONReader is null");

        this.jsonObject = jsonReader.readJSON();
    }

    /**
     * Checks whether the provided object (should be retrieved from the JSONObject) is a terminal value
     *
     * @param object the object to check
     * @return true if a terminal object, false if another JSONObject
     */
    private boolean isTerminalValue(Object object) {
        return !(object instanceof JSONObject);
    }

    /**
     * Performs the recursive flattening of the JSON object
     *
     * @param resultObject  the result object to flatten into
     * @param currentObject the current JSONObject being parsed in this recursive step
     * @param currentKey    the current key being constructed
     */
    private void doFlatten(JSONObject resultObject, JSONObject currentObject, String currentKey) {
        for (Object objectKey : currentObject.keySet()) {
            Object value = currentObject.get(objectKey);

            String key = currentKey == null ? objectKey.toString() : (currentKey + "." + objectKey); // currentKey is not null, so we are in a recursive step, so append the key of this object on

            if (isTerminalValue(value)) {
                resultObject.put(key, value);
            } else {
                doFlatten(resultObject, (JSONObject) value, key);
            }
        }
    }

    /**
     * Flattens the JSON parsed in
     *
     * @return flattened JSONObject
     */
    public JSONObject flatten() {
        JSONObject jsonObject = new JSONObject();
        doFlatten(jsonObject, this.jsonObject, null); // initially, we want to step into the recursion with the parsed object and no key met yet
        return jsonObject;
    }

    /**
     * Prints the flattened JSONObject
     *
     * @param flattened the object to print
     * @throws IllegalArgumentException if it is not a flattened object
     */
    public static void printFlattened(JSONObject flattened) {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Object key : flattened.keySet()) {
            Object value = flattened.get(key);

            if (value instanceof JSONObject) {
                throw new IllegalArgumentException("Cannot print non-flattened JSON");
            } else {
                stringBuilder.append("\n    ")
                        .append("\"")
                        .append(key)
                        .append("\": ")
                        .append((value instanceof String) ? ("\"" + value + "\"") : value)
                        .append(",");
            }
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1); // delete the last ','
        stringBuilder.append("\n}");

        System.out.println(stringBuilder.toString());
    }

    /**
     * Flattens the JSON file
     */
    private static void flattenJSON() {
        try {
            JSONReader reader = new JSONReader();
            JSONFlattener flattener = new JSONFlattener(reader);
            JSONObject flattened = flattener.flatten();

            printFlattened(flattened);
        } catch (JSONFlattenerException ex) {
            System.out.println("An error has occurred flattening the JSON: " + ex.getMessage());
        }
    }

    /**
     * Main entry point to our program.
     */
    public static void main(String[] args) {
        flattenJSON();
    }
}

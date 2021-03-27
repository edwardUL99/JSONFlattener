package edward.json.flattener;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

/**
 * This class reads JSON from either a file or command line using System.in
 */
public class JSONReader {
    /**
     * An optional input stream to read from
     */
    private final InputStream inputStream;

    /**
     * This constructor sets up the JSONReader to read from standard input
     */
    public JSONReader() {
        inputStream = System.in;
    }

    /**
     * Read JSON from the provided input stream
     * @param inputStream the input stream to read from
     */
    public JSONReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Reads the JSON into a JSONObject
     * @return parsed JSONObject
     * @throws JSONFlattenerException if an error occurs parsing the JSON
     */
    public JSONObject readJSON() throws JSONFlattenerException {
        try {
            InputStreamReader reader = new InputStreamReader(inputStream);
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(reader);
        } catch (Exception ex) {
            throw new JSONFlattenerException("Failed to parse JSON as an exception occurred", ex);
        }
    }
}

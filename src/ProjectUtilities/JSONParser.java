package ProjectUtilities;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class relative to JSON files
 * All paths are relatives to the "resources" folder
 */
public class JSONParser {
    private static String resourcesPath = "resources/";
    private static String jsonPath = "json/";

    /**
     * Parse a JSON file and return a JSON object
     *
     * @param path Path to file
     * @return JSON object or null if failed
     */
    public static JSONObject parseFile(String path) {
        try {
            String s = "";

            // Reading all fill at once
            for (String line : Files.readAllLines(Paths.get(resourcesPath + jsonPath + path))) {
                s += line + " \n";
            }

            // Converting to JSON object
            return new JSONObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

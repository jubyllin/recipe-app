package persistence;

import org.json.JSONObject;

import model.RecipeCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class JsonReader {
    private String source;

    //EFFECTS: Creates reader to read from the source file.
    public JsonReader(String source) {
        this.source = source;
    }

    //MODIFIES: this
    //EFFECTS: Reads JSON data from the file and return in the form of JSON object.
    public RecipeCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return new RecipeCollection(jsonObject);
    }

    private String readFile(String source) throws IOException {
    StringBuilder contentBuilder = new StringBuilder();
    BufferedReader reader = new BufferedReader(new FileReader(source));
    String line;
    while ((line = reader.readLine()) != null) {
        contentBuilder.append(line);
    }
    reader.close();
    return contentBuilder.toString();
    }
}

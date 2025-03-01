package persistence;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

//Represents a writer that writes JSON representation of application data to a file.
public class JsonWriter {
    private static final int TAB = 4;
    private String filePath;

    //Construct writer with the specified file path.
    public JsonWriter(String filePath) {
        this.filePath = filePath;
    }

    //MODIFIES: this
    // EFFECTS: writes the provided JSON object to file
    public void write(JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.flush();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

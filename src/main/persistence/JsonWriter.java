package persistence;

import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//Represents a writer that writes JSON representation of application data to a file.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //Construct writer with the specified file path.
    public JsonWriter(String filePath) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer; throws FileNotFoundException on if the target file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    //MODIFIES: this
    // EFFECTS: writes the provided JSON object to file
    public void write(JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(destination)) {
            file.flush();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to the file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

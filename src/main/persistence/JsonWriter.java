package persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//Represents a writer that writes JSON representation of application data to a file.
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    //Construct writer with the specified file path.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer; throws FileNotFoundException on if the target file cannot be opened
    public void open() throws IOException {
        
        File file = new File(destination);
        file.getParentFile().mkdirs(); 
        
        writer = new PrintWriter(file);
    }

    //MODIFIES: this
    // EFFECTS: writes the provided JSON object to file
    public void write(JSONObject jsonObject) {
        writer.print(jsonObject.toString(4));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

}

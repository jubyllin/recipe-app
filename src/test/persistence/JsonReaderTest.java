package persistence;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private static final String VALID_TEST_FILE = "./data/testRecipeData.json";
    private static final String EMPTY_TEST_FILE = "./data/emptyFile.json";
    private static final String MISSING_TEST_FILE = "./data/missingFile.json";

    @BeforeEach
    void setup() throws IOException {
        String validJson = "{ \"recipeCollection\": { \"recipes\": [] } }";
        Files.write(Paths.get(VALID_TEST_FILE), validJson.getBytes());

        Files.write(Paths.get(EMPTY_TEST_FILE), new byte[0]);
    }


    @Test
    void testReadValidJsonFile() {
        JsonReader reader = new JsonReader(VALID_TEST_FILE);
        try {
            JSONObject jsonObject = reader.read();
            assertNotNull(jsonObject);
            assertTrue(jsonObject.has("recipeCollection"));
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReadMissingFile() {
        JsonReader reader = new JsonReader(MISSING_TEST_FILE);
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReadEmptyFile() {
        JsonReader reader = new JsonReader(EMPTY_TEST_FILE);
        assertThrows(org.json.JSONException.class, reader::read);
    }
}


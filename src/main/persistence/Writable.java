package persistence;

import org.json.JSONObject;


public interface Writable {
    //Return in the form of JSON object.
    JSONObject toJson();
}

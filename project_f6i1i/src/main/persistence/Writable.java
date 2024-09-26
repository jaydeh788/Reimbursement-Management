package persistence;

import org.json.JSONObject;

// Reference from given JsonSerializationDemo sample
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

package persistence;

import model.ReimburseCollection;
import org.json.JSONObject;

import java.io.*;

// Reference from given JsonSerializationDemo sample
// Represents a writer that writes JSON representation of reimbursement collection to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // EFFECTS: writes JSON representation of reimbursement collection to file
    public void write(ReimburseCollection rc) {
        JSONObject json = rc.toJson();
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

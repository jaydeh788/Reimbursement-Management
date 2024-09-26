package persistence;

import model.ReimburseRequest;
import model.ReimburseCollection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

// Reference from given JsonSerializationDemo sample
// Represents a reader that reads ReimburseCollection from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: view ReimburseCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ReimburseCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReimburseCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES:  reimbursement collection
    // EFFECTS: parses collection from JSON object and returns it
    private ReimburseCollection parseReimburseCollection(JSONObject jsonObject) {
        ReimburseCollection rc = new ReimburseCollection();
        addRequests(rc, jsonObject);
        return rc;
    }

    // MODIFIES: reimbursement collection
    // EFFECTS: parses requests from JSON object and adds them to collection
    private void addRequests(ReimburseCollection rc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("requests");
        for (Object json : jsonArray) {
            JSONObject nextRequest = (JSONObject) json;
            addRequest(rc, nextRequest);
        }
    }

    // MODIFIES: reimbursement collection
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addRequest(ReimburseCollection collection, JSONObject jsonObject) {
        String serviceName = jsonObject.getString("serviceName");
        double amount = jsonObject.getDouble("amount");
        String payeeName = jsonObject.getString("payeeName");
        String bankStatementName = jsonObject.getString("bankStatementName");
        ReimburseRequest request = new ReimburseRequest(serviceName, amount,payeeName,
                bankStatementName);
        collection.addRequest(request);
    }
}

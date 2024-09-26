package persistence;

// Reference from given JsonSerializationDemo sample

import model.ReimburseRequest;
import model.ReimburseCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test if the list of requests is read correctly
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ReimburseCollection rc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyReimbursementCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyReimbursementCollection.json");
        try {
            ReimburseCollection rc = reader.read();
            assertTrue(rc.toJson().getJSONArray("requests").isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralReimbursementCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralReimbursementCollection.json");
        try {
            ReimburseCollection rc = reader.read();
            List<ReimburseRequest> requests = rc.getReimburseCollection();
            assertEquals(2, requests.size());
            checkRequest("NIBC", 200.0, "Will An", "Will An",
                    requests.get(0));
            checkRequest("ABC", 466.8,"Ben Liu", "Ben Liu",
                    requests.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
package persistence;

import model.ReimburseRequest;
import model.ReimburseCollection;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//write data to a file and then use the reader to read it back in and check that we
//read in a copy of what was written out.
class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ReimburseCollection rc = new ReimburseCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ReimburseCollection requests = new ReimburseCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyReimbursementCollection.json");
            writer.open();
            writer.write(requests);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyReimbursementCollection.json");
            requests = reader.read();
            assertTrue(requests.toJson().getJSONArray("requests").isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ReimburseCollection collection = new ReimburseCollection();
            collection.addRequest(new ReimburseRequest("NIBC", 20.3, "Jason",
                    "Jason"));
            collection.addRequest(new ReimburseRequest("ABC", 234, "Amy",
                    "Amy"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralReimbursementCollection.json");
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralReimbursementCollection.json");
            collection = reader.read();
            List<ReimburseRequest> requests = collection.getReimburseCollection();
            assertEquals(2, requests.size());
            checkRequest("NIBC", 20.3, "Jason", "Jason",
                    requests.get(0));
            checkRequest("ABC", Double.valueOf(234),"Amy", "Amy",
                    requests.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
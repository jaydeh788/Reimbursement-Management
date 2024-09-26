package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReimburseCollectionTest {
    private ReimburseCollection collection;

    @BeforeEach
    public void runBefore() {
        collection = new ReimburseCollection();
    }

    @Test
    public void testConstructor() {
        ReimburseRequest request1 = new ReimburseRequest("JDC",
                800.03, "Jeffery Lee", "Jeffery Lee");
        ReimburseRequest request2 = new ReimburseRequest("NIBC",
                408.22, "Catherine Shah",
                "Jiayi Shah");
        assertEquals(0, collection.getReimburseCollection().size());

        collection.addRequest(request1);
        assertEquals(1, collection.getReimburseCollection().size());
        assertEquals("JDC", collection.getReimburseCollection().get(0).getServiceName());
        assertEquals("Jeffery Lee", collection.getReimburseCollection().get(0).getPayeeName());
        assertEquals("Jeffery Lee", collection.getReimburseCollection().get(0).getBankStatementName());
    }

    @Test
    public void testAddRequest() {
        ReimburseRequest request1 = new ReimburseRequest("JDC",
                800.03, "Jeffery Lee", "Jeffery Lee");
        ReimburseRequest request2 = new ReimburseRequest("NIBC",
                408.22, "Catherine Shah",
                "Jiayi Shah");

        assertTrue(collection.addRequest(request1));
        assertFalse(collection.addRequest(request1)); // Adding the existing reimbursement request returns false
        assertFalse(collection.addRequest(request2)); // Adding the invalid request returns false
        assertFalse(collection.addRequest(request2)); // Adding the invalid request multiple times returns false
    }

    @Test
    public void testSortRequests() {
        ReimburseRequest request1 = new ReimburseRequest("JDC",
                300.03, "Carol Zou", "Carol Zou");

        ReimburseRequest request2 = new ReimburseRequest("JDC",
                100.09, "Leo An", "Yi An");

        ReimburseRequest request3 = new ReimburseRequest("JDC",
                309.48, "Edward Bian", "Edward Bian");

        ReimburseRequest request4 = new ReimburseRequest("JAC",
                359.46, "Edward Qin", "Edward Bian");

        collection.addRequest(request1);
        collection.addRequest(request2);
        collection.addRequest(request3);
        collection.addRequest(request4);

        List<ReimburseRequest> sortedRequests1 = collection.sortRequests("JDC");
        assertEquals(2, sortedRequests1.size());
        assertEquals("JDC", sortedRequests1.get(0).getServiceName());
        assertEquals("JDC", sortedRequests1.get(1).getServiceName());

        List<ReimburseRequest> sortedRequests2 = collection.sortRequests("JAC");
        assertEquals(0, sortedRequests2.size());

        List<ReimburseRequest> sortedRequests3 = collection.sortRequests("JBC");
        assertEquals(0, sortedRequests3.size());
    }

    @Test
    public void testBudgetLeftNoRequest() {
        assertEquals(10000.0, collection.budgetLeft("JAC"));
    }

    @Test
    public void testBudgetLeftSingleRequest() {
        ReimburseRequest request = new ReimburseRequest("NIBC",
                408.22, "Kate Shah",
                "Kate Shah");
        collection.addRequest(request);
        assertEquals(9591.78, collection.budgetLeft("NIBC"));
    }

    @Test
    public void testBudgetLeftMultipleRequest() {
        ReimburseRequest request1 = new ReimburseRequest("JDC",
                3000.03, "Carol Zou", "Carol Zou");

        ReimburseRequest request2 = new ReimburseRequest("JDC",
                6199.94, "Leo An", "Leo An");

        ReimburseRequest request3 = new ReimburseRequest("JDC",
                309.48, "Edward Bian", "Edward Bian");

        ReimburseRequest request4 = new ReimburseRequest("NIBC",
                408.22, "Kate Shah",
                "Kate Shah");

        ReimburseRequest request5 = new ReimburseRequest("JDC",
                800.03, "Jeffery Lee", "Jeffery Lee");

        // multiple requests added for the service with budget left > 0
        collection.addRequest(request1);
        collection.addRequest(request5);
        assertEquals(6199.94, collection.budgetLeft("JDC"));

        // multiple requests added for the service with budget left = 0
        collection.addRequest(request2);
        assertEquals(0, collection.budgetLeft("JDC")); // budget == 0

        // multiple requests added for the service with one request from other services, which brings no change to the
        // budget left
        collection.addRequest(request4);
        assertEquals(0, collection.budgetLeft("JDC")); // budget == 0

        // multiple requests added for the service with expense exceeding budget
        collection.addRequest(request3);
        assertEquals(-309.48, collection.budgetLeft("JDC"));
    }

    @Test
    public void testToJson() {
        ReimburseRequest request1 = new ReimburseRequest("JDC",
                800.03, "Jeffery Lee", "Jeffery Lee");
        ReimburseRequest request2 = new ReimburseRequest("NIBC",
                408.22, "Catherine Shah",
                "Jiayi Shah");

        collection.addRequest(request1);
        collection.addRequest(request2);
        JSONObject json = collection.toJson();
        assertTrue(json.has("requests"));
        assertFalse(json.getJSONArray("requests").isEmpty());
    }

    @Test
    public void testRequestsToJson() {
        ReimburseRequest request1 = new ReimburseRequest("JDC",
                800.03, "Jeffery Lee", "Jeffery Lee");
        ReimburseRequest request2 = new ReimburseRequest("NIBC",
                408.22, "Catherine Shah",
                "Jiayi Shah");
        ReimburseRequest request3 = new ReimburseRequest("NIBC",
                408.22, "Sunny Shah",
                "Sunny Shah");

        collection.addRequest(request1);
        collection.addRequest(request2);
        collection.addRequest(request3);
        JSONArray json = collection.requestsToJson();

        assertEquals(request1.getServiceName(),json.getJSONObject(0).getString("serviceName"));
        assertEquals(request1.getAmount(),json.getJSONObject(0).getDouble("amount"));
        assertEquals(request1.getPayeeName(),json.getJSONObject(0).getString("payeeName"));
        assertEquals(request1.getBankStatementName(),json.getJSONObject(0).getString("bankStatementName"));
        assertEquals(request3.getServiceName(),json.getJSONObject(1).getString("serviceName"));
        assertEquals(request3.getAmount(),json.getJSONObject(1).getDouble("amount"));
        assertEquals(request3.getPayeeName(),json.getJSONObject(1).getString("payeeName"));
        assertEquals(request3.getBankStatementName(),json.getJSONObject(1).getString("bankStatementName"));

    }
}

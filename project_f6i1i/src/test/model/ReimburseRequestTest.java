package model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReimburseRequestTest {
    private ReimburseRequest request1;
    private ReimburseRequest request2;

    @BeforeEach
    public void runBefore() {
        request1 = new ReimburseRequest("JDC",
                800.03, "Jeffery Lee", "Jeffery Lee");
        request2 = new ReimburseRequest("NIBC",
                408.22, "Catherine Shah", "Jiayi Shah");
    }

    @Test
    public void testValid() {
        assertTrue(request1.valid()); // The payeeName and bankStatementName are the same, so it's valid
        assertFalse(request2.valid());
    }

    @Test
    public void testToStringValidRequest() {
        String expected = "Service Name:JDC" + "\nAmount:800.03" + "\nPayee's Name:Jeffery Lee" +
                "\nName on Bank Statement:Jeffery Lee";

        assertEquals(expected, request1.toString());
    }

    @Test
    public void testToStringInvalidRequest() {
        assertEquals("Not a valid reimbursement request. Please resubmit the request.", request2.toString());
    }

    @Test
    public void testToJson() {
        JSONObject json = request1.toJson();
        assertTrue(json.has("serviceName"));
        assertTrue(json.has("amount"));
        assertTrue(json.has("payeeName"));
        assertTrue(json.has("bankStatementName"));
        assertEquals("JDC", json.getString("serviceName"));
        assertEquals(800.03, json.getDouble("amount"));
        assertEquals("Jeffery Lee", json.getString("payeeName"));
        assertEquals("Jeffery Lee", json.getString("bankStatementName"));
    }
}


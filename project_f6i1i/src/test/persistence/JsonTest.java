package persistence;

import model.ReimburseRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test check if the request is read correctly
public class JsonTest {
    protected void checkRequest(String serviceName, Double amount, String payeeName, String bankStatementName,
                                ReimburseRequest request) {
        assertEquals(serviceName, request.getServiceName());
        assertEquals(amount,request.getAmount());
        assertEquals(payeeName,request.getPayeeName());
        assertEquals(bankStatementName,request.getBankStatementName());
    }
}


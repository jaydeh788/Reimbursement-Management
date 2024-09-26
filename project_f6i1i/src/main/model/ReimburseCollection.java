package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Collects lists of reimbursement requests from different services
public class ReimburseCollection implements Writable {
    private List<ReimburseRequest> reimburseCollection;
    private List<String> serviceNameCollection;
    private double singleServiceBudget = 10000;

    // EFFECTS: construct a collection of reimbursement requests created
    public ReimburseCollection() {
        this.reimburseCollection = new ArrayList<>();
    }

    // MODIFIES:this
    // EFFECTS: Add new reimbursement requests to the collection if the new request is valid with no duplicate request
    // be added
    public boolean addRequest(ReimburseRequest request) {
        if (request.valid() && !reimburseCollection.contains(request)) {
            reimburseCollection.add(request);
            return true;
        }
        return false;
    }

    // EFFECTS: sort the reimbursement requests based on the name of service
    public List<ReimburseRequest> sortRequests(String serviceName) {
        List<ReimburseRequest> sortedRequests = new ArrayList<>();
        for (ReimburseRequest request : reimburseCollection) {
            if (request.getServiceName().equals(serviceName)) {
                sortedRequests.add(request);
            }
        }
        return sortedRequests;
    }

    // MODIFIES: this
    // EFFECTS: reflect the budget left of one service
    public double budgetLeft(String serviceName) {
        double budgetLeftForSingleService = singleServiceBudget;
        for (ReimburseRequest request : reimburseCollection) {
            if (request.getServiceName().equals(serviceName)) {
                budgetLeftForSingleService -= request.getAmount();
            }
        }
        return budgetLeftForSingleService;
    }

    //getter
    public List<ReimburseRequest> getReimburseCollection() {
        return reimburseCollection;
    }

    // Reference from given JsonSerializationDemo sample
    // EFFECTS: returns one request in this collection as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("requests", requestsToJson());
        return json;
    }

    // EFFECTS: returns requests in this collection as a JSON array
    public JSONArray requestsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (ReimburseRequest request : reimburseCollection) {

            jsonArray.put(request.toJson());
        }

        return jsonArray;
    }

}



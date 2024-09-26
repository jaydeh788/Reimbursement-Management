package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Create a reimbursement request that is added to the corresponding club
public class ReimburseRequest implements Writable {
    private String serviceName;
    private double amount;
    private String payeeName;
    private String bankStatementName;

    // REQUIRES: amount > 0;
    // EFFECTS: initialized a reimbursement request with the service name, date, amount, payee's name and name on bank
    // statement
    public ReimburseRequest(String serviceName,double amount,String payeeName,
                            String bankStatementName) {
        this.serviceName = serviceName;
        this.amount = amount;
        this.payeeName = payeeName;
        this.bankStatementName = bankStatementName;
    }

    // EFFECTS: check if the submitted reimbursement request is valid to be processed further
    public boolean valid() {
        return payeeName.equals(bankStatementName);
    }

    // EFFECTS: return the reimbursement request if the request submitted is valid
    public String toString() {
        if (valid()) {
            return "Service Name:" + serviceName
                    + "\nAmount:"  + amount
                    + "\nPayee's Name:" + payeeName
                    + "\nName on Bank Statement:" + bankStatementName;
        } else {
            return "Not a valid reimbursement request. Please resubmit the request.";
        }
    }

    // getters
    public String getServiceName() {

        return serviceName;
    }

    public double getAmount() {

        return amount;
    }

    public String getPayeeName() {

        return payeeName;
    }

    public String getBankStatementName() {

        return bankStatementName;
    }

    // Reference from given JsonSerializationDemo sample
    // EFFECT: change to data type of json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("serviceName", serviceName);
        json.put("amount", amount);
        json.put("payeeName", payeeName);
        json.put("bankStatementName", bankStatementName);
        return json;
    }

}

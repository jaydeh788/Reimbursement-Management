package ui;

import model.ReimburseCollection;
import model.ReimburseRequest;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents the reimbursement application
// Reference from given JsonSerializationDemo sample
public class ReimbursementProgram {
    private static final String JSON_STORE = "./data/reimbursementCollection.json";
    private Scanner scanner;
    private ReimburseRequest request;
    private List<ReimburseRequest> requests;
    private ReimburseCollection reimburseCollection;
    private String starting;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: initialized a reimbursement program
    public ReimbursementProgram() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        this.requests = new ArrayList<>();
        this.reimburseCollection = new ReimburseCollection();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        starting = "Welcome to Reimbursement Program. Type the following commands to run the respective commands."
                + "\\nADD - add a new reimbursement request\\nDISPLAY - display the list of reimbursements requests"
                + "\\nVIEW - view a list of reimbursement requests by service\\nSAVE - Save the reimbursement request"
                + " to collection\\nLOAD - load reimbursement requests from the collection\\nEXIT - exit the"
                + "reimbursement program";

        runReimbursementProgram();
    }


    // EFFECTS: run a reimbursement program on console
    public void runReimbursementProgram() {
        System.out.println(starting);
        while (true) {
            System.out.print("Enter a command: ");
            String command = scanner.nextLine().toUpperCase();
            if (command.equals("ADD")) {
                commandAdd();
            } else if (command.equals("DISPLAY")) {
                displayRequests(reimburseCollection.getReimburseCollection());
            } else if (command.equals("VIEW")) {
                commandView();
            } else if (command.equals("SAVE")) {
                commandSave();
            } else if (command.equals("LOAD")) {
                commandLoad();
            } else if (command.equals("EXIT")) {
                commandExit();
            } else {
                System.out.println("Invalid command. Please type 'ADD' to add a request, 'DISPLAY' to view all "
                        + "requests, 'VIEW' to view requests by service, or 'EXIT' to end and exit the program.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: add a request to the list of reimbursement requests after the command of add is called
    private void commandAdd() {
        System.out.print("Service Name: ");
        String serviceName = scanner.nextLine().trim();

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Payee's Name: ");
        String payeeName = scanner.nextLine().trim();

        System.out.print("Name on Bank Statement: ");
        String bankStatementName = scanner.nextLine().trim();

        reimburseCollection.addRequest(new ReimburseRequest(serviceName, amount, payeeName, bankStatementName));
    }

    // EFFECTS: view a request list
    private void commandView() {
        System.out.print("Enter the service name to view requests for a specific service: ");
        String serviceName = scanner.nextLine().trim();
        List<ReimburseRequest> serviceRequests = reimburseCollection.sortRequests(serviceName);
        displayRequests(serviceRequests);

        System.out.print("Do you want to know the budget left for this service? (YES/NO): ");
        String viewBudgetLeft = scanner.nextLine().trim().toUpperCase();
        if (viewBudgetLeft.equals("YES")) {
            double budgetLeft = reimburseCollection.budgetLeft(serviceName);
            if (budgetLeft >= 0) {
                System.out.println("Budget Left for " + serviceName + " is " + budgetLeft);
            } else {
                System.out.println("Budget for " + serviceName + " should be increased by " + Math.abs(budgetLeft));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: display the list of ReimburseRequest
    private void displayRequests(List<ReimburseRequest> requests) {
        if (requests.isEmpty()) {
            System.out.println("No request found.");
        } else {
            System.out.println("Reimbursement Requests:");
            for (ReimburseRequest request : requests) {
                System.out.println(request);
            }
        }
    }

    // EFFECTS: saves the reimbursement collection to file
    private void commandSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(reimburseCollection);
            jsonWriter.close();
            System.out.println("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads reimbursement collection from file
    private void commandLoad() {
        try {
            reimburseCollection = jsonReader.read();
            System.out.println("Loaded reimbursement collection from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: exit the program
    private void commandExit() {
        System.out.println("Exiting Reimbursement Program.");
        scanner.close();
        System.exit(0);
    }


}


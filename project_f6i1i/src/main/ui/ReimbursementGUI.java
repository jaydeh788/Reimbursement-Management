import model.ReimburseCollection;
import model.ReimburseRequest;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;

public class ReimbursementGUI {
    private JFrame frame;
    private JTextField serviceNameField, amountField, payeeNameField, bankStatementNameField;
    private JTextArea displayArea;
    private Collection reimburseCollection = new ReimburseCollection();

    public ReimbursementGUI() {
        initialize();
        reimburseCollection = new ReimburseCollection();
    }

    private void initialize() {
        frame = new JFrame("Reimbursement Program");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        serviceNameField = new JTextField();
        amountField = new JTextField();
        payeeNameField = new JTextField();
        bankStatementNameField = new JTextField();

        inputPanel.add(new JLabel("Service Name:"));
        inputPanel.add(serviceNameField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Payee's Name:"));
        inputPanel.add(payeeNameField);
        inputPanel.add(new JLabel("Name on Bank Statement:"));
        inputPanel.add(bankStatementNameField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addRequest());

        JButton displayAllButton = new JButton("Display All");
        displayAllButton.addActionListener(e -> displayAllRequests());

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> sortRequests());

        JButton budgetButton = new JButton("Track Budget");
        budgetButton.addActionListener(e -> trackBudget());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveRequests());

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(displayAllButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(budgetButton);
        buttonPanel.add(saveButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addRequest() {
        String serviceName = serviceNameField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String payeeName = payeeNameField.getText();
        String bankStatementName = bankStatementNameField.getText();

        ReimburseRequest request = new ReimburseRequest(serviceName, amount, payeeName, bankStatementName);
        boolean added = reimburseCollection.addRequest(request);

        if (added) {
            displayArea.setText("Request added:\n" + request.toString());
        } else {
            displayArea.setText("Duplicate or invalid request. Please check and resubmit.");
        }
    }

    private void displayAllRequests() {
        List<ReimburseRequest> allRequests = reimburseCollection.getReimburseCollection();
        displayArea.setText("All Reimbursement Requests:\n");
        for (ReimburseRequest request : allRequests) {
            displayArea.append(request.toString() + "\n");
        }
    }

    private void sortRequests() {
        List<ReimburseRequest> allRequests = reimburseCollection.getReimburseCollection();
        allRequests.sort((r1, r2) -> r1.getServiceName().compareToIgnoreCase(r2.getServiceName()));

        displayArea.setText("Sorted Reimbursement Requests by Service Name:\n");
        for (ReimburseRequest request : allRequests) {
            displayArea.append(request.toString() + "\n");
        }
    }

    private void trackBudget() {
        displayArea.setText("Budget Left for Each Service:\n");
        for (ReimburseRequest request : reimburseCollection.getReimburseCollection()) {
            double budgetLeft = reimburseCollection.budgetLeft(request.getServiceName());
            displayArea.append(request.getServiceName() + ": $" + budgetLeft + "\n");
        }
    }

    private void saveRequests() {
        JsonWriter jsonWriter = new JsonWriter("reimbursements.json");
        jsonWriter.write(reimburseCollection);
        displayArea.setText("Reimbursement requests saved to file.");
    }

    public static void main(String[] args) {
        new ReimbursementGUI();
    }
}

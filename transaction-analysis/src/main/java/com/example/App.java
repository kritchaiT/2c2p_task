package com.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String filePath = "transaction.json"; // identify path file for input

        List<Transaction> transactions = null; // ✅ declared here

        try {
            // Jackson Object Mapper
            ObjectMapper mapper = new ObjectMapper();

            // Load and Parse the JSON file into a List of Transaction objects
            // identify List named "transactions" -> for easy usage
            transactions = mapper.readValue(
            new File(filePath),
            new TypeReference<List<Transaction>>() {}
            );

            displayDataShowcase(transactions);

        } catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
        }

        // 1. Call the method and store it in a Map
        Map<String, List<Transaction>> result = validateTransactions(transactions);

        // 2. Access the lists using the .get() method and the keys "valid" and "invalid"
        int validCount = result.get("valid").size();
        int invalidCount = result.get("invalid").size();

        System.out.println("Valid count: " + validCount);
        System.out.println("Invalid count: " + invalidCount);
    }

// Method to display data showcase
    public static void displayDataShowcase(List<Transaction> list) {
        System.out.println("====================================================");
        System.out.println("TRANSACTION DATA LOAD SUCCESSFUL");
        System.out.println("Total Records Found: " + list.size());
        System.out.println("====================================================");
        
        // Print headers
        System.out.printf("%-12s | %-12s | %-8s | %-8s | %-10s%n | %-20s |", 
                          "ID", "REF", "AMOUNT", "CURR", "STATUS", "CREATED_AT_UTC" + "\n");
        System.out.println("----------------------------------------------------");

        // Display each record
        for (Transaction tx : list) {
            System.out.printf("%-12s | %-12s | %-8.2f | %-8s | %-10s%n | %-20s |\n",
                tx.getTransactionId(),
                tx.getMerchantRef(),
                tx.getAmount() != null ? tx.getAmount() : 0.0,
                tx.getCurrency(),
                tx.getStatus(),
                tx.getCreatedAtUtc());
        }
        System.out.println("====================================================");
    }

    // Method to validate transactions -> using Map to conatin valid and invalid transactions
    public static Map<String, List<Transaction>> validateTransactions(List<Transaction> rawTxn) {
    List<Transaction> validTxn = new ArrayList<>();
    List<Transaction> invalidTxn = new ArrayList<>();

    for (Transaction tx : rawTxn) {
        // check validity using the Validation class
        if (Validation.isValidTransaction(tx)) {
            validTxn.add(tx);
        } else {
            invalidTxn.add(tx);
        }
    }

    // using hashmap to store valid and invalid transactions
    Map<String, List<Transaction>> result = new HashMap<>();
    result.put("valid", validTxn);
    result.put("invalid", invalidTxn);

    return result;
}
}

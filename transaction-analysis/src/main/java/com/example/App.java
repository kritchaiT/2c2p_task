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
        // check working directory
        System.out.println( "Hello World!" );
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        // identify path file for input
        String filePath = "transaction.json";
        List<Transaction> transactions = null;

        try {
            // ***Jackson Object Mapper***
            ObjectMapper mapper = new ObjectMapper();

            // Load and Parse the JSON file into a List of Transaction objects
            // identify List named "transactions" -> for easy usage
            transactions = mapper.readValue(
            new File(filePath),
            new TypeReference<List<Transaction>>() {}
            );

            // Display data
            DisplayData.displayData(transactions);

        } catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
        }

        // Call the method and store it in a Map
        Map<String, List<Transaction>> result = Validation.validateTransactions(transactions);

        // Access the lists using the .get() method and the keys "valid" and "invalid"
        int validCount = result.get("valid").size();
        int invalidCount = result.get("invalid").size();

        System.out.println("Valid count: " + validCount);
        System.out.println("Invalid count: " + invalidCount);

        DisplayData.displayData(result.get("valid"));
        DisplayData.displayData(result.get("invalid"));

        // test printing the first invalid transaction
        Transaction firstBad = result.get("invalid").get(0);
        System.out.println(firstBad.toString());

    }
}

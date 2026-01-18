package com.example;
import java.util.List;

// Class to display data in tabular format
public class DisplayData {
    public static void displayData(List<Transaction> list) {
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

    public static void displayDupData(List<Transaction> list) {
        System.out.println("====================================================");
        System.out.println("Total duplicated Records Found: " + list.size());
        System.out.println("====================================================");
        
        // Print headers
        System.out.printf("%-12s | %-12s | %-8s | %-8s | %-10s%n | %-20s |", 
                          "ID", "REF", "AMOUNT", "CURR", "STATUS", "DUP_reason" + "\n");
        System.out.println("----------------------------------------------------");

        // Display each record
        for (Transaction tx : list) {
            System.out.printf("%-12s | %-12s | %-8.2f | %-8s | %-10s%n | %-20s |\n",
                tx.getTransactionId(),
                tx.getMerchantRef(),
                tx.getAmount() != null ? tx.getAmount() : 0.0,
                tx.getCurrency(),
                tx.getStatus(),
                tx.getReasonForRejection());
        }
        System.out.println("====================================================");
    }
}

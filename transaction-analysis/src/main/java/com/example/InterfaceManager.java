package com.example;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InterfaceManager {

    private static List<Transaction> transactions = new ArrayList<>();
    private static Map<String, List<Transaction>> validationResult = new HashMap<>();

    public static void startInterface() {
        System.out.println("------------------------------------------------");
        System.out.println("Interface Manager Started");
        System.out.println("Type 'help' for available commands");
        System.out.println("------------------------------------------------");
    }

    public static boolean handleCommand(String input) {
        String[] args = input.trim().split("\\s+");

        if (args.length == 0) return true;

        switch (args[0].toLowerCase()) {
            // load
            case "load":
                loadTransactions();
                break;
            // show
            case "show":
                if (transactions.isEmpty()) {
                    System.out.println("No data loaded. Use 'load' first.");
                    break;
                }

                if (args.length < 2) {
                    System.out.println("Usage: show all | valid | invalid");
                    break;
                }

                switch (args[1].toLowerCase()) {
                    case "all":
                        DisplayData.displayData(transactions);
                        break;
                    case "valid":
                        DisplayData.displayData(validationResult.get("valid"));
                        break;
                    case "invalid":
                        DisplayData.displayData(validationResult.get("invalid"));
                        break;
                    case "dup":
                        DuplicationIden.identifyDup(transactions);
                        DisplayData.displayData(transactions.stream().filter(t -> t.getFlagDup() == 1).collect(Collectors.toList()));
                        break;
                    default:
                        System.out.println("Invalid show option.");
                }
                break;
            // clear
            case "clear":
                clearBash();
                break;
            // help
            case "help":
                printHelp();
                break;
            // end
            case "end":
                System.out.println("Exiting Interface Manager.");
                return false; // stop loop

            default:
                System.out.println("Invalid command. Type 'help' for options.");
        }

        return true; // continue loop
    }

    private static void loadTransactions() {
        String filePath = "transaction.json";

        try {
            // ***Jackson Object Mapper***
            ObjectMapper mapper = new ObjectMapper();
            transactions = mapper.readValue(
                    new File(filePath),
                    new TypeReference<List<Transaction>>() {}
            );

            validationResult = Validation.validateTransactions(transactions);

            System.out.println("Loaded " + transactions.size() + " transactions.");

        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("load               - Load transaction data");
        System.out.println("show all           - Display all transactions");
        System.out.println("show valid         - Display valid transactions");
        System.out.println("show invalid       - Display invalid transactions");
        System.out.println("show dup           - Display duplicate transactions");
        System.out.println("clear              - Clear the console");
        System.out.println("help               - Show this help");
        System.out.println("end                - Exit the program");
    }

    public static void clearBash() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Could not clear screen.");
        }
    }
}

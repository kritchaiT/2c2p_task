package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InterfaceManager {

    private static List<Transaction> transactions = new ArrayList<>();
    private static Map<String, List<Transaction>> validationResult = new HashMap<>();

    // =====================================================
    // ENTRY POINT
    // =====================================================

    public static void startInterface() {
        System.out.println("------------------------------------------------");
        System.out.println("Interface Manager Started");
        System.out.println("Type 'help' for available commands");
        System.out.println("------------------------------------------------");
    }

    public static boolean handleCommand(String input) {
        if (input == null || input.isBlank()) {
            return true;
        }

        String[] args = input.trim().split("\\s+");

        switch (args[0].toLowerCase()) {

            case "load":
                loadTransactions();
                break;

            case "show":
                handleShow(args);
                break;

            case "report":
                if (transactions.isEmpty()) {
                    System.out.println("No data loaded. Use 'load' first.");
                } else {
                    generateReport("report.json");
                }
                break;

            case "clear":
                clearBash();
                break;

            case "help":
                printHelp();
                break;

            case "end":
                System.out.println("Exiting Interface Manager.");
                return false;

            default:
                System.out.println("Invalid command. Type 'help' for options.");
        }
        return true;
    }

    // =====================================================
    // SHOW COMMAND
    // =====================================================

    private static void handleShow(String[] args) {

        if (transactions.isEmpty()) {
            System.out.println("No data loaded. Use 'load' first.");
            return;
        }

        if (args.length < 2) {
            System.out.println("Usage: show all | valid | invalid | dup");
            return;
        }

        switch (args[1].toLowerCase()) {

            case "all":
                DisplayData.displayData(transactions);
                break;

            case "valid":
                DisplayData.displayData(
                        validationResult.getOrDefault("valid", Collections.emptyList())
                );
                break;

            case "invalid":
                DisplayData.displayData(
                        validationResult.getOrDefault("invalid", Collections.emptyList())
                );
                break;

            case "dup":
                DuplicationIden.identifyDup(transactions);
                DisplayData.displayDupData(
                        transactions.stream()
                                .filter(tx -> tx.getFlagDup() == 1)
                                .collect(Collectors.toList())
                );
                break;

            default:
                System.out.println("Invalid show option.");
        }
    }

    // =====================================================
    // LOAD
    // =====================================================

    private static void loadTransactions() {

        String filePath = "transaction.json";

        try {
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

    // =====================================================
    // REPORT
    // =====================================================

    private static void generateReport(String outputFile) {

        System.out.println("Generating report...");

        List<Transaction> valid =
                validationResult.getOrDefault("valid", Collections.emptyList());
        List<Transaction> invalid =
                validationResult.getOrDefault("invalid", Collections.emptyList());

        // Detect duplicates on VALID records only
        DuplicationIden.identifyDup(valid);

        // ---------- INVALID BREAKDOWN ----------
        Map<String, Integer> invalidBreakdown = new HashMap<>();
        for (Transaction tx : invalid) {
            String reason = tx.getReasonForRejection();
            if (reason == null || reason.isBlank()) {
                reason = "UNKNOWN";
            }
            invalidBreakdown.put(reason, invalidBreakdown.getOrDefault(reason, 0) + 1);
        }

        // ---------- DUPLICATE GROUPS ----------
        List<DuplicateGroup> duplicateGroups = buildDuplicateGroups(valid);

        // ---------- SUCCESS + IDEMPOTENT ----------
        List<Transaction> reportTxs = buildReportTransactions(valid);

        // ---------- STATUS COUNTS ----------
        Map<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("SUCCESS", reportTxs.size());

        // ---------- AMOUNT STATS ----------
        List<Double> amounts = reportTxs.stream()
                .map(Transaction::getAmount)
                .collect(Collectors.toList());

        AmountStats stats;
        if (amounts.isEmpty()) {
            stats = new AmountStats(0, 0, 0);
        } else {
            stats = new AmountStats(
                    Collections.min(amounts),
                    Collections.max(amounts),
                    amounts.stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElse(0)
            );
        }

        // ---------- BUILD REPORT ----------
        Report report = new Report();
        report.totalRecords = transactions.size();
        report.validRecords = valid.size();
        report.invalidRecords = invalid.size();
        report.invalidBreakdown = invalidBreakdown;
        report.statusCounts = statusCounts;
        report.successAmountStats = stats;
        report.duplicates = new Report.DuplicateSummary(
                duplicateGroups.size(),
                duplicateGroups.stream()
                        .mapToInt(group -> group.transactions.size())
                        .sum(),
                duplicateGroups
        );

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(outputFile), report);

            System.out.println("Report written to " + outputFile);

        } catch (Exception e) {
            System.err.println("Failed to write report: " + e.getMessage());
        }
    }

    // =====================================================
    // HELPERS
    // =====================================================

    /**
     * SUCCESS only, idempotent:
     * First SUCCESS per transactionId wins.
     */
    private static List<Transaction> buildReportTransactions(List<Transaction> validTxs) {

        Map<String, Transaction> firstSuccess = new LinkedHashMap<>();

        for (Transaction tx : validTxs) {
            if (!"SUCCESS".equals(tx.getStatus())) {
                continue;
            }
            firstSuccess.putIfAbsent(tx.getTransactionId(), tx);
        }

        return new ArrayList<>(firstSuccess.values());
    }

    private static List<DuplicateGroup> buildDuplicateGroups(List<Transaction> txs) {

        Map<String, List<Transaction>> grouped = new HashMap<>();

        for (Transaction tx : txs) {
            if (tx.getFlagDup() != 1) {
                continue;
            }

            String key = tx.getReasonForRejection() + "|" + tx.getTransactionId();

            grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(tx);
        }

        List<DuplicateGroup> result = new ArrayList<>();

        for (Map.Entry<String, List<Transaction>> entry : grouped.entrySet()) {
            String[] parts = entry.getKey().split("\\|", 2);
            result.add(new DuplicateGroup(parts[0], parts[1], entry.getValue()));
        }

        return result;
    }

    // =====================================================
    // MISC
    // =====================================================

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("load               - Load transaction data");
        System.out.println("show all           - Display all transactions");
        System.out.println("show valid         - Display valid transactions");
        System.out.println("show invalid       - Display invalid transactions");
        System.out.println("show dup           - Display duplicate transactions");
        System.out.println("report             - Generate report.json");
        System.out.println("clear              - Clear the console");
        System.out.println("help               - Show this help");
        System.out.println("end                - Exit the program");
    }

    public static void clearBash() {
        try {
            new ProcessBuilder("clear")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (Exception e) {
            System.out.println("Could not clear screen.");
        }
    }
}

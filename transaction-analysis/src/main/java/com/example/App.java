package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Map<String, String> options = parseArgs(args);

        // ---------------- CLI / BATCH MODE ----------------
        if (options.containsKey("input")) {
            String inputFile = options.get("input");
            String outputFile = options.getOrDefault("output", "report.json");

            System.out.println("Running in CLI mode");
            System.out.println("Input  : " + inputFile);
            System.out.println("Output : " + outputFile);

            InterfaceManager.runBatch(inputFile, outputFile);
            return; // IMPORTANT: exit after batch
        }

        // ---------------- INTERACTIVE MODE ----------------
        InterfaceManager.startInterface();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine();
            running = InterfaceManager.handleCommand(input);
        }

        scanner.close();
    }

    // --------------------------------------------------
    // Simple CLI parser
    // --------------------------------------------------
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.startsWith("--")) {
                String key = arg.substring(2);

                if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
                    map.put(key, args[++i]);
                } else {
                    map.put(key, "true");
                }
            }
        }
        return map;
    }
}

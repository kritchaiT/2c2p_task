package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicationIden {

    public static void identifyDup(List<Transaction> txns) {
        System.out.println("duplication identification working...");

        // Count occurrences of each transactionId
        Map<String, Integer> countByTxnId = new HashMap<>();

        for (Transaction txn : txns) {
            String txnId = txn.getTransactionId();
            if (txnId != null && !txnId.isEmpty()) {
                countByTxnId.put(txnId, countByTxnId.getOrDefault(txnId, 0) + 1);
            }
        }

        // Flag duplicates
        for (Transaction txn : txns) {
            String txnId = txn.getTransactionId();
            if (txnId != null && countByTxnId.getOrDefault(txnId, 0) > 1) {
                txn.flagDuplicate();
                System.out.println("Duplicate found: " + txn);
            }
        }
    }
}

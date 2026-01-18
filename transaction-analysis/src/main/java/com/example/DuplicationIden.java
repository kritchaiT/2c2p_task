package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicationIden {

    public static void identifyDup(List<Transaction> txxs) {
        // txxs -> transactions
        // txn -> each transaction from transactions(txxs)
        System.out.println("duplication identification working...");

        // Count occurrences of each transactionId
        Map<String, Integer> countByTxnId = new HashMap<>();

        for (Transaction txn : txxs) {
            String txnId = txn.getTransactionId();
            if (txnId != null && !txnId.isEmpty()) {
                countByTxnId.put(txnId, countByTxnId.getOrDefault(txnId, 0) + 1);
            }
        }

        // Flag duplicates
        for (Transaction txn : txxs) {
            String txnId = txn.getTransactionId();

            // Case 1: if count > 1, flag as duplicate
            if (txnId != null && countByTxnId.getOrDefault(txnId, 0) > 1) {
                txn.flagDuplicate();
                txn.setReasonForRejection("Duplicated Txn ID");
                System.out.println("Duplicate found: " + txn);
            }
            // Case 2: if transactionId is missing, also flag as duplicate
            else if (txnId == null || txnId.isEmpty()) {
                txn.flagDuplicate();
                txn.setReasonForRejection("Missing Txn ID");
                System.out.println("Missing Txn ID found: " + txn);
            }
            // Else, not a duplicate
        }
    }
}

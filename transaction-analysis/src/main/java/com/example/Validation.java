package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validation {

    // to add return reason of rejection
    public static boolean isValidTransaction(Transaction tx) {
        // input validation checks

        if (tx.getTransactionId() == null || tx.getTransactionId().isEmpty()) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getMerchantRef() == null || tx.getMerchantRef().isEmpty()) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getAmount() == null || tx.getAmount() < 0) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getCurrency() == null
                || tx.getCurrency().isEmpty()
                || tx.getCurrency().length() != 3) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getStatus() == null || tx.getStatus().isEmpty()) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getCreatedAtUtc() == null || tx.getCreatedAtUtc().isEmpty()) {
            tx.flagIncomplete();
            return false;
        }

        return true;
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
